package com.example.demo;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private String email;
    private String password;
    private String username;
    @ManyToMany(fetch= FetchType.EAGER)
    private Set<Friend> friends;

    public Set<Role> getRoleOfUsers() {
        return roleOfUsers;
    }

    public void setRoleOfUsers(Set<Role> roleOfUsers) {
        this.roleOfUsers = roleOfUsers;
    }

    @ManyToMany(fetch= FetchType.EAGER)
    private Set<Role> roleOfUsers;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password) ;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Friend> getFriends() {
        return friends;
    }

    public void setFriends(Set<Friend> friends) {
        this.friends = friends;
    }

    public User() {
        this.friends = new HashSet<>();
        this.roleOfUsers = new HashSet<>();
    }
    public void  addRole(Role rr){
        this.roleOfUsers.add(rr);
    }


}
