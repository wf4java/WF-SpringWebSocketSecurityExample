package wf.spring.justmessenger.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import wf.spring.justmessenger.entity.Person;
import wf.spring.justmessenger.model.exception.basic.HttpException;

@RestController
@RequiredArgsConstructor
public class MessageController {

    @MessageMapping("/message")
    public void sendMessage(@RequestBody String s, @AuthenticationPrincipal Person principal) {
        System.out.println(s);
        System.out.println(principal.getUsername());
        System.out.println();
    }




    @MessageExceptionHandler
    private void exceptionHandler(HttpException e) {
        //Code
    }

}
