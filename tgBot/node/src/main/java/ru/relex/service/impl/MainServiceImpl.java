package ru.relex.service.impl;

//import Boot.cadastreCompany.repositories.HomeRepository;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;

import ru.relex.models.TgEngineerUser;
import ru.relex.repositories.TgRepository;
import ru.relex.service.MainService;
import ru.relex.service.ProducerService;

import java.util.List;

import static ru.relex.models.enums.UserState.BASIC_STATE;

@Getter
@Setter
class Registration {
    public String login;
    public String password;

    public Registration(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
@Service
@Log4j
//@SpringBootApplication(scanBasePackages={"Boot"})
//@SpringBootApplication(scanBasePackages = "Boot.cadastreCompany.repositories")
//@EnableJpaRepositories("Boot")
//@ComponentScan(basePackages = { "Boot" })
//@EntityScan("Boot")
//@ComponentScan(basePackages = {"Boot.cadastreCompany.service"})
public class MainServiceImpl implements MainService {

    private ProducerService producerService;
    private TgRepository tgRepository;
    private Registration registration;

    public MainServiceImpl(ProducerService producerService, TgRepository tgRepository) {
        this.producerService = producerService;
        this.tgRepository = tgRepository;
        this.registration = new Registration("", "");

    }

    @Override
    public void processTextMessage(Update update) {

//        TgEngineerUser tgEngineerUser = findOrSaveTgEngineerUser(update);
//
//        UserState userState = tgEngineerUser.getState();
//        String text = update.getMessage().getText();
//        String output = "";
//
//        if (CANCEL.equals(text)) {
//            //output = cancelProcess();
//        } else if (BASIC_STATE.equals(userState)) {
//
//        }
        //String s = homeService.getAllClient().toString();
        Message message = update.getMessage();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText("getTestAll.toString()");

        producerService.producerAnswer(sendMessage);

        producerService.producerAnswerClient();
    }

    private TgEngineerUser findOrSaveTgEngineerUser(Update update) {
        Message textMessage = update.getMessage();
        User tgEngineerUser = textMessage.getFrom();

        TgEngineerUser persistentEngineerUser = tgRepository.findByTelegramUserId(tgEngineerUser.getId());
        if (persistentEngineerUser == null) {
            TgEngineerUser transientEngineerUser = TgEngineerUser.builder()
                        .telegramUserId(tgEngineerUser.getId())
                        .username(tgEngineerUser.getUserName())
                        // TODO можно добавить регистрацию через эмейл
                        .isActive(false)
                        .state(BASIC_STATE)
                        .build();
            return transientEngineerUser;
            //TODO проверка логина и пароля
//            if (loginAnPasswordVerification() == true) {
//                TgEngineerUser transientEngineerUser = TgEngineerUser.builder()
//                        .telegramUserId(tgEngineerUser.getId())
//                        .username(tgEngineerUser.getUserName())
//                        // TODO можно добавить регистрацию через эмейл
//                        .isActive(true)
//                        .state(UserState.DB_ACCESS_IS_ALLOWED)
//                        .build();
//                return tgRepository.save(transientEngineerUser);
//            }
//            else {
//                //TODO
//                return null;
//            }
        }
        return persistentEngineerUser;
    }

    @Override
    public void spam(String strSpam) {
        List<TgEngineerUser> userList = tgRepository.findAll();
        for (TgEngineerUser user : userList) {

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(user.getTelegramUserId());
            sendMessage.setText(strSpam);
            producerService.producerAnswer(sendMessage);
        }
    }
}
