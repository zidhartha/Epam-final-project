package com.epam.rd.autocode.assessment.appliances.controller;

import com.epam.rd.autocode.assessment.appliances.Dtos.ClientDto;
import com.epam.rd.autocode.assessment.appliances.Dtos.EmployeeDto;
import com.epam.rd.autocode.assessment.appliances.Dtos.OrdersDto;
import com.epam.rd.autocode.assessment.appliances.config.ModelMapperConfig;
import com.epam.rd.autocode.assessment.appliances.model.Client;
import com.epam.rd.autocode.assessment.appliances.model.Employee;
import com.epam.rd.autocode.assessment.appliances.model.Orders;
import com.epam.rd.autocode.assessment.appliances.service.EmployeeService;
import com.epam.rd.autocode.assessment.appliances.service.impl.ApplianceServiceImpl;
import com.epam.rd.autocode.assessment.appliances.service.impl.ClientServiceImpl;
import com.epam.rd.autocode.assessment.appliances.service.impl.EmployeeServiceImpl;
import com.epam.rd.autocode.assessment.appliances.service.impl.OrderServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrdersController {

    private final OrderServiceImpl orderService;
    private final ClientServiceImpl clientService;
    private final EmployeeServiceImpl employeeService;
    private final ApplianceServiceImpl applianceService;


    @GetMapping("/add")
    public String add(Model model) {

        var auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        boolean isEmployee = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_EMPLOYEE"));

        OrdersDto order = new OrdersDto();

        model.addAttribute("employees", employeeService.getAll());

        if (isEmployee) {
            // EMPLOYEE: choose both client + employee
            model.addAttribute("order", order);
            model.addAttribute("clients", clientService.getAll());
            model.addAttribute("employeeMode", true); // flag for Thymeleaf
        } else {
            // CLIENT: choose only employee
            ClientDto client = clientService.findByEmail(email);
            order.setClientId(client.getId());

            model.addAttribute("order", order);
            model.addAttribute("employeeMode", false);
            model.addAttribute("clientName", client.getName());
        }

        return "order/newOrder";
    }


    @PostMapping("/add")
    public String create(@Valid @ModelAttribute("order") OrdersDto dto,
                         BindingResult result,
                         Model model) {

        if (result.hasErrors()) {
            model.addAttribute("employees", employeeService.getAll());

            // Re-render client list only for employees
            var auth = SecurityContextHolder.getContext().getAuthentication();
            boolean isEmployee = auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_EMPLOYEE"));

            if (isEmployee) {
                model.addAttribute("clients", clientService.getAll());
                model.addAttribute("employeeMode", true);
            } else {
                model.addAttribute("employeeMode", false);
            }

            return "order/newOrder";
        }

        // If CLIENT is logged in → auto-assign clientId
        var auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isEmployee = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_EMPLOYEE"));

        if (!isEmployee) {
            String email = auth.getName();
            ClientDto client = clientService.findByEmail(email);
            dto.setClientId(client.getId());
        }

        orderService.create(dto);
        return "redirect:/orders";
    }



    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        OrdersDto dto = orderService.getById(id);
        model.addAttribute("order", dto);
        model.addAttribute("rows", dto.getOrderRows());

        var auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isEmployee = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_EMPLOYEE"));

        if (isEmployee) {
            model.addAttribute("employees", employeeService.getAll());
            model.addAttribute("employeeMode", true);
        } else {
            model.addAttribute("employeeMode", false);
        }

        return "order/editOrder";
    }
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        OrdersDto dto = orderService.getById(id);

        if(dto.isApproved()){
            return "redirect:/custom-error?msg=Cannot delete an approved order";
        }
        orderService.delete(id);
        return "redirect:/orders";
    }

    @GetMapping("/{id}/approve")
    public String approve(@PathVariable Long id) {
        orderService.setApproved(id, true);
        return "redirect:/orders";
    }

    @GetMapping("/{id}/unapprove")
    public String unapprove(@PathVariable Long id) {
        orderService.setApproved(id, false);
        return "redirect:/orders";
    }

    @GetMapping("/{id}/choice-appliance")
    public String chooseAppliance(@PathVariable("id") Long orderId, Model model) {
        model.addAttribute("appliances", applianceService.getAll());
        model.addAttribute("orderId", orderId);
        return "order/choiceAppliance";
    }

    @PostMapping("/{id}/add-into-order")
    public String addIntoOrder(
            @PathVariable("id") Long orderId,
            @RequestParam("applianceId") Long applianceId,
            @RequestParam("numbers") Long quantity
    ) {
        orderService.addRow(orderId, applianceId, quantity);
        return "redirect:/orders/" + orderId + "/edit";
    }

    @GetMapping()
    public String getAll(Model model) {

        var auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        boolean isEmployee = auth.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_EMPLOYEE"));

        if (isEmployee) {
            model.addAttribute("orders", orderService.getAll());
        } else {
            model.addAttribute("orders", orderService.getClientOrders(email));
        }

        return "order/orders";
    }

    // for removing a singular row of the order.
    @GetMapping("/{orderId}/remove-row/{rowId}")
    public String removeRow(
            @PathVariable Long orderId,
            @PathVariable Long rowId) {

        OrdersDto ordersDto = orderService.getById(orderId);
        if (ordersDto.isApproved()) {
             return "redirect:/custom-error";
        }

        orderService.removeRow(orderId, rowId);

        return "redirect:/orders/" + orderId + "/edit";
    }

}

