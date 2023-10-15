package Boot.cadastreCompany.dto;

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
public class EngineerUpdatePasswordDTO {

    private String login;

    @NotEmpty(message = "password can't be equal null")
    @Size(min = 8, message = "pls enter more than 8 characters")
    private String oldPassword;

    @NotEmpty(message = "password can't be equal null")
    @Size(min = 8, message = "pls enter more than 8 characters")
    private String newPassword;

    public String toString() {
        return "Engineer have login: " + this.getLogin() + " and old password: "
                + this.getOldPassword() + " and new password " + this.getNewPassword();
    }
}
