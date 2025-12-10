package com.epam.rd.autocode.assessment.appliances.controller;

import com.epam.rd.autocode.assessment.appliances.Dtos.SignupDto;
import com.epam.rd.autocode.assessment.appliances.model.Client;
import com.epam.rd.autocode.assessment.appliances.model.Employee;
import com.epam.rd.autocode.assessment.appliances.repository.ClientRepository;
import com.epam.rd.autocode.assessment.appliances.repository.EmployeeRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class SignupController {

    private final EmployeeRepository employeeRepository;
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("signup", new SignupDto());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute("signup") SignupDto dto,
                         BindingResult result) {

        if (result.hasErrors()) {
            return "signup";
        }

        if (dto.getRole().equals("EMPLOYEE")) {
            Employee e = new Employee();
            e.setName(dto.getName());
            e.setEmail(dto.getEmail());
            e.setPassword(passwordEncoder.encode(dto.getPassword()));
            e.setDepartment("General");
            employeeRepository.save(e);
        } else {
            Client c = new Client();
            c.setName(dto.getName());
            c.setEmail(dto.getEmail());
            c.setPassword(passwordEncoder.encode(dto.getPassword()));
            c.setCard("0000-0000");
            clientRepository.save(c);
        }

        return "redirect:/login";
    }
}
