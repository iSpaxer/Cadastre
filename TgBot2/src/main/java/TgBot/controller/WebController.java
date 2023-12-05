package TgBot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@CrossOrigin
@RequestMapping("/tgbot/web")
public class WebController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/getClients")
    public String getClients() {
        return "getClients";
    }

    @GetMapping("/test")
    public String test() {
        return "index";
    }
}
