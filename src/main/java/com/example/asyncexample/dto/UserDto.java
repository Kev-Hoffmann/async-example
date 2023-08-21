package com.example.asyncexample.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

  private Integer id;
  private String name;
  private String username;
  private String email;
  private AddressDto address;
  private String phone;
  private String website;
  private CompanyDto company;
}
