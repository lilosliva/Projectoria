package org.coursework.projectoria.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity
@Table(name = "user")
@Data
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Email не должен быть пустым!")
    @Email(message = "Это не похоже на email")
    private String email;


    @Pattern(regexp = "^.{8,}$", message = "Пароль, должен иметь длину от 8 символов")
    private String password;

    private String role;

    public Person() {
    }

    public Person(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Person(String email){
        this.email = email;
    }
}
