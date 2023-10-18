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

        //REVERSE
        for (int i = 0; i < pricelistAll.size(); i++) {
            PricelistDTO pricelistDTO = modelMapper.map(pricelistAll.get(i), PricelistDTO.class);
            pricelistAllDTO.add(pricelistDTO);
        }
//        for (int i = pricelistAll.size(); i > 0; i--) {
//            PricelistDTO pricelistDTO = modelMapper.map(pricelistAll.get(i-1), PricelistDTO.class);
//            pricelistAllDTO.add(pricelistDTO);
//        }
        return pricelistAllDTO;
    }

    @Override
    public void pricelistSwap() {
        List<Pricelist> pricelistAll = pricelistRepository.findAll();
//        Collections.swap(pricelistAll, 0 ,1);
        pricelistAll.get(0).setId(2);
        pricelistAll.get(1).setId(1);
        pricelistRepository.save(pricelistAll.get(1));
        pricelistRepository.save(pricelistAll.get(0));
//        for (int i = 0; i < pricelistAll.size(); i++) {
//            pricelistRepository.save(pricelistAll.get(i));
//            log.info("Service: Updated Pricelist index: " + i +" successfully!");
//            log.info(pricelistAll.get(i).toString());
//        }
    }

    @Override
    public void updateDeadline(PricelistDTO pricelistDTO) {
        int lineTable = 1;
        pricelistDTO.setLast_change(new Date());
        Pricelist pricelist = modelMapper.map(pricelistDTO, Pricelist.class);
        pricelist.setId(lineTable);
        pricelistRepository.save(pricelist);
        log.info("Service: Updated Pricelist Deadline successfully!");
    }

    @Override
    public void updateCost(PricelistDTO pricelistDTO) {
        int lineTable = 2;
        pricelistDTO.setLast_change(new Date());
        Pricelist pricelist = modelMapper.map(pricelistDTO, Pricelist.class);
        pricelist.setId(lineTable);
        pricelistRepository.save(pricelist);
        log.info("Service: Updated Pricelist Cost service successfully!");
    }

    @Override
    public void updatePricelist(List<PricelistDTO> pricelistDTOList) {

        for (int i = 0; i < pricelistDTOList.size(); i++) {
            var pricelistDTO = pricelistDTOList.get(i);
            validationData(pricelistDTO);
            pricelistDTO.setLast_change(new Date());
            Pricelist pricelist = modelMapper.map(pricelistDTO, Pricelist.class);
            pricelist.setId(i+1);
            pricelistRepository.save(pricelist);
            log.info("Service: Updated Pricelist index: " + i +" successfully!");
        }
    }

    private void validationData(PricelistDTO pricelistDTO) {
        if (pricelistDTO.getMezhevaniye() == null) {
            pricelistDTO.setMezhevaniye("");
        }
        if (pricelistDTO.getTech_plan() == null) {
            pricelistDTO.setTech_plan("");
        }
        if (pricelistDTO.getAkt_inspection() == null) {
            pricelistDTO.setAkt_inspection("");
        }
        if (pricelistDTO.getScheme_location() == null) {
            pricelistDTO.setScheme_location("");
        }
        if (pricelistDTO.getTakeaway_borders() == null) {
            pricelistDTO.setTakeaway_borders("");
        }

//        if (pricelistDTO.getMezhevaniye() == "") {
//            pricelistDTO.setMezhevaniye(null);
//        }
//        if (pricelistDTO.getTech_plan() == "") {
//            pricelistDTO.setTech_plan(null);
//        }
//        if (pricelistDTO.getAkt_inspection() == "") {
//            pricelistDTO.setAkt_inspection(null);
//        }
//        if (pricelistDTO.getScheme_location() == "") {
//            pricelistDTO.setScheme_location(null);
//        }
//        if (pricelistDTO.getTakeaway_borders() == "") {
//            pricelistDTO.setTakeaway_borders(null);
//        }
    }
}
