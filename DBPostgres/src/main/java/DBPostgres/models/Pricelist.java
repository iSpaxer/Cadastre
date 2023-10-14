package DBPostgres.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "Pricelist")
@Getter
@Setter
@NoArgsConstructor
public class Pricelist {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "mezhevaniye")
    @NotEmpty(message = "Поле 'mezhevaniye' пустое")
    private String mezhevaniye;

    @Column(name = "tech_plan")
    @NotEmpty(message = "Поле 'tech_plan' пустое")
    private String tech_plan;

    @Column(name = "akt_inspection")
    @NotEmpty(message = "Поле 'akt_inspection' пустое")
    private String akt_inspection;

    @Column(name = "scheme_location")
    @NotEmpty(message = "Поле 'scheme_location' пустое")
    private String scheme_location;

    @Column(name = "takeaway_borders")
    @NotEmpty(message = "Поле 'takeaway_borders' пустое")
    private String takeaway_borders;

    @Column(name = "lastChange")
//    @NotEmpty(message = "Поле 'lastChange' пустое")
    @Temporal(TemporalType.TIMESTAMP)
    private Date last_change;

    public Pricelist(String mezhevaniye, String tech_plan, String akt_inspection,
                     String scheme_location, String takeaway_borders, Date last_change) {
        this.mezhevaniye = mezhevaniye;
        this.tech_plan = tech_plan;
        this.akt_inspection = akt_inspection;
        this.scheme_location = scheme_location;
        this.takeaway_borders = takeaway_borders;
        this.last_change = last_change;
    }
}
