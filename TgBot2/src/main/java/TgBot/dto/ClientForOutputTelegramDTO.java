package TgBot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientForOutputTelegramDTO {
    private int id;
    private String name;
    private Date createdData;
    private String phone;
    private String engineerName;
}
