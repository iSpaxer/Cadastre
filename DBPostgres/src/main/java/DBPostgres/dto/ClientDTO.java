package DBPostgres.dto;


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
public class ClientDTO {

    @NotEmpty(message = "Поле пустое")
    @Size(min = 2, max = 40, message = "Введите ваше имя")
    private String name;

    @NotEmpty(message = "Поле пустое")
    @Size(min = 11, max = 11, message = "не корректный номер")
    private String phone;

}
