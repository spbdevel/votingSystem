package org.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.app.entity.Role;
import org.app.entity.User;
import org.app.repository.RoleRepository;
import org.app.repository.UserRepository;

import java.util.List;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByAccountName(username);
        if (user == null) {
            throw new UsernameNotFoundException("username not found");
        }
        List<Role> roles = user.getRoles();
        StringBuffer sb = new StringBuffer();
        for(Role role: roles) {
            if(sb.length() !=0) sb.append(", ");
            sb.append(role.getName());
        }
        List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList(sb.toString());
        String password = user.getPassword();
        return new org.springframework.security.core.userdetails.User(username, password, auth);
    }

}