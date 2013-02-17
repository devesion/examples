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
import com.google.caliper.Param;
import com.google.caliper.Runner;
import com.google.caliper.SimpleBenchmark;

/**
 * @author dembol
 * @version $Revision$ $Date$
 */
@SuppressWarnings("unused")
public class ObjectMapperBenchmark extends SimpleBenchmark {

    @Param({ "1", "10", "25", "50"})
    private int friendsCount;

    //@Param({ "MANUAL", "REFLECTION", "COMMONS", "DOZER" })
    @Param({ "MANUAL", "MODELMAPPER", "DOZER" })
    private ObjectMapperType mapperType;

    private User user;

    private ObjectMapper<User, UserDTO> mapper;

    @Override
    protected void setUp() throws Exception {
        user = new User("Lukas", 10);
        for (int i = 0; i < friendsCount; i++) {
            User friend = new User("Martin" + i, 40);
            user.addFriend(friend);
        }

        mapper = MapperFactory.getMapper(mapperType, User.class, UserDTO.class);
    }

    public void timeMapObjects(int reps) {
        for (int i = 0; i < reps; i++) {
            UserDTO userDTO = new UserDTO();
            mapper.mapObjects(user, userDTO);
        }
    }

    public static void main(String[] args) {
        Runner.main(ObjectMapperBenchmark.class, args);
    }
}
