package com.clinicdata.cdms.repository;

import com.clinicdata.cdms.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

	List<User> findByRole(String string);
}