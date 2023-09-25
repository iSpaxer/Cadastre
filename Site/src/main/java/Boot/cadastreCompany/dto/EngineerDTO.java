package Boot.cadastreCompany.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EngineerDTO {

    @NotEmpty(message = "login can't be equal to null")
    @Size(min = 3, message = "pls enter more than 3 characters")
    private String login;

    @NotEmpty(message = "password can't be equal null")
    @Size(min = 8, message = "pls enter more than 8 characters")
    private String password;

    public String toString() {
        return "Engineer have login: " + this.getLogin() + " and password: " + this.getPassword();
    }

}
