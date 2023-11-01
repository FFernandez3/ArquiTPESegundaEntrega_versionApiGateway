package com.app.travelmicroservice.utils;

import com.app.travelmicroservice.domain.Price;
import com.app.travelmicroservice.domain.Travel;
import com.app.travelmicroservice.dto.AccountDTO;
import com.app.travelmicroservice.dto.ScooterDTO;
import com.app.travelmicroservice.repository.PriceRepository;
import com.app.travelmicroservice.repository.TravelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class LoadDataBase {
        private static final Logger log = LoggerFactory.getLogger(LoadDataBase.class);
        private RestTemplate accountClientRestTemplate;
        private RestTemplate scooterClientRestTemplate;
        @Bean
        CommandLineRunner initDatabase(TravelRepository travelRepository, PriceRepository priceRepository, RestTemplate accountClientRestTemplate, RestTemplate scooterClientRestTemplate){
            return args -> {
              /* Price p1 = new Price(LocalDate.of(2020,8,23), 70.0, 90.0);
               Price p2 = new Price(LocalDate.of(2022,3,1), 140.0, 180.0);

                ResponseEntity<AccountDTO[]>accountsId=accountClientRestTemplate.getForEntity("http://localhost:8083/api/accounts/ids", AccountDTO[].class);
                ResponseEntity<ScooterDTO[]>scootersId=scooterClientRestTemplate.getForEntity("http://localhost:8082/api/scooters/ids",ScooterDTO[].class);

                List<AccountDTO> accounts=null;
                List<ScooterDTO> scooters=null;
                if(accountsId.getStatusCode().is2xxSuccessful()){

                    AccountDTO[] accountsArray=accountsId.getBody();
                    accounts= Arrays.asList(accountsArray);

                }
                else {
                    System.out.println("Error al conectar con el microservicio Usuario");
                }
                if(scootersId.getStatusCode().is2xxSuccessful()){

                    ScooterDTO[] scootersArray=scootersId.getBody();
                    scooters= Arrays.asList(scootersArray);

                }
                else {
                    System.out.println("Error al conectar con el microservicio Scooter");
                }

                Travel t1=new Travel(accounts.get(0).getId(), scooters.get(0).getId(), LocalDateTime.of(2022, 8, 15, 13,0), LocalDateTime.of(2022,8,15, 13, 30), 10, 5.2,p2);
                Travel t2=new Travel(accounts.get(0).getId(), scooters.get(1).getId(), LocalDateTime.of(2022, 9, 18, 20,0), LocalDateTime.of(2022,9,18, 21, 30), 16, 8.0,p2);
                Travel t3=new Travel(accounts.get(1).getId(), scooters.get(2).getId(), LocalDateTime.of(2022, 9, 23, 16,40), LocalDateTime.of(2022,9,23, 17, 50), 0, 9.7,p2);
                Travel t4=new Travel(accounts.get(2).getId(), scooters.get(3).getId(), LocalDateTime.of(2022, 10, 2, 10,0), LocalDateTime.of(2022,10,2, 10, 50), 20, 5.2,p2);



                log.info("Preloading " + priceRepository.save(p1));
                log.info("Preloading " + priceRepository.save(p2));
                log.info("Preloading " + travelRepository.save(t1));
                log.info("Preloading " + travelRepository.save(t2));
                log.info("Preloading " + travelRepository.save(t3));
                log.info("Preloading " + travelRepository.save(t4));*/

            };
        }
    }

