package com.app.usermicroservice.userService;

import com.app.usermicroservice.dto.ScooterDTO;
import com.app.usermicroservice.dto.Stop;
import com.app.usermicroservice.dto.UserDTO;
import com.app.usermicroservice.userDomain.User;
import com.app.usermicroservice.userRepository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service

public class UserService {
    private final UserRepository userRepository;
    private RestTemplate restTemplate;

    public UserService(UserRepository userRepository, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.restTemplate=restTemplate;
    }

    public Optional<UserDTO> findById(Long id) {
        return this.userRepository.findById(id).map(user -> new UserDTO(user.getId(), user.getName(),
                user.getSurname(), user.getCelphone(), user.getLatitude(), user.getLongitude(), user.getEmail()));

    }
    @Transactional
    public User save(User entity) throws Exception{
        try {
            return this.userRepository.save(entity);
        }
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public List<UserDTO> findAll() {
        return this.userRepository.findAll().stream()
                .map(user -> new UserDTO(user.getId(), user.getName(),
                        user.getSurname(), user.getCelphone(), user.getLatitude(), user.getLongitude(),user.getEmail()))
                .toList();

    }
    @Transactional
    public Optional<User> deleteById(Long id) {
        Optional<User> entityToDelete = userRepository.findById(id);
        entityToDelete.ifPresent(entity -> userRepository.deleteById(id));
        return entityToDelete;
    }
    @Transactional
    public int updateNameById(Long id, String name){
       // Optional<User> entityToUpdate = userRepository.findById(id);
       // entityToUpdate.ifPresent(entity -> userRepository.updateNameById(id, name));
        return userRepository.updateNameById(id, name);

    }
    //problema con el tipo stop, igual anda
    public List<ScooterDTO> getScootersByStop(Stop idStop)  throws Exception{
        try{
            List<ScooterDTO> scooters=null;
            ResponseEntity<ScooterDTO[]> responseEntity=restTemplate.getForEntity("http://localhost:8082/api/scooters/stopId/" +idStop, ScooterDTO[].class);
            if(responseEntity.getStatusCode().is2xxSuccessful()){
                ScooterDTO[] scootersArray= responseEntity.getBody();
                if(scootersArray!=null){
                    scooters= Arrays.asList(scootersArray);
                }
                // scooters.stream().map(travel->new TravelDTO(travel.getId(), travel.getStartDateTime(), travel.getFinishDateTime(), travel.getPause(), travel.getKilometers())).toList();
            }

            return scooters;

        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }
    //para localizar paradas cercanas
    public List<Stop> getStopsNear(Double latitude, Double longitude)  throws Exception{
        try{
            List<Stop> stops=null;
            ResponseEntity<Stop[]> responseEntity=restTemplate.getForEntity("http://localhost:8082/api/stops/latitude/" +latitude + "/longitude/" + longitude, Stop[].class);
            if(responseEntity.getStatusCode().is2xxSuccessful()){
                Stop[] stopsArray= responseEntity.getBody();
                if(stopsArray!=null){
                    stops= Arrays.asList(stopsArray);
                }
                // scooters.stream().map(travel->new TravelDTO(travel.getId(), travel.getStartDateTime(), travel.getFinishDateTime(), travel.getPause(), travel.getKilometers())).toList();
            }

            return stops;

        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }
    public List<ScooterDTO> getScootersNear(Double latitude, Double longitude)  throws Exception{
        try{
            List<ScooterDTO> scooters=null;
            ResponseEntity<ScooterDTO[]> responseEntity=restTemplate.getForEntity("http://localhost:8082/api/scooters/latitude/" +latitude + "/longitude/" + longitude, ScooterDTO[].class);
            if(responseEntity.getStatusCode().is2xxSuccessful()){
                ScooterDTO[] scootersArray= responseEntity.getBody();
                if(scootersArray!=null){
                    scooters= Arrays.asList(scootersArray);
                }
                // scooters.stream().map(travel->new TravelDTO(travel.getId(), travel.getStartDateTime(), travel.getFinishDateTime(), travel.getPause(), travel.getKilometers())).toList();
            }

            return scooters;

        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }


}
