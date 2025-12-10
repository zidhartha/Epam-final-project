package com.epam.rd.autocode.assessment.appliances.controller;

import com.epam.rd.autocode.assessment.appliances.Dtos.ManufacturerDto;
import com.epam.rd.autocode.assessment.appliances.service.impl.ManufacturerServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/manufacturers")
@RequiredArgsConstructor
public class ManufacturerController {

    private final ManufacturerServiceImpl manufacturerService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("manufacturers", manufacturerService.getAll());
        return "manufacture/manufacturers";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("manufacturerDto", new ManufacturerDto());
        return "manufacture/newManufacturer";
    }

    @PostMapping("/add-manufacturer")
    public String create(@Valid @ModelAttribute("manufacturerDto") ManufacturerDto manufacturerDto,
                         BindingResult result) {

        if (result.hasErrors()) {
            return "manufacture/newManufacturer";
        }

        manufacturerService.create(manufacturerDto);
        return "redirect:/manufacturers";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        ManufacturerDto dto = manufacturerService.getById(id);
        model.addAttribute("manufacturerDto", dto);
        return "manufacture/editManufacturer";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("manufacturerDto") ManufacturerDto dto,
                         BindingResult result) {

        if (result.hasErrors()) {
            return "manufacture/editManufacturer";
        }

        manufacturerService.update(id, dto);
        return "redirect:/manufacturers";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        manufacturerService.delete(id);
        return "redirect:/manufacturers";
    }
}
