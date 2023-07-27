package Boot.cadastreCompany.controllers;

import Boot.cadastreCompany.models.Client;
import Boot.cadastreCompany.service.HomeService;
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

    private HomeService service;

    @Autowired
    public mainController(HomeService service) {
        this.service = service;
    }

    @GetMapping()
    public String homePage(Model model) {
        model.addAttribute("client", new Client());
        return "homePage/2index";
    }

    @PostMapping()
    public String addClient(@ModelAttribute("client") @Valid Client client, BindingResult bindingResult) {
        //валидация
        if (bindingResult.hasErrors()) {
            return "homePage/index";
        }
        service.save(client);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "backPage/login";
    }

    @GetMapping("/in") // {name}
    public String adminPanel(Model model) {
        model.addAttribute("clients", service.getAllClient());

        return "backPage/adminPanel";
    }
}
