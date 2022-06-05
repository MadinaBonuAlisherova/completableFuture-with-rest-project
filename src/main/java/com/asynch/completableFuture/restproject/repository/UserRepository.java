package com.asynch.completableFuture.restproject.repository;

import com.asynch.completableFuture.restproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
