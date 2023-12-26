package wf.spring.justmessenger.service.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import wf.spring.justmessenger.properties.JwtProperties;
import wf.spring.justmessenger.security.PersonDetails;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationService {

    private final PersonDetailsService personDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtProperties jwtProperties;


    public String validateAndGenerateToken(String username, String password) throws AuthenticationException {
        PersonDetails personDetails = personDetailsService.loadUserByUsername(username);

        if (!passwordEncoder.matches(password, personDetails.getPassword()))
            throw new BadCredentialsException("Password not valid");

        if (!personDetails.isEnabled()) throw new DisabledException("This account not activated");
        return generateTokenFromId(personDetails.getPerson().getId().toHexString());
    }


    public UsernamePasswordAuthenticationToken getAuthenticatedOrFail(final String jwtToken) throws AuthenticationException {
        if (jwtToken == null)
            throw new AuthenticationCredentialsNotFoundException("Token was empty");

        String id;

        try {id = validateTokenAndRetrieveSubjectToId(jwtToken);}
        catch (JWTVerificationException exception) {throw new BadCredentialsException("Token not valid");}

        PersonDetails personDetails = personDetailsService.loadUserById(id);

        if (!personDetails.isEnabled()) throw new DisabledException("This account not activated");

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                personDetails.getPerson(),
                personDetails.getPerson().getPassword(),
                personDetails.getAuthorities()
        );
        authenticationToken.setDetails(personDetails);

        return authenticationToken;
    }




    private String generateTokenFromId(String id) {
        Date expirationDate = Date.from(ZonedDateTime.now().plusDays(jwtProperties.getExpirationDays()).toInstant());

        return JWT.create()
                .withSubject("user_details")
                .withClaim("id", id)
                .withIssuer(jwtProperties.getIssuer())
                .withIssuedAt(new Date())
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(jwtProperties.getSecretKey()));
    }



    private String validateTokenAndRetrieveSubjectToId(String token) throws JWTVerificationException {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(jwtProperties.getSecretKey()))
                .withSubject("user_details")
                .withIssuer(jwtProperties.getIssuer())
                .build();

        return Optional.ofNullable(jwtVerifier.verify(token).getClaim("id").asString())
                .orElseThrow(() -> new AuthenticationServiceException("JWT token invalid"));
    }

}
