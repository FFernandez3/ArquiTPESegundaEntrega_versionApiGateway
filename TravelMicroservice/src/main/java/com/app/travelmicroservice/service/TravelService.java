package com.app.travelmicroservice.service;

import com.app.travelmicroservice.domain.Travel;
import com.app.travelmicroservice.dto.TravelRequestDTO;
import com.app.travelmicroservice.dto.TravelResponseDTO;
import com.app.travelmicroservice.repository.PriceRepository;
import com.app.travelmicroservice.repository.TravelRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TravelService {
    private final TravelRepository travelRepository;
    private final PriceRepository priceRepository;
    private final RestTemplate restTemplate;



   /* @Transactional
    public TravelResponseDTO save(TravelRequestDTO requestDTO) throws Exception {
        try {
            var travel = new Travel(requestDTO);
            final var price = this.priceRepository.findById( requestDTO.getPriceId() ).orElseThrow(); //retorna price
            travel.setPrice(price);
            travel= this.travelRepository.save(travel);
            return new TravelResponseDTO(travel);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }*/

    @Transactional
    public TravelResponseDTO save(TravelRequestDTO requestDTO) throws Exception {
        try {
            var travel = new Travel(requestDTO);
            final var price = this.priceRepository.findById(requestDTO.getPriceId())
                    .orElseThrow(() -> new EntityNotFoundException("Price not found")); // Lanza una excepción específica
            travel.setPrice(price);
            travel = this.travelRepository.save(travel);
            return new TravelResponseDTO(travel);
        } catch (EntityNotFoundException e) {
            // Manejar excepción específica de entidad no encontrada
            throw new EntityNotFoundException("Price not found");
        } catch (Exception e) {
            // Manejar excepciones generales
            throw new Exception("An error occurred while saving the travel: " + e.getMessage());
        }
    }


    public List<TravelResponseDTO> findAll() {
        return this.travelRepository.findAll().stream()
                .map(travel -> new TravelResponseDTO(travel.getId(), travel.getIdUser(),
                        travel.getIdScooter(), travel.getStartDateTime(), travel.getFinishDateTime(), travel.getPause(), travel.getPrice(), travel.getKilometers()))
                .toList();

    }
    public Optional<TravelResponseDTO> findById(Long id) {
        return this.travelRepository.findById(id).map(travel -> new TravelResponseDTO(travel.getId(), travel.getIdUser(),
                travel.getIdScooter(), travel.getStartDateTime(), travel.getFinishDateTime(), travel.getPause(), travel.getPrice(), travel.getKilometers()));

    }
    @Transactional
    public Optional<Travel> deleteById(Long id) {
        Optional<Travel> entityToDelete = travelRepository.findById(id);
        entityToDelete.ifPresent(entity -> travelRepository.deleteById(id));
        return entityToDelete;
    }
    @Transactional
    public int updateScooterById(Long id, Long idScooter){
       // Optional<Travel> entityToUpdate = travelRepository.findById(id);
       // entityToUpdate.ifPresent(entity -> travelRepository.updateScooterById(id,idScooter));
        return travelRepository.updateScooterById(id, idScooter);
    }

    //metodo para calcular duracion total del viaje
    public long getTotalTime(LocalDateTime startDateTime, LocalDateTime finishDateTime){
        Duration time = Duration.between(startDateTime, finishDateTime);
        return time.toMinutes();
    }

    //metodo para calcular el precio total del viaje
    public Double getTotalCost(Long id){
        Optional<Travel> t1 = this.travelRepository.findById(id);
        if(t1.isPresent()){
            Travel travelFinded = t1.get();
            long minutes = this.getTotalTime(travelFinded.getStartDateTime(), travelFinded.getFinishDateTime());
            if(travelFinded.getPause() < 15){
                return minutes *travelFinded.getPrice().getRegularFee();
            }else
                return (minutes + travelFinded.getPause()) * travelFinded.getPrice().getExtraFee();
        }else
            return 0.0;
    }

    public List<TravelResponseDTO>getTravelsByScooterId(Long id){
        return  this.travelRepository.getTravelsByScooterId(id).stream().map(travel -> new TravelResponseDTO(travel.getId(), travel.getIdUser(),
                travel.getIdScooter(), travel.getStartDateTime(), travel.getFinishDateTime(), travel.getPause(), travel.getPrice(), travel.getKilometers())).toList();
       //return this.travelRepository.getTravelsByScooterId(id);
    }
    public  List<TravelResponseDTO>getTravelsByMonthAndYear(Integer month1, Integer month2, Integer year){
        if (month1<month2){
           return this.travelRepository.getTravelsByMonthAndYear(month1, month2, year).stream().map(travel -> new TravelResponseDTO(travel.getId(), travel.getFinishDateTime(), travel.getPause(), travel.getKilometers(), travel.getPrice())).toList();

        }
        else {
            return this.travelRepository.getTravelsByMonthAndYear(month2, month1, year).stream().map(travel -> new TravelResponseDTO(travel.getId(), travel.getFinishDateTime(), travel.getPause(), travel.getKilometers(), travel.getPrice())).toList();

        }

    }
    public Double getTotalInvoicedByDate(Integer month1, Integer month2, Integer year){
        List<TravelResponseDTO> travels=this.getTravelsByMonthAndYear(month1, month2, year);
        Double sum=0.0;
        if (!travels.isEmpty()){
            for (int i=0; i<travels.size();i++){
                Long id= travels.get(i).getId();
                sum+=this.getTotalCost(id);

            }

        }
        return sum;
    }

    @Transactional
    public int updateFinishDateTimeById(Long id, LocalDateTime finishDateTime){
        //Optional<Travel> entityToUpdate = travelRepository.findById(id);
        //entityToUpdate.ifPresent(entity -> travelRepository.updateFinishDateTimeById(id,finishDateTime));
        return this.travelRepository.updateFinishDateTimeById(id, finishDateTime);
    }

    public List<Tuple> getTravelsByYear(Long year, Long quantity){
        return this.travelRepository.getTravelsByYear(year, quantity);
    }

   /* public Long getIdScooter(Long id){
        this.restTemplate.getForEntity("http://localhost:8082/api/scooters"+id, );

    }*/

    }


