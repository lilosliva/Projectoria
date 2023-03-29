package org.coursework.projectoria.services;

import lombok.AllArgsConstructor;
import org.coursework.projectoria.models.Person;
import org.coursework.projectoria.repositories.PeopleRepository;
import org.coursework.projectoria.security.PersonDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonDetailService implements UserDetailsService {
    private final PeopleRepository peopleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = peopleRepository.findByEmail(username);
        if(person.isEmpty()){
            throw new UsernameNotFoundException("Пользователь не найден.");
        }
        return new PersonDetails(person.get());
    }
}
