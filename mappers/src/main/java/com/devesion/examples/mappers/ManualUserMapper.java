/*
 * Copyright (C) 2013 devesion.com
 *
 * Contact: Lukas Dembinski <dembol@devesion.com>
 * Initial developer: Lukas Dembinski <dembol@devesion.com>
 * Contributor(s):
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.devesion.examples.mappers;

import com.devesion.examples.mappers.junk.User;
import com.devesion.examples.mappers.junk.UserDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * @author dembol
 * @version $Revision$ $Date$
 */
class ManualUserMapper extends AbstractMapper<User, UserDTO> {

    @Override
    public void doMapObjects(User src, UserDTO dst) {
        dst.setUserName(src.getName());
        dst.setUserAge(src.getAge());
        List<UserDTO> friendsDTO = new LinkedList<UserDTO>();
        for (User friend : src.getFriends()) {
            UserDTO friendDTO = new UserDTO();
            mapObjects(friend, friendDTO);
            friendsDTO.add(friendDTO);
        }
        dst.setUserFriends(friendsDTO);
    }
}
