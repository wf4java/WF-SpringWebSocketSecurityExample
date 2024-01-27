package wf.spring.justmessenger.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.security.Principal;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Document(collection = "person")
public class Person implements Principal {

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    private String username;

    @Indexed(unique = true)
    private String email;

    private String password;

    private Date createdAt;

    private Boolean active;



    @Override
    public Person clone() {
        try {return (Person) super.clone();} catch (CloneNotSupportedException e) {throw new RuntimeException(e);}
    }

    @Override
    public String getName() {
        return username;
    }
}
