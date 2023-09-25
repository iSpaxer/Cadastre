package Boot.cadastreCompany.controllers;

import Boot.cadastreCompany.dto.ClientDTO;
import Boot.cadastreCompany.dto.EngineerDTO;
import Boot.cadastreCompany.exception.AuthenticationError;
import Boot.cadastreCompany.security.EngDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class MainController {


    @GetMapping()
    public String homePage(Model model) {
        model.addAttribute("client", new ClientDTO());
        ///TODO
        return "test";
    }

    @PostMapping()
    public String addClient(@ModelAttribute("client") @Valid ClientDTO clientDTO, BindingResult bindingResult) {
        //валидация
        if (bindingResult.hasErrors()) {
            ///TODO
            return "test";
        }

        //service.save(clientDTO);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody EngineerDTO engineerDTO, BindingResult bindingResult,
                                   HttpServletRequest request, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            throw new AuthenticationError("Invalid username or password", HttpStatus.UNAUTHORIZED.value());
        }

        try {
            request.login(engineerDTO.getLogin(), engineerDTO.getPassword());
        } catch (ServletException e) {
            throw new AuthenticationError("Invalid username or password", HttpStatus.UNAUTHORIZED.value());
        }

        var auth = (Authentication) request.getUserPrincipal();
        var engDetails = (EngDetails) auth.getPrincipal();
        var engDTO = engDetails.getEngineerDTO();

        //   rememberMeServices.loginSuccess(request, response, auth);

        //return new ResponseEntity<>("Successfully! " + engDTO.getLogin() ,HttpStatus.OK);
        return "/login";
    }

//    @GetMapping("/login2")
//    public String loginPage2() {
//        return "login";
//    }

    @GetMapping("/adminPanel") // {name}
    public String adminPanel(Model model) {
        //model.addAttribute("clients", service.getAllClient());

        return "backPage/adminPanel";
    }

}
