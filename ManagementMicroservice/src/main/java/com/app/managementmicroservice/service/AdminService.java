package com.app.managementmicroservice.service;

import com.app.managementmicroservice.domain.Manager;
import com.app.managementmicroservice.dto.*;
import com.app.managementmicroservice.repository.ManagementRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AdminService {

    private final ManagementRepository managementRepository;
    private final RestTemplate restTemplate;

    //CRUD
    @Transactional
    public Manager save(Manager entity) throws Exception {
        try {
            return this.managementRepository.save(entity);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public List<ManagerDTO> findAll() {
        return this.managementRepository.findAll().stream()
                .map(manager -> new ManagerDTO(manager.getId(), manager.getFileNumber(),
                        manager.getName(), manager.getRole()))
                .toList();

    }
    public Optional<ManagerDTO> findById(Long id) {
        return this.managementRepository.findById(id).map(manager -> new ManagerDTO(manager.getId(), manager.getFileNumber(),
                manager.getName(), manager.getRole()));

    }
    @Transactional
    public Optional<Manager> deleteById(Long id) {
        Optional<Manager> entityToDelete = managementRepository.findById(id);
        entityToDelete.ifPresent(entity -> managementRepository.deleteById(id));
        return entityToDelete;
    }
    @Transactional
    public int updateRoleManagerById(Long id, String role){
        return this.managementRepository.updateRoleManagerById(id, role);
    }

   /* public Scooter addScooter(Scooter newScooter) throws Exception {
        try{
            Scooter scooter = null;
            ResponseEntity<Scooter> responseEntity=restTemplate.getForEntity("http://localhost:8082/api/scooters", Scooter.class);
            if(responseEntity.getStatusCode().is2xxSuccessful()){
              scooter= responseEntity.getBody();


            }

            return scooter;
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }

    }*/
    //SERVICIOS DEL ADMIN
    public ScooterResponseDTO addScooter(ScooterRequestDTO newScooter) throws HttpClientErrorException {
        ScooterResponseDTO response = restTemplate.postForObject("http://localhost:8082/api/scooters", newScooter, ScooterResponseDTO.class);
        return response;
    }

    public ResponseEntity deleteScooterById(Long id){
         HttpHeaders headers = new HttpHeaders();
         HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
         ResponseEntity<String> response = restTemplate.exchange("http://localhost:8082/api/scooters/" + id, HttpMethod.DELETE, requestEntity, String.class);
         headers.setContentType(MediaType.APPLICATION_JSON);
         return response;
    }
    public StopResponseDTO addStop(StopRequestDTO requestDTO){
        StopResponseDTO response=restTemplate.postForObject("http://localhost:8082/api/stops", requestDTO, StopResponseDTO.class);
        return response;
    }
    public ResponseEntity deleteStopById(Long id){
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8082/api/stops/" + id, HttpMethod.DELETE, requestEntity, String.class);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return response;
    }
    public ResponseEntity modifyStateOfAccountById(Long id, boolean state){
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8083/api/accounts/isCanceled/" + id + "?isCanceled=" + state, HttpMethod.PUT, requestEntity, String.class);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return response;
    }


    public List<ScooterResponseDTO> getAllScooters() throws Exception {
        try{
            List<ScooterResponseDTO> scooters=null;
            ResponseEntity<ScooterResponseDTO[]> responseEntity=restTemplate.getForEntity("http://localhost:8082/api/scooters", ScooterResponseDTO[].class);
            if(responseEntity.getStatusCode().is2xxSuccessful()){
               ScooterResponseDTO[] scootersArray= responseEntity.getBody();
                if(scootersArray!=null){
                    scooters= Arrays.asList(scootersArray);
                }
                // travels.stream().map(travel->new TravelDTO(travel.getId(), travel.getStartDateTime(), travel.getFinishDateTime(), travel.getPause(), travel.getKilometers())).toList();
            }

            return scooters;

        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }

        /*HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Scooter> response = restTemplate.exchange("http://localhost:8082/api/scooters", HttpMethod.GET,requestEntity, Scooter.class);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return response;*/
    }
    public PriceResponseDTO addPrice(PriceRequestDTO requestDTO){
        PriceResponseDTO response=restTemplate.postForObject("http://localhost:8081/api/prices", requestDTO, PriceResponseDTO.class);
        return response;
    }

    public List<ScooterResponseDTO>getScootersReport() throws Exception {
        try{
            List<ScooterResponseDTO> scooters=null;
            ResponseEntity<ScooterResponseDTO[]> responseEntity=restTemplate.getForEntity("http://localhost:8082/api/scooters/allUpdated", ScooterResponseDTO[].class);
            if(responseEntity.getStatusCode().is2xxSuccessful()) {
                ScooterResponseDTO[] scootersArray = responseEntity.getBody();
                if (scootersArray != null) {
                    scooters = Arrays.asList(scootersArray);
                }
            }
            return scooters;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public ResponseEntity<?> getTotalInvoicedByDate(Integer month1, Integer month2, Integer year){
        HttpHeaders headers = new HttpHeaders();
        //HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8081/api/travels/invoiced/month1/"+month1+"/month2/"+month2+"/year/"+year, String.class);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return response;
        //return restTemplate.getForEntity("http://localhost:8081/api/travels/invoiced/month1/"+month1+"/month2/"+month2+"/year/"+year, ResponseEntity.class);
    }

    public ResponseEntity<?> getScootersAvailability(){
        HttpHeaders headers = new HttpHeaders();
        //HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8082/api/scooters/availabilityQuantity", String.class);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return response;
    }

    public ResponseEntity<?> getScootersByYearAndQuantity(Long year, Long quantity) throws Exception {
        try{
            HttpHeaders headers = new HttpHeaders();
            //HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
            ResponseEntity<String[]> response = restTemplate.getForEntity("http://localhost:8081/api/travels/allByYear/" +year+ "/quantity/" +quantity, String[].class);
            headers.setContentType(MediaType.APPLICATION_JSON);
           // if(response.getStatusCode().is2xxSuccessful()) {
           //     String[] messageArray = response.getBody();

            //}

            return response;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }

    public ResponseEntity sendScooterToMaintenance(Long id, boolean availability) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8082/api/scooters/isAvailable/" + id + "?isAvailable=" + availability, HttpMethod.PUT, requestEntity, String.class);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return response;

    }
}
