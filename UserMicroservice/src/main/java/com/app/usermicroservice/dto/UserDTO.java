package com.app.usermicroservice.dto;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter

public class UserDTO {
    private Long id;

    private String name;

    private String surname;

    private Long cellphone;

    private String email;

    private Double latitude;

    private Double longitude;



    public UserDTO(Long id, String name, String surname, Long cel, String email){
        this.id=id;
        this.name=name;
        this.surname=surname;
        this.cellphone =cel;
        this.email=email;
    }

    public UserDTO(Long id, String name, String surname, Long cellphone, Double latitude, Double longitude, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.cellphone = cellphone;
        this.email = email;
        this.latitude = latitude;
        this.longitude = longitude;
    }

   /* @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", cellphone=" + cellphone +
                ", email='" + email + '\'' +
                '}';
    }*/


}
