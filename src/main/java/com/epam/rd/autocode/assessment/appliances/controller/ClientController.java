    package com.epam.rd.autocode.assessment.appliances.controller;


    import com.epam.rd.autocode.assessment.appliances.Dtos.ClientDto;
    import com.epam.rd.autocode.assessment.appliances.model.Client;
    import com.epam.rd.autocode.assessment.appliances.service.impl.ClientServiceImpl;
    import jakarta.validation.Valid;
    import lombok.RequiredArgsConstructor;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.validation.BindingResult;
    import org.springframework.web.bind.annotation.*;

    @Controller
    @RequiredArgsConstructor
    @RequestMapping("/clients")
    public class ClientController {
        private final ClientServiceImpl clientService;

        @GetMapping
        public String getAll(Model model) {
            model.addAttribute("clients", clientService.getAll());
            return "client/clients";
        }

        @GetMapping("/add")
        public String addForm(Model model) {
            model.addAttribute("client", new ClientDto());
            return "client/newClient";
        }

        @PostMapping("/add-client")
        public String create(@Valid @ModelAttribute("client") ClientDto clientDto,
                             BindingResult result) {

            if (result.hasErrors()) {
                return "client/newClient";
            }

            clientService.create(clientDto);
            return "redirect:/clients";
        }

        @GetMapping("/{id}/delete")
        public String delete(@PathVariable Long id) {
            clientService.delete(id);
            return "redirect:/clients";
        }

        @GetMapping("/edit/{id}")
        public String editClientForm(@PathVariable Long id, Model model) {
            ClientDto client = clientService.getById(id);
            model.addAttribute("client", client);
            return "client/client-edit";
        }

        @PostMapping("/edit/{id}")
        public String updateClient(@PathVariable Long id,
                                   @ModelAttribute("client") @Valid ClientDto clientDto,
                                   BindingResult result) {

            if (result.hasErrors()) {
                return "client/client-edit";
            }

            clientService.update(id, clientDto);
            return "redirect:/clients";
        }

    }
