package DBPostgres.models;

import DBPostgres.dto.EngineerDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Engineer")
@Getter
@Setter
@NoArgsConstructor
public class Engineer {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "eng_name can't be equal to null")
    @Size(min = 3, max = 12, message = "The name can be no more than 12 letters and can be more then 3 letters")
    private String name;

    @Column(name = "login")
    @NotEmpty(message = "login can't be equal to null")
    @Size(min = 3, message = "pls enter more than 3 characters")
    private String login;

    @Column(name = "password")
    @NotEmpty(message = "password can't be equal null")
    @Size(min = 8, message = "pls enter more than 8 characters")
    private String password;

    @Column(name = "tgId")
    private int tgId;

    @OneToMany(mappedBy = "engineer")
    private List<Client> clientList;

    public Engineer(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public EngineerDTO mappingEngineerDTO() {
        return new EngineerDTO(login, password);
    }
}
