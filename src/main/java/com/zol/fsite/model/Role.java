package com.zol.fsite.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;
    @Column(unique = true)
    private String role;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<User> getUserslist() {
        return userslist;
    }

    public void setUserslist(List<User> userslist) {
        this.userslist = userslist;
    }

    public Role() {
        this.userslist = new ArrayList<>();
    }


    @ManyToMany(mappedBy = "roleOfUsers")
    private List<User> userslist;
}
