package com.app.usermicroservice.userDomain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private Long celphone;
    @Column
    private String email;
    @Column
    private Double latitude;
    @Column
    private Double longitude;
    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)
    @JsonManagedReference
    private List<UserAccount> accounts;

    public User() {
    }

    public User(String name, String surname, Long cel, Double latitude, Double longitude,String email) {
        this.name = name;
        this.surname=surname;
        this.celphone=cel;
        this.latitude = latitude;
        this.longitude = longitude;
        this.email=email;
        this.accounts=new ArrayList<>();
    }
    public ArrayList<UserAccount>getAccounts(){
        return new ArrayList<UserAccount>(this.accounts);
    }

    public void setAccounts(ArrayList<UserAccount> accounts) {
        this.accounts = accounts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Long getCelphone() {
        return celphone;
    }

    public void setCelphone(Long celphone) {
        this.celphone = celphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
