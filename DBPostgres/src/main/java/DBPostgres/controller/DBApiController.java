package DBPostgres.controller;

import DBPostgres.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.models.Client;

import java.util.List;

@RestController
@RequestMapping("DB/")
public class DBApiController {
    private HomeService homeService;

    @Autowired
    public DBApiController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping("allClients")
    public List<Client> clientList() {
        return homeService.getAllClient();
    }
}
