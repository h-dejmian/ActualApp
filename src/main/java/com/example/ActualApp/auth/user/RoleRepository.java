package com.example.ActualApp.auth.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

//    @Override
//    @Query("SELECT role FROM Role role JOIN FETCH role.users")
//    List<Role> findAll();

    Optional<Role> findByName(String name);
}
