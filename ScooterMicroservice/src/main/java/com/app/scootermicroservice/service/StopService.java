package com.app.scootermicroservice.service;


import com.app.scootermicroservice.domain.Stop;

import com.app.scootermicroservice.dto.StopRequestDTO;
import com.app.scootermicroservice.dto.StopResponseDTO;
import com.app.scootermicroservice.repository.StopRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StopService {
    private final StopRepository stopRepository;

    public StopService(StopRepository stopRepository) {
        this.stopRepository = stopRepository;
    }

    @Transactional
    public StopResponseDTO save(StopRequestDTO requestDTO) throws Exception{
        var stop = new Stop(requestDTO);
        stop = this.stopRepository.save(stop);
        return new StopResponseDTO(stop);
    }
    public List<StopResponseDTO> findAll() {
        return this.stopRepository.findAll().stream()
                .map(stop -> new StopResponseDTO(stop.getId(), stop.getLatitude(),
                        stop.getLongitude()))
                .toList();

    }
    public Optional<StopResponseDTO> findById(Long id) {
        return this.stopRepository.findById(id).map(stop -> new StopResponseDTO(stop.getId(), stop.getLatitude(),
                stop.getLongitude()));

    }
    @Transactional
    public Optional<Stop> deleteById(Long id) {
        Optional<Stop> entityToDelete = stopRepository.findById(id);
        entityToDelete.ifPresent(entity -> stopRepository.deleteById(id));
        return entityToDelete;
    }
    @Transactional
    public Optional<Stop>updateLongitudeById(Long id,  Double longitude){
        Optional<Stop> entityToUpdate = stopRepository.findById(id);
        entityToUpdate.ifPresent(entity -> stopRepository.updateLongitudeById(id,longitude));
        return entityToUpdate;
    }
    public  List<StopResponseDTO> getStopsNear(Double latitude, Double longitude){
        Double latitudeMin=latitude-200;
        Double latitudeMax=latitude+200;
        Double longitudeMin=longitude-200;
        Double longitudeMax=longitude+200;
        return this.stopRepository.getStopsNear(latitudeMin, latitudeMax, longitudeMin, longitudeMax).stream().map(stop -> new StopResponseDTO(stop.getId(), stop.getLatitude(),
                        stop.getLongitude()))
                .toList();

    }

}
