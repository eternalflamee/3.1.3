package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Table(name = "roles")
@Entity
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "role")
    private String role;

    @Transient
    private Set<User> users;


    public Role() {
    }

    public Role(String role) {
        this.role = role;
    }



    public Role(Long id, String role) {
        this.id = id;
        this.role = role;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return getRole();
    }

    @Override
    public String toString() {
        String role = getRole();
        if (role.equals("ROLE_ADMIN")) {
            return "ADMIN";
        }
        if (role.equals("ROLE_USER")) {
            return "USER";
        }
        return role;
    }
}