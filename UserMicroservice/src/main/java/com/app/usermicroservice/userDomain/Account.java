package com.app.usermicroservice.userDomain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private double amount;
    @Column
    private LocalDate date;
    @Column
    private boolean isCanceled;
    @OneToMany(mappedBy = "account", cascade = CascadeType.MERGE)
    @JsonManagedReference
    private List<UserAccount> users;

    public Account() {

    }

    public Account(double amount, LocalDate date) {
        this.amount = amount;
        this.date=date;
        this.users=new ArrayList<UserAccount>();
        this.isCanceled = false;
    }

    public ArrayList<UserAccount>getUsers(){
        return new ArrayList<>(this.users);
    }

    public void setUsers(ArrayList<UserAccount> users) {
        this.users = users;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }
}
