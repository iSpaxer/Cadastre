package DBPostgres.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "Client")
@Getter
@Setter
@NoArgsConstructor
public class Client {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "Поле пустое")
    @Size(min = 2, max = 15, message = "Введите ваше имя")
    private String name;

    @Column(name = "created_data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdData;

    @Column(name = "phone")
    @NotEmpty(message = "Поле пустое")
    @Size(min = 11, max = 11, message = "не корректный номер")
    private String phone;

    @ManyToOne
    @JoinColumn(name = "eng_id", referencedColumnName = "id")
    private Engineer engineer;

    public Client(String name, Date createdData, String phone) {
        this.name = name;
        this.createdData = createdData;
        this.phone = phone;
    }
}
