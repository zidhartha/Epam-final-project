package com.epam.rd.autocode.assessment.appliances.controller;

import com.epam.rd.autocode.assessment.appliances.model.Client;
import com.epam.rd.autocode.assessment.appliances.model.Employee;
import com.epam.rd.autocode.assessment.appliances.repository.ClientRepository;
import com.epam.rd.autocode.assessment.appliances.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class IndexController {

    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;
    private final ClientRepository clientRepository;

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/reset-password")
    public String resetPasswordForm() {
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String email,
                                @RequestParam String newPassword) {


        Optional<Client> client = clientRepository.findByEmail(email);
        if (client.isPresent()) {
            client.get().setPassword(passwordEncoder.encode(newPassword));
            clientRepository.save(client.get());
            return "redirect:/login?resetSuccess";
        }


        Optional<Employee> employee = employeeRepository.findByEmail(email);
        if (employee.isPresent()) {
            employee.get().setPassword(passwordEncoder.encode(newPassword));
            employeeRepository.save(employee.get());
            return "redirect:/login?resetSuccess";
        }

        return "redirect:/reset-password?error";
    }
    @GetMapping("/custom-error")
    public String showErrorPage(Model model) {
        model.addAttribute("title", "Modification Not Allowed");
        model.addAttribute("message", "This order has already been approved and cannot be modified.");
        model.addAttribute("status", 400);
        model.addAttribute("timestamp", java.time.LocalDateTime.now().toString());
        return "Error/customError"; // This loads your HTML template
    }
}
