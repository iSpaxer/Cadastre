package DBPostgres.dto.engineer;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EngineerLoginDTO {
    @NotEmpty(message = "login can't be equal to null")
    @Size(min = 3, message = "pls enter more than 3 characters")
    private String login;

    @NotEmpty(message = "password can't be equal null")
    @Size(min = 8, message = "pls enter more than 8 characters")
    private String password;

    @NotEmpty(message = "eng_name can't be equal to null")
    @Size(min = 3, max = 12, message = "The name can be no more than 12 letters and can be more then 3 letters")
    private String name;

}
