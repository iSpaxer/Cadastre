package TgBot.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tgbot")
public class WebController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
