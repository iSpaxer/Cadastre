package Boot.cadastreCompany.utils;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

public class AuxiliaryBean {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
