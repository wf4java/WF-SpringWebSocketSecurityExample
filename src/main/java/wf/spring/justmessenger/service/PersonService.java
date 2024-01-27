package wf.spring.justmessenger.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wf.spring.justmessenger.repository.PersonRepository;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;



}
