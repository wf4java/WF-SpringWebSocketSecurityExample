package wf.spring.justmessenger.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import wf.spring.justmessenger.dto.ErrorMessageRsDTO;
import wf.spring.justmessenger.dto.auth.AuthenticationRqDTO;
import wf.spring.justmessenger.dto.auth.JwtTokenRsDTO;
import wf.spring.justmessenger.dto.auth.RegisterRqDTO;
import wf.spring.justmessenger.model.exception.basic.BadRequestException;
import wf.spring.justmessenger.model.exception.basic.HttpException;
import wf.spring.justmessenger.service.security.JwtAuthenticationService;
import wf.spring.justmessenger.service.security.PersonRegisterService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtAuthenticationService jwtAuthenticationService;
    private final PersonRegisterService personRegisterService;


    @PostMapping("/singin")
    public JwtTokenRsDTO auth(@RequestBody @Valid AuthenticationRqDTO authenticationRqDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            throw new BadRequestException(bindingResult);

        return new JwtTokenRsDTO(jwtAuthenticationService.validateAndGenerateToken(
                authenticationRqDTO.getUsername(), authenticationRqDTO.getPassword()));
    }

    @PostMapping("/singup")
    public void register(@RequestBody @Valid RegisterRqDTO registerRqDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            throw new BadRequestException(bindingResult);

        personRegisterService.register(registerRqDTO);
    }




    @ExceptionHandler
    private ResponseEntity<ErrorMessageRsDTO> exceptionHandler(AuthenticationException e){
        return new ResponseEntity<>(new ErrorMessageRsDTO(e), HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler
    private ResponseEntity<ErrorMessageRsDTO> exceptionHandler(HttpException e){
        return new ResponseEntity<>(new ErrorMessageRsDTO(e.getMessage()), e.getHttpStatus());
    }

}
