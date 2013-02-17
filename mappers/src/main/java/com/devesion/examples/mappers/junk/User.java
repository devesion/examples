package com.devesion.examples.mappers.junk;

import com.google.common.base.Preconditions;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

/**
 * @author dembol
 * @version $Revision$ $Date$
 */
public class User {

    @Getter
    private String name;

    @Getter
    private int age;

    @Getter
    private List<User> friends = new LinkedList<User>();

    public User(String name, int age) {
        Preconditions.checkNotNull(name);
        Preconditions.checkState(age > 0);
        this.name = name;
        this.age = age;
    }

    public void addFriend(User friend) {
        Preconditions.checkNotNull(friend);
        friends.add(friend);
    }
}
