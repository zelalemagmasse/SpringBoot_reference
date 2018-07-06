package com.example.demo;

import org.springframework.data.repository.CrudRepository;

public interface FriendRepositroy extends CrudRepository<Friend,Long>{
Iterable<Friend> findAllByFilledByOrderByRankOfFriend(String filledby);
}
