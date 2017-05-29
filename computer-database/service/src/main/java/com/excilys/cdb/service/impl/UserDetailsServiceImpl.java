package com.excilys.cdb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.model.auth.UserRole;
import com.excilys.cdb.persistence.UserDAO;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserDAO userDao;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.info("Load user by name : " + username);
        com.excilys.cdb.model.auth.User user = userDao.findByUsername(username);

        return buildUserAuth(user);
    }

    /**
     * Build user for authentication.
     *
     * @param user user bean
     * @return user for authentication
     */
    private User buildUserAuth(com.excilys.cdb.model.auth.User user) {
        LOGGER.info("Build User : " + user);
        LOGGER.debug("accountNonExpired, creadentialsNonExpired and accountNonLocked");
        return new User(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true,
                buildUserAuthority(user.getUserRole()));
    }

    /**
     * Build authorities from user roles.
     *
     * @param userRoles user roles
     * @return authorities
     */
    private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {
        LOGGER.info("Build authorities : " + userRoles);
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (UserRole ur : userRoles) {
            authorities.add(new SimpleGrantedAuthority(ur.getRole()));
        }

        return authorities;
    }
}