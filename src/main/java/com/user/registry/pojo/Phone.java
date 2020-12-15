package com.user.registry.pojo;

import javax.persistence.*;

@Table(name = "Phone")
@Entity
public class Phone {
    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String ID;
    @Column(name = "NUMBER")
    private String number;
    @Column(name = "CITYCODE")
    private String citycode;

    @Column(name = "COUNTRYCODE")
    private String countrycode;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getCountrycode() {
        return countrycode;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
    }

}