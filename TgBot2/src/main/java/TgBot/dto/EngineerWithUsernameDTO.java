package TgBot.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EngineerWithUsernameDTO {

    @NotEmpty(message = "login can't be equal to null")
    @Size(min = 5, message = "pls enter more than 3 characters")
    private String username;

    @NotEmpty(message = "password can't be equal null")
    @Size(min = 8, message = "pls enter more than 8 characters")
    private String password;

    public String toString() {
        return "Engineer have login: " + this.getUsername() + " and password: " + this.getPassword();
    }
}
