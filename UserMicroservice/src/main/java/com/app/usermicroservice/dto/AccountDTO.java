package com.app.usermicroservice.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AccountDTO {
    private Long id;

    private double amount;

    private LocalDate date;


  /* @Override
    public String toString() {
        return "AccountDTO{" +
                "id=" + id +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }*/
}
