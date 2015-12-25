package org.app.repository.loader;

import org.app.entity.Role;
import org.app.entity.User;
import org.app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DatabaseLoader implements CommandLineRunner {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public void run(String... strings) throws Exception {
        User frodo = userRepository.findByAccountName("frodo");
        if(frodo != null)
            return;
        frodo = userRepository.save(new User("frodo", "Frodo", "Baggins", "", passwordEncoder.encode("12345"), null));
        User admin = userRepository.save(new User("admin", "Admin", "zloy", "", passwordEncoder.encode("12345"), null));
        User user1 = userRepository.save(new User("user1", "Vasia", "Petrov", "", passwordEncoder.encode("12345"), null));
        User user2 = userRepository.save(new User("user2", "Kostia", "Ivanov", "", passwordEncoder.encode("12345"), null));

        Role admRole = new Role();
        admRole.setName("ADMIN");
        admRole = roleRepository.save(admRole);

        //create regular users
        Role userRole = new Role();
        userRole.setName("USER");
        userRole = roleRepository.save(admRole);

        applyrole(admin, admRole);
        applyrole(user1, userRole);
        applyrole(user2, userRole);
    }

    private void applyrole(User user, Role role1) {
        user.setRoles(Arrays.asList(role1));
        userRepository.save(user);
    }
}