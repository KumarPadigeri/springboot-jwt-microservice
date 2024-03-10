package com.jwt.repository;

import com.jwt.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*
 * @Created 2/11/24
 * @Project springboot-jwt-microservice
 * @User Kumar Padigeri
 */
@Repository
public interface UserRepository extends JpaRepository<Users,Integer> {

    Optional<Users> findByName(String username);
}
