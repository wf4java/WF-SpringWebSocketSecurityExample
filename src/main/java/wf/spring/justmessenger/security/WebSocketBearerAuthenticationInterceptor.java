package wf.spring.justmessenger.security;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.security.Principal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@RequiredArgsConstructor
public class WebSocketBearerAuthenticationInterceptor implements ChannelInterceptor {

    private static final Pattern AUTHORIZATION_PATTERN = Pattern.compile("^Bearer (?<token>[a-zA-Z0-9-._~+/]+=*)$", 2);
    private static final String HEADER = "Authorization";

    private final AuthenticationManager authenticationManager;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        Assert.notNull(accessor, "Accessor is null!");

        if(accessor.getCommand() != StompCommand.CONNECT) return message;


        String authorization = accessor.getFirstNativeHeader(HEADER);

        if(authorization == null) return getTextMessage("Missing authorization token!");
        if (!StringUtils.startsWithIgnoreCase(authorization, "bearer")) return getTextMessage("Incorrect authorization token!");
        Matcher matcher = AUTHORIZATION_PATTERN.matcher(authorization);
        if (!matcher.matches()) return getTextMessage("Incorrect authorization token!");

        authorization = matcher.group("token");

        try {
            Authentication authentication = authenticationManager.authenticate(new BearerTokenAuthenticationToken(authorization));
            accessor.setUser((Principal) authentication.getPrincipal());
        }
        catch (AuthenticationException e) { return getTextMessage(e.getMessage()); }

        return message;
    }

    private static Message<String> getTextMessage(String text) {
        return MessageBuilder.withPayload(text).build();
    }

}
