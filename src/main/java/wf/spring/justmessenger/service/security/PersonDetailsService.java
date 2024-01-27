package wf.spring.justmessenger.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import wf.spring.justmessenger.entity.Person;
import wf.spring.justmessenger.repository.PersonRepository;
import wf.spring.justmessenger.security.PersonDetails;


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;

    @Override
    public PersonDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = personRepository.findByUsername(username);
        if(person.isEmpty()) throw new UsernameNotFoundException("User not found!");

        return new PersonDetails(person.get());
    }

    public PersonDetails loadUserById(String id) throws UsernameNotFoundException {
        Optional<Person> person = personRepository.findById(id);
        if(person.isEmpty()) throw new UsernameNotFoundException("User not found!");

        return new PersonDetails(person.get());
    }

}
