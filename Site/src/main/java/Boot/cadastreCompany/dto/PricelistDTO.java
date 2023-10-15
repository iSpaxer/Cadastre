package Boot.cadastreCompany.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PricelistDTO {

    @NotEmpty(message = "Поле 'mezhevaniye' пустое")
    private String mezhevaniye;

    @NotEmpty(message = "Поле 'tech_plan' пустое")
    private String tech_plan;

    @NotEmpty(message = "Поле 'akt_inspection' пустое")
    private String akt_inspection;

    @NotEmpty(message = "Поле 'scheme_location' пустое")
    private String scheme_location;

    @NotEmpty(message = "Поле 'takeaway_borders' пустое")
    private String takeaway_borders;

    private Date last_change;
}
