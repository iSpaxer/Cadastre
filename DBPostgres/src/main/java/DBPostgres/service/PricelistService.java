package DBPostgres.service;

import DBPostgres.dto.PricelistDTO;

import java.util.List;

public interface PricelistService {
    List<PricelistDTO> getPricelist();

    void updateDeadline(PricelistDTO pricelistDTO);
}
