package DBPostgres.service.impl;

import DBPostgres.dto.PricelistDTO;
import DBPostgres.models.Pricelist;
import DBPostgres.repositories.PricelistRepository;
import DBPostgres.service.PricelistService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class PricelistServiceImpl implements PricelistService {
    private PricelistRepository pricelistRepository;
    private ModelMapper modelMapper;

    @Autowired
    public PricelistServiceImpl(PricelistRepository pricelistRepository, ModelMapper modelMapper) {
        this.pricelistRepository = pricelistRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<PricelistDTO> getPricelist() {
        List<Pricelist> pricelistAll = pricelistRepository.findAll();
        System.out.println(pricelistAll);
        List<PricelistDTO> pricelistAllDTO = new ArrayList<>();
        for (Pricelist pricelist : pricelistAll) {
            PricelistDTO pricelistDTO = modelMapper.map(pricelist, PricelistDTO.class);
            pricelistAllDTO.add(pricelistDTO);
        }
        Collections.swap(pricelistAllDTO, 0 ,1);
        return pricelistAllDTO;
    }

    @Override
    public void updateDeadline(PricelistDTO pricelistDTO) {
        pricelistDTO.setLast_change(new Date());
        Pricelist pricelist = modelMapper.map(pricelistDTO, Pricelist.class);
        pricelist.setId(1);
        pricelistRepository.save(pricelist);
        log.info("Service: Updated Pricelist Deadline successfully!");
    }
}
