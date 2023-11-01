package com.app.scootermicroservice.service;

import com.app.scootermicroservice.domain.Scooter;
import com.app.scootermicroservice.domain.Stop;
import com.app.scootermicroservice.dto.ScooterResponseDTO;

import com.app.scootermicroservice.dto.ScooterRequestDTO;
import com.app.scootermicroservice.dto.TravelResponseDTO;
import com.app.scootermicroservice.repository.ScooterRepository;
import com.app.scootermicroservice.repository.StopRepository;
import jakarta.persistence.Tuple;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class ScooterService {
    private final ScooterRepository scooterRepository;
    private final StopRepository stopRepository;
    private final RestTemplate restTemplate;

    public ScooterService(ScooterRepository scooterRepository,StopRepository stopRepository, RestTemplate restTemplate) {
        this.scooterRepository = scooterRepository;
        this.stopRepository=stopRepository;
        this.restTemplate=restTemplate;
    }


    public List<ScooterResponseDTO> findAll() {
        return this.scooterRepository.findAll().stream()
                .map(scooter -> new ScooterResponseDTO(scooter.getId(), scooter.getKilometersTraveled(),
                        scooter.getTimeWithBreaks(), scooter.getTimeWithoutBreaks(), scooter.isAvailable(),
                        scooter.getLatitude(), scooter.getLongitude(),
                        scooter.getLastMaintenance()))
                .toList();

    }
    public Optional<ScooterResponseDTO> findById(Long id) {
        return this.scooterRepository.findById(id).map(scooter -> new ScooterResponseDTO(scooter.getId(),
                scooter.getKilometersTraveled(),
                scooter.getTimeWithBreaks(), scooter.getTimeWithoutBreaks(),
                scooter.isAvailable(), scooter.getLatitude(), scooter.getLongitude()));

    }
    public List<Long>getScooterIds(){
        return this.scooterRepository.getScooterIds();

    }
    @Transactional
    public Optional<Scooter> deleteById(Long id) {
        Optional<Scooter> entityToDelete = scooterRepository.findById(id);
        entityToDelete.ifPresent(entity -> scooterRepository.deleteById(id));
        return entityToDelete;
    }
    @Transactional
    public int updateIsAvailableById(Long id, boolean isAvailable){
        //Optional<Scooter> entityToUpdate = scooterRepository.findById(id);
        //entityToUpdate.ifPresent(entity -> scooterRepository.updateIsAvailableById(id,isAvailable));
        return this.scooterRepository.updateIsAvailableById(id, isAvailable);
    }

    @Transactional
    public ScooterResponseDTO save(ScooterRequestDTO requestDTO ){
        var scooter = new Scooter(requestDTO);
        final var stop = this.stopRepository.findById( requestDTO.getStopID() ).orElseThrow(); //retorna Stop
        scooter.setStop( stop );
        scooter = this.scooterRepository.save( scooter );
        return new ScooterResponseDTO( scooter );
    }
    public List<TravelResponseDTO> getAllTravels(Long id)  throws Exception {
        try{
            List<TravelResponseDTO> travels=null;
            ResponseEntity<TravelResponseDTO[]> responseEntity=restTemplate.getForEntity("http://localhost:8081/api/travels/byScooterId/" + id, TravelResponseDTO[].class);
            if(responseEntity.getStatusCode().is2xxSuccessful()){
                TravelResponseDTO[] travelsArray= responseEntity.getBody();
                if(travelsArray!=null){
                    travels= Arrays.asList(travelsArray);
                }
                // travels.stream().map(travel->new TravelDTO(travel.getId(), travel.getStartDateTime(), travel.getFinishDateTime(), travel.getPause(), travel.getKilometers())).toList();
            }
            else {
                throw new RuntimeException("Error buscando los viajes. Status code: " + responseEntity.getStatusCodeValue());

            }

            return travels;

        }
        catch (Exception e){
            //throw new Exception(e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }

    }
    // metodo para setKilometers
    @Transactional
    public int updateKilometersById(Long id, Double kilometersTraveled){
        return this.scooterRepository.updateKilometersById(id, kilometersTraveled);
    }
    //asignar km recorridos
    @Transactional
    public Optional<ScooterResponseDTO> setKilometers(Long id)  {
        try{
            double sum=0.0;
            List<TravelResponseDTO>travels=this.getAllTravels(id);
           // List<TravelResponseDTO> transformedTravels=travels.stream().map(travel->new TravelResponseDTO(travel.getId(), travel.getStartDateTime(), travel.getFinishDateTime(), travel.getKilometers(), travel.getPause())).toList();
            for(int i=0; i<travels.size(); i++){
                sum+= travels.get(i).getKilometers();

            }
            if(sum>0.0){
                this.updateKilometersById(id,sum);
            }
            Optional<ScooterResponseDTO> scooter=this.findById( id);

            return scooter;

        }
        catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error occurred while setting kilometers for scooter with ID: " + id, e);
        }

    }
    @Transactional
    public int updateTimeWithoutBreaksById(Long id, Double timeWithoutBreaks){
        return this.scooterRepository.updateTimeWithoutBreaksById(id, timeWithoutBreaks);
    }
    //asignar tiempo sin pausas
    @Transactional
    public Optional<ScooterResponseDTO> setTimeWithoutBreaks(Long id) throws Exception {
        try{
            double sum=0.0;
            List<TravelResponseDTO>travels=this.getAllTravels(id);
            //travels.stream().map(travel->new TravelResponseDTO(travel.getId(), travel.getStartDateTime(), travel.getFinishDateTime(), travel.getKilometers(), travel.getPause()));
            for(int i=0; i<travels.size(); i++){
                LocalTime startHour = travels.get(i).getStartDateTime().toLocalTime();
                LocalTime finishHour = travels.get(i).getFinishDateTime().toLocalTime();
                sum += (double)(startHour.until(finishHour, ChronoUnit.MINUTES));

            }
            if(sum>0.0){
                this.updateTimeWithoutBreaksById(id, sum);
            }
            Optional<ScooterResponseDTO> scooter=this.findById( id);

            return scooter;
        }
       catch(Exception e){
           e.printStackTrace();
           throw new RuntimeException("Error occurred while setting time without breaks for scooter with ID: " + id, e);
       }

    }
    @Transactional
    public int updateTimeWithBreaksById(Long id, Double timeWithBreaks){
        return this.scooterRepository.updateTimeWithBreaksById(id, timeWithBreaks);
    }
    @Transactional
    //asignar tiempo con pausas
    public Optional<ScooterResponseDTO> setTimeWithBreaks(Long id) throws Exception {
        try{
            double sum=0.0;
            List<TravelResponseDTO>travels=this.getAllTravels(id);
            //travels.stream().map(travel->new TravelResponseDTO(travel.getId(), travel.getStartDateTime(), travel.getFinishDateTime(), travel.getKilometers(), travel.getPause()));
            for(int i=0; i<travels.size(); i++){
                if(travels.get(i).getPause()!=null){
                    LocalTime startHour = travels.get(i).getStartDateTime().toLocalTime();
                    LocalTime finishHour = travels.get(i).getFinishDateTime().toLocalTime();
                    sum += (double)(startHour.until(finishHour, ChronoUnit.MINUTES) + travels.get(i).getPause());


                }


            }
            if(sum>0.0){
                this.updateTimeWithBreaksById(id, sum);
            }
            Optional<ScooterResponseDTO> scooter=this.findById( id);

            return scooter;
        }
        catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error occurred while setting time with breaks for scooter with ID: " + id, e);
        }


    }
    //reporte scooters por km
    public List<ScooterResponseDTO> getAllOrderByKm() {
        return this.scooterRepository.getAllOrderByKm().stream()
                .map(scooter -> new ScooterResponseDTO(scooter.getId(), scooter.getKilometersTraveled()))
                .toList();

    }

    //reporte por tiempo con pausa
    public List<ScooterResponseDTO> getAllOrderByTimeWithBreaks() {
        return this.scooterRepository.getAllOrderByTimeWithBreaks().stream()
                .map(scooter -> new ScooterResponseDTO(scooter.getId(), scooter.getTimeWithBreaks(), scooter.isAvailable()))
                .toList();

    }

    //reporte por tiempo sin pausa
    public List<ScooterResponseDTO> getAllOrderByTimeWithoutBreaks() {
        return this.scooterRepository.getAllOrderByTimeWithoutBreaks().stream()
                .map(scooter -> new ScooterResponseDTO(scooter.getId(), scooter.getTimeWithoutBreaks(), scooter.getLastMaintenance()))
                .toList();

    }
    public List<ScooterResponseDTO>getScootersByStop(Stop id){
        return this.scooterRepository.getScooterByStop(id).stream().map(scooter -> new ScooterResponseDTO(scooter.getId(), scooter.isAvailable(), scooter.getLatitude(), scooter.getLongitude(), scooter.getStop()))
                .toList();
    }

    public  List<ScooterResponseDTO> getScootersNear(Double latitude, Double longitude){
        Double latitudeMin=latitude-200;
        Double latitudeMax=latitude+200;
        Double longitudeMin=longitude-200;
        Double longitudeMax=longitude+200;
        return this.scooterRepository.getScootersNear(latitudeMin, latitudeMax, longitudeMin, longitudeMax).stream().map(scooter -> new ScooterResponseDTO(scooter.getId(), scooter.isAvailable(), scooter.getLatitude(),
                scooter.getLongitude(), scooter.getStop()))
                .toList();

    }
    @Transactional
    public List<ScooterResponseDTO>getScootersUpdated() throws Exception {
        try{
            List<ScooterResponseDTO> scooters =this.findAll();
            for(int i = 0; i < scooters.size(); i++){
                Long id = scooters.get(i).getId();
                this.setKilometers(id);
                this.setTimeWithBreaks(id);
                this.setTimeWithoutBreaks(id);

            }
            return scooters.stream().map(scooter->new ScooterResponseDTO(scooter.getId(), scooter.getKilometersTraveled(),
                    scooter.getTimeWithBreaks(), scooter.getTimeWithoutBreaks(), scooter.isAvailable(),
                    scooter.getLatitude(), scooter.getLongitude(),
                    scooter.getLastMaintenance())).toList();

        }
        catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error occurred while setting scooters  ", e);
        }
    }
    public Tuple getQuantityScootersAvailability(){
        return this.scooterRepository.getQuantityScootersByAvailability();


    }

}
