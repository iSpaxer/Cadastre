package DBPostgres.dto;

import DBPostgres.models.Client;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EngineerDbDTO {
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
    private int tgId;

    ///TODO ?
    @JsonBackReference
    private List<Client> clientList;

}
