package TgBot.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EngineerTelegramDTO {

    @NotEmpty(message = "login can't be equal to null")
    @Size(min = 5, message = "pls enter more than 3 characters")
    private String login;

    @NotEmpty(message = "password can't be equal null")
    @Size(min = 8, message = "pls enter more than 8 characters")
    private String password;

    private Long tgId;

    public EngineerTelegramDTO(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String toString() {
        return "Engineer have login: " + this.getLogin() + " and password: " + this.getPassword()
                + " and telegram_id: " + this.getTgId();
    }
}

