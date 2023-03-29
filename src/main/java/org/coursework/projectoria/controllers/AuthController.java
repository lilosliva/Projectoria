package org.coursework.projectoria.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.coursework.projectoria.models.Person;
import org.coursework.projectoria.security.PersonDetails;
import org.coursework.projectoria.services.RegistrationService;
import org.coursework.projectoria.util.PersonValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("sign")
@Controller
@AllArgsConstructor
public class AuthController {
    private final RegistrationService registrationService;
    private final PersonValidator personValidator;

    @GetMapping("/up")
    public String signUp(Model model) {
        model.addAttribute("user", new Person());
        return "signup";
    }
    @GetMapping("/in")
    public String signIn(Model model) {
        model.addAttribute("user", new Person());
        return "signin";
    }
    @PostMapping("/up")
    public String performRegistration(@ModelAttribute("user") @Valid Person person,
                                      BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "signup";
        }
        registrationService.register(person);
        return "redirect:/signin";
    }
    @ModelAttribute(name = "role")
    public String getCurrentRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof PersonDetails personDetails) {
            return personDetails.getPerson().getRole();
        } else {
            return null;
        }
    }
}
