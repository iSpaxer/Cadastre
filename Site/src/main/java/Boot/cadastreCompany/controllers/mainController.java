package Boot.cadastreCompany.controllers;

import Boot.cadastreCompany.dto.ClientDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class mainController {


    @GetMapping()
    public String homePage(Model model) {
        model.addAttribute("client", new ClientDTO());
        ///TODO
        return "/test";
    }

    @PostMapping()
    public String addClient(@ModelAttribute("client") @Valid ClientDTO clientDTO, BindingResult bindingResult) {
        //валидация
        if (bindingResult.hasErrors()) {
            ///TODO
            return "/test";
        }

        //service.save(clientDTO);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "backPage/login";
    }

    @GetMapping("/adminPanel") // {name}
    public String adminPanel(Model model) {
        //model.addAttribute("clients", service.getAllClient());

        return "backPage/adminPanel";
    }
}
