package Boot.cadastreCompany.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
@RequestMapping("/tgbot")
public class TelegramWebController {
    @GetMapping("/login")
    public String login() {
        return "tgbot/login";
    }

    @GetMapping("/getClients")
    public String getClients() {
        return "tgbot/getClients";
    }

    @GetMapping("/setPrice")
    public String test() {
        return "tgbot/setPrice";
    }
}
