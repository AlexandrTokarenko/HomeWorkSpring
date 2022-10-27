package com.example.homeworkspring.repository;

import com.example.homeworkspring.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.util.List;
import java.util.Optional;

//@NamedQueries({
//        @NamedQuery(name = "UserRepository.findByEmailAndPassword", query = "select U from User u where u.email = :email and u.password = :password")
//})
public interface UserRepository extends JpaRepository<User, Integer> {


    @Query("select u from User u where u.email = ?1 and u.password = ?2")
    Optional<User> findUserByEmailAndPassword(String email, String password);

    @Query("select u from User u where u.email = ?1")
    Optional<User> findUserByEmail(String email);
}