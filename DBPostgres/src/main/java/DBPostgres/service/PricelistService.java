package DBPostgres.service;

import DBPostgres.dto.pricelist.PricelistDTO;

import java.util.List;

public interface PricelistService {
    List<PricelistDTO> getPricelist();

    void updateDeadline(PricelistDTO pricelistDTO);

    void updateCost(PricelistDTO pricelistDTO);

    void updatePricelist(List<PricelistDTO> pricelistDTOList);

    void pricelistSwap();
}
