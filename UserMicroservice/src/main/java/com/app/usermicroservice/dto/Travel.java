package com.app.usermicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Travel {
    private Long id;

    private Long idUser;

    private Long idScooter;

    private LocalDateTime startDateTime;

    private LocalDateTime finishDateTime;

    private Integer pause;

    private Double kilometers;


}
