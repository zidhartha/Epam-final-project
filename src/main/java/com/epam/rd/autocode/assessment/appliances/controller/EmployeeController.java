package com.epam.rd.autocode.assessment.appliances.controller;

import com.epam.rd.autocode.assessment.appliances.Dtos.EmployeeDto;
import com.epam.rd.autocode.assessment.appliances.model.Employee;
import com.epam.rd.autocode.assessment.appliances.service.impl.EmployeeServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeServiceImpl employeeService;

    @GetMapping
    public String getAll(Model model){
        model.addAttribute("employees", employeeService.getAll());
        return "employee/employees";
    }

    @GetMapping("/add")
    public String addForm(Model model){
        model.addAttribute("employee", new EmployeeDto());
        return "employee/newEmployee";
    }

    @PostMapping("/add")
    public String create(@Valid @ModelAttribute("employee") EmployeeDto employeeDto,
                         BindingResult result) {

        if (result.hasErrors()) {
            return "employee/newEmployee";
        }

        employeeService.create(employeeDto);
        return "redirect:/employees";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        employeeService.delete(id);
        return "redirect:/employees";
    }
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        EmployeeDto dto = employeeService.getEditDtoById(id);
        model.addAttribute("employee", dto);
        return "employee/editEmployee";
    }
    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id,
                         @ModelAttribute("employee") @Valid EmployeeDto employeeDto,
                         BindingResult result) {

        if (result.hasErrors()) {
            return "employee/editEmployee";
        }

        employeeService.update(id, employeeDto);
        return "redirect:/employees";
    }


}