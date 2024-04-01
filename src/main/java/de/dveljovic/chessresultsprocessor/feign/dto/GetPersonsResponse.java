package de.dveljovic.chessresultsprocessor.feign.dto;

import lombok.Data;

import java.util.List;

@Data
public class GetPersonsResponse {
    private String status;
    private int code;
    private int total;
    private List<PersonDto> data;

    @Data
    public static class PersonDto {
        private int id;
        private String firstname;
        private String lastname;
        private String email;
        private String phone;
        private String birthday;
        private String gender;
        private AddressDto address;
        private String website;
        private String image;
    }

    @Data
    public static class AddressDto {
        private int id;
        private String street;
        private String streetName;
        private String buildingNumber;
        private String city;
        private String zipcode;
        private String country;
        private String county_code;
        private double latitude;
        private double longitude;
    }
}





