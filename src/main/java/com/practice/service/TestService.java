package com.practice.service;

/*
 * @Created 1/6/2023 11:22 AM 2023
 * @Project testing-project-springboot3
 * @User Kumar Padigeri
 */

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
@Slf4j
public class TestService {

    public List<String> getAlphabets() {
        List<String> alphabets = new ArrayList<>();
        char c;
        for (c = 'A'; c <= 'Z'; ++c)
            alphabets.add(String.valueOf(c));
        return alphabets;

    }

    public List<String> getLowerAlphabets() {
        List<String> alphabets = new ArrayList<>();
        char c;
        for (c = 'a'; c <= 'z'; ++c)
            alphabets.add(String.valueOf(c));
        return alphabets;

    }


}

