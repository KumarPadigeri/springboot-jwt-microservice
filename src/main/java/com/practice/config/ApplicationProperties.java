package com.practice.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/*
 * @Created 1/13/2023 11:54 AM 2023
 * @Project testing-project-springboot3
 * @User Kumar Padigeri
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "application")
@Data
public class ApplicationProperties {

    private Auth auth = new Auth();




    @Data
    public static class Roles {

        private User user;
        private Admin admin;


    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class User extends Creds {


    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class Admin extends Creds {


    }

    @Data

    public static class Creds {
        private String username;
        private String password;

    }

    @Data

    public static class Auth {
        private Roles roles;

    }
}
