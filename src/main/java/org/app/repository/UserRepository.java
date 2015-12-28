package org.app.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.app.entity.User;
import org.springframework.stereotype.Repository;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByAccountName(String name);
}
