package org.app.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.app.entity.Role;


public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
