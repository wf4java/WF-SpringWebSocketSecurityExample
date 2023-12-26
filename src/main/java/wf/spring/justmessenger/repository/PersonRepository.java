package wf.spring.justmessenger.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import wf.spring.justmessenger.entity.Person;

import java.util.Optional;

public interface PersonRepository extends MongoRepository<Person, String> {

    public Optional<Person> findByUsername(String username);

    public boolean existsByUsername(String username);

    public boolean existsByEmail(String email);

}
