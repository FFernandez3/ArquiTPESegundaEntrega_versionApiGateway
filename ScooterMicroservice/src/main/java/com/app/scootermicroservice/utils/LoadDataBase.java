package com.app.scootermicroservice.utils;

import com.app.scootermicroservice.domain.Scooter;
import com.app.scootermicroservice.domain.Stop;
import com.app.scootermicroservice.repository.ScooterRepository;
import com.app.scootermicroservice.repository.StopRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDataBase {
    private static final Logger log = LoggerFactory.getLogger(LoadDataBase.class);
    @Bean
    CommandLineRunner initDatabase(ScooterRepository scooterRepository, StopRepository stopRepository){
        return args -> {


            /*Stop st1=new Stop(855.365, 954.541);
            Stop st2=new Stop(800.205, 958.254);
            Stop st3=new Stop(840.452, 978.891);
            Stop st4=new Stop(866.852, 998.871);
            Stop st5=new Stop(601.452, 878.891);
            Stop st6=new Stop(846.450, 902.801);

            Scooter s1=new Scooter(true,855.365, 954.541,st1 );
            Scooter s2=new Scooter(true,855.365, 954.541,st1 );
            Scooter s3=new Scooter(true,800.205, 958.254,st2 );
            Scooter s4=new Scooter(true,840.452, 978.891, st3 );


            log.info("Preloading " + stopRepository.save(st1));
            log.info("Preloading " + stopRepository.save(st2));
            log.info("Preloading " + stopRepository.save(st3));
            log.info("Preloading " + stopRepository.save(st4));
            log.info("Preloading " + stopRepository.save(st5));
            log.info("Preloading " + stopRepository.save(st6));
            log.info("Preloading " + scooterRepository.save(s1));
            log.info("Preloading " + scooterRepository.save(s2));
            log.info("Preloading " + scooterRepository.save(s3));
            log.info("Preloading " + scooterRepository.save(s4));*/



        };
    }
}
