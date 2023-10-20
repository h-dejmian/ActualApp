package com.example.ActualApp.auth.user;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    @Override
    @Query("SELECT us FROM User us JOIN FETCH us.roles")
    List<User> findAll();

    Optional<User> findByUserName(String userName);
}
