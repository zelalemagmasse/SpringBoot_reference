package com.zol.fsite.model;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Friend {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String nameOfFFr;
    private int rankOfFriend;
    private String urlImage;
    private String filledBy;
    public String getFilledBy() {
        return filledBy;
    }

    public void setFilledBy(String filledBy) {
        this.filledBy = filledBy;
    }

    @ManyToMany(mappedBy = "friends")
    private List<User> users;

    public String getNameOfFFr() {
        return nameOfFFr;
    }

    public void setNameOfFFr(String nameOfFFr) {
        this.nameOfFFr = nameOfFFr;
    }

    public Friend() {
        this.users = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getRankOfFriend() {
        return rankOfFriend;
    }

    public void setRankOfFriend(int rankOfFriend) {
        this.rankOfFriend = rankOfFriend;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }



}
