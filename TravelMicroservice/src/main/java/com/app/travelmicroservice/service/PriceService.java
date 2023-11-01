package com.app.travelmicroservice.service;

import com.app.travelmicroservice.domain.Price;
import com.app.travelmicroservice.dto.PriceRequestDTO;
import com.app.travelmicroservice.dto.PriceResponseDTO;
import com.app.travelmicroservice.repository.PriceRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PriceService {
    private final PriceRepository priceRepository;

    public PriceService(PriceRepository priceRepository) {
        super();
        this.priceRepository = priceRepository;
    }
    @Transactional
    public PriceResponseDTO save(PriceRequestDTO requestDTO) throws Exception {
        var price = new Price(requestDTO);
        price = this.priceRepository.save( price );
        return new PriceResponseDTO(price);
    }

    public List<PriceResponseDTO> findAll() {
        return this.priceRepository.findAll().stream()
                .map(price -> new PriceResponseDTO(price.getDate(), price.getRegularFee(),
                        price.getExtraFee()))
                .toList();

    }
    public Optional<PriceResponseDTO> findById(Long id) {
        return this.priceRepository.findById(id).map(price -> new PriceResponseDTO(price.getDate(), price.getRegularFee(),
                price.getExtraFee()));

    }
    @Transactional
    public Optional<Price> deleteById(Long id) {
        Optional<Price> entityToDelete = priceRepository.findById(id);
        entityToDelete.ifPresent(entity -> priceRepository.deleteById(id));
        return entityToDelete;
    }
    @Transactional
    public Optional<Price>updateRegularFeeById(Long id, Double regularFee){
        Optional<Price> entityToUpdate = priceRepository.findById(id);
        entityToUpdate.ifPresent(entity -> priceRepository.updateRegularFeeById(id,regularFee));
        return entityToUpdate;
    }
}
