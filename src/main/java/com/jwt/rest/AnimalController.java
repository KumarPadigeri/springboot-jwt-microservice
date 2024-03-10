package com.jwt.rest;

import com.test.api.PetApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/*
 * @Created 7/13/2023 11:51 AM 2023
 * @Project testing-project-springboot3
 * @User Kumar Padigeri
 */
@Controller
@RestController
@RequiredArgsConstructor
@Slf4j
public class AnimalController implements PetApi {

    @Override
    public ResponseEntity<List<String>> getAnimals() {
        return new ResponseEntity<>(Arrays.asList("Dog", "Cat", "Hen"), HttpStatus.OK);
    }
}
