package com.epam.rd.autocode.assessment.appliances.controller;

import com.epam.rd.autocode.assessment.appliances.Dtos.ApplianceDto;
import com.epam.rd.autocode.assessment.appliances.model.Category;
import com.epam.rd.autocode.assessment.appliances.model.PowerType;
import com.epam.rd.autocode.assessment.appliances.service.ManufacturerService;
import com.epam.rd.autocode.assessment.appliances.service.impl.ApplianceServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/appliances")
public class ApplianceController {

    private final ApplianceServiceImpl applianceService;
    private final ManufacturerService manufacturerService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("appliances", applianceService.getAll());
        model.addAttribute("manufacturers", manufacturerService.getAll());
        return "appliance/appliances";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("applianceDto", new ApplianceDto());
        model.addAttribute("categories", Category.values());
        model.addAttribute("powerTypes", PowerType.values());
        model.addAttribute("manufacturers", manufacturerService.getAll());
        return "appliance/newAppliance";
    }

    @PostMapping("/add-appliance")
    public String create(@Valid @ModelAttribute("applianceDto") ApplianceDto applianceDto,
                         BindingResult result,
                         Model model) {

        if (result.hasErrors()) {
            model.addAttribute("categories", Category.values());
            model.addAttribute("powerTypes", PowerType.values());
            model.addAttribute("manufacturers", manufacturerService.getAll());
            return "appliance/newAppliance";
        }

        applianceService.create(applianceDto);
        return "redirect:/appliances";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        applianceService.delete(id);
        return "redirect:/appliances";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {

        ApplianceDto dto = applianceService.get(id);

        model.addAttribute("applianceDto", dto);
        model.addAttribute("categories", Category.values());
        model.addAttribute("powerTypes", PowerType.values());
        model.addAttribute("manufacturers", manufacturerService.getAll());

        return "appliance/editAppliance";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("applianceDto") ApplianceDto applianceDto,
                         BindingResult result,
                         Model model) {

        if (result.hasErrors()) {
            model.addAttribute("categories", Category.values());
            model.addAttribute("powerTypes", PowerType.values());
            model.addAttribute("manufacturers", manufacturerService.getAll());
            return "appliance/editAppliance";
        }

        applianceService.update(id, applianceDto);
        return "redirect:/appliances";
    }


}
