package com.practice.dto;

import lombok.Builder;
import lombok.Data;

/*
 * @Created 2/3/24
 * @Project springboot-jwt-microservice
 * @User Kumar Padigeri
 */
@Data
@Builder
public class StudentDTO {


     private int id;
     private String studentName ;
     private int studentAge;
     private int yob;
}
