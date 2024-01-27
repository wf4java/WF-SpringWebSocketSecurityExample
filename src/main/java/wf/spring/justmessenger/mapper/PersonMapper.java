package wf.spring.justmessenger.mapper;

import org.springframework.stereotype.Component;
import wf.spring.justmessenger.dto.auth.RegisterRqDTO;
import wf.spring.justmessenger.entity.Person;

@Component
public class PersonMapper {

    public Person toPerson(RegisterRqDTO registerRqDTO) {
        Person person = new Person();

        person.setUsername(registerRqDTO.getUsername());
        person.setEmail(registerRqDTO.getEmail());
        person.setPassword(registerRqDTO.getPassword());
        person.setActive(true);

        return person;
    }

}
