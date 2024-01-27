package wf.spring.justmessenger.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wf.spring.justmessenger.dto.auth.RegisterRqDTO;
import wf.spring.justmessenger.entity.Person;
import wf.spring.justmessenger.mapper.PersonMapper;
import wf.spring.justmessenger.model.exception.ConflictException;
import wf.spring.justmessenger.repository.PersonRepository;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class PersonRegisterService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    private final PersonMapper personMapper;

    @Transactional
    public void register(RegisterRqDTO registerRqDTO) {
        Person person = personMapper.toPerson(registerRqDTO);

        if(personRepository.existsByUsername(person.getUsername()))
            throw new ConflictException("Username already taken!");

        if(personRepository.existsByEmail(person.getEmail()))
            throw new ConflictException("Email already taken!");

        person.setCreatedAt(new Date());
        person.setPassword(passwordEncoder.encode(person.getPassword()));

        personRepository.save(person);
    }

}
