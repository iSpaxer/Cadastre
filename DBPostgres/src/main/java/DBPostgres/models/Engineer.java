package DBPostgres.models;

import DBPostgres.dto.EngineerDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Engineer")
@Getter
@Setter
@NoArgsConstructor
public class Engineer {

    @Id
    @Column(name = "eng_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int eng_id;

    @Column(name = "eng_name")
    @NotEmpty(message = "eng_name can't be equal to null")
    private String eng_name;

    @Column(name = "login")
    @NotEmpty(message = "login can't be equal to null")
    @Size(min = 3, message = "pls enter more than 3 characters")
    private String login;

    @Column(name = "password")
    @NotEmpty(message = "password can't be equal null")
    @Size(min = 8, message = "pls enter more than 8 characters")
    private String password;

    public Engineer(String eng_name, String login, String password) {
        this.eng_name = eng_name;
        this.login = login;
        this.password = password;
    }

    public EngineerDTO mappingEngineerDTO() {
        return new EngineerDTO(login, password);
    }
}
