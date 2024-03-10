package com.jwt.rest;
/*
 * @Created 1/6/2023 11:22 AM 2023
 * @Project testing-project-springboot3
 * @User Kumar Padigeri
 */

import com.jwt.service.TestService;
import com.test.api.TestApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RestController
@RequiredArgsConstructor
@Slf4j
public class TestController implements TestApi {

    private final TestService service;

    @Override
    public ResponseEntity<List<String>> getAlphabets() {
        return new ResponseEntity<>(service.getAlphabets(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<String>> getLowerAlphabets() {
        return new ResponseEntity<>(service.getLowerAlphabets(), HttpStatus.OK);
    }
}
