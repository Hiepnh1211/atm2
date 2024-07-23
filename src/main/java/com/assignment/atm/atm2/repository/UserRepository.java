package com.assignment.atm.atm2.repository;

import com.assignment.atm.atm2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Modifying
    @Query(value = "INSERT INTO user_info (atm2.user_info.user_name, atm2.user_info.password, atm2.user_info.name, atm2.user_info.contact_number, atm2.user_info.gender, atm2.user_info.address) VALUES (:username, :password, :name, :contactNumber, :gender, :address)", nativeQuery = true)
    void userCreation(@Param("username") String username, @Param("password") String password, @Param("name") String name,@Param("contactNumber") String contactNumber, @Param("gender") String gender, @Param("address") String address);

    @Query(value = "SELECT COUNT(user_info.name), name FROM user_info WHERE name = :name", nativeQuery = true)
    int nameCount(@Param("name") String name);

    @Query(value = "SELECT user_info.password FROM atm2.user_info WHERE user_name = :username", nativeQuery = true)
    String login(@Param("username") String username);
}
