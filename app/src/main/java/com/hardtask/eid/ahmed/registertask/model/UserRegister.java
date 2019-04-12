package com.hardtask.eid.ahmed.registertask.model;

public class UserRegister  {
    private String fullName,password,email,code,country,city ;

    public UserRegister(String fullName, String password, String email, String code, String country, String city) {
        this.fullName = fullName;
        this.password = password;
        this.email = email;
        this.code = code;
        this.country = country;
        this.city = city;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getCode() {
        return code;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }
}
