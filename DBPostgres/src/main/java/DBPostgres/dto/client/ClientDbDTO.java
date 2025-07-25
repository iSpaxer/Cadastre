package DBPostgres.dto.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDbDTO {
    private Integer id;
    private String name;
    private Date createdData;
    private String phone;
    private String engName;
}
