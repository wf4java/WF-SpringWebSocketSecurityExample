package wf.spring.justmessenger.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;
import wf.spring.justmessenger.security.JwtAuthenticationManager;
import wf.spring.justmessenger.security.WebSocketBearerAuthenticationInterceptor;


@Configuration
@EnableWebSocket
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    private final JwtAuthenticationManager jwtAuthenticationManager;


    @Override
    public void configureMessageBroker(final MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/messenger", "/topic");
    }


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint("/ws/messenger")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }


    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(webSocketJwtAuthInterceptor());
    }


    @Bean
    public WebSocketBearerAuthenticationInterceptor webSocketJwtAuthInterceptor() {
        return new WebSocketBearerAuthenticationInterceptor(jwtAuthenticationManager);
    }


}
