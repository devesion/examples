package com.devesion.examples.mappers.junk;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author dembol
 * @version $Revision$ $Date$
 */
public class UserDTO {

    @Getter
    @Setter
    private String userName;

    @Getter
    @Setter
    private int userAge;

    @Getter
    @Setter
    private List<UserDTO> userFriends;
}
