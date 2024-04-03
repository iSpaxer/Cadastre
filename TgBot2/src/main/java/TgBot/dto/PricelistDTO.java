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
public class PricelistDTO {

    private String mezhevaniye;

    private String tech_plan;

    private String akt_inspection;

    private String scheme_location;

    private String takeaway_borders;

    private Date last_change;
}
