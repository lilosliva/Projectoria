package org.coursework.projectoria.util;

import lombok.AllArgsConstructor;
import org.coursework.projectoria.models.Person;
import org.coursework.projectoria.services.PersonDetailService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@AllArgsConstructor
public class PersonValidator implements Validator {
    private final PersonDetailService personDetailService;
    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        try {
            personDetailService.loadUserByUsername(person.getEmail());
        }
        catch (UsernameNotFoundException e) {
            return;
        }
        errors.rejectValue("email", "",
                "Пользователь с такой электронной почтой уже существует.");
    }
}
