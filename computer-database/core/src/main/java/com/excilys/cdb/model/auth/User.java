package com.excilys.cdb.model.auth;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(nullable = false, length = 45)
    private String username;

    @Column(nullable = false, length = 45)
    private String password;

    @Column(nullable = false)
    private boolean enabled;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<UserRole> userRole;

    /**
     * Constructor.
     */
    public User() {
        this.userRole = new HashSet<>();
    }

    /**
     * Constructor.
     *
     * @param username name
     * @param password pasword
     * @param enabled true if used for the application
     */
    public User(String username, String password, boolean enabled) {
        this();
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    /**
     * Constructor.
     *
     * @param username name
     * @param password password
     * @param enabled true if used for the application
     * @param userRole roles
     */
    public User(String username, String password, boolean enabled, Set<UserRole> userRole) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.userRole = userRole;
    }
    
    @Override
    public String toString() {
        String res = "User : " + username + ' '+ password + ' ' + enabled;
        
        for (UserRole ur : userRole) {
            res = res + ' ' + ur.getRole();
        }
        
        return res;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return the userRole
     */
    public Set<UserRole> getUserRole() {
        return userRole;
    }

    /**
     * @param userRole the userRole to set
     */
    public void setUserRole(Set<UserRole> userRole) {
        this.userRole = userRole;
    }
}