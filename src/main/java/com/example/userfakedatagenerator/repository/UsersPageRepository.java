package com.example.userfakedatagenerator.repository;

import com.example.userfakedatagenerator.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersPageRepository extends JpaRepository<User, Long> {
    List<User> findAllByOrderByNumberAsc();
}
