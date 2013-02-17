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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author dembol
 * @version $Revision$ $Date$
 */
public class UserMapperTest {

    @DataProvider(name = "Mapper-Provider")
    public Object[][] parameterTestProvider() {
        return new Object[][]{
                {MapperFactory.getMapper(ObjectMapperType.MANUAL, User.class, UserDTO.class)},
//                { MapperFactory.getMapper(ObjectMapperType.REFLECTION, User.class, UserDTO.class) },
//                {MapperFactory.getMapper(ObjectMapperType.COMMONS, User.class, UserDTO.class)},
                {MapperFactory.getMapper(ObjectMapperType.MODELMAPPER, User.class, UserDTO.class)},
                {MapperFactory.getMapper(ObjectMapperType.DOZER, User.class, UserDTO.class)}
        };
    }

    @Test(dataProvider = "Mapper-Provider")
    public void shouldMapObjects(ObjectMapper mapper) {
        // Given
        User user = new User("dembol", 20);
        User friend1 = new User("Joshua", 30);
        User friend2 = new User("Martin", 40);
        user.addFriend(friend1);
        user.addFriend(friend2);
        UserDTO userDTO = new UserDTO();

        // When
        mapper.mapObjects(user, userDTO);

        // Then
        assertThat(user.getName()).isEqualTo(userDTO.getUserName());
        assertThat(user.getAge()).isEqualTo(userDTO.getUserAge());
        assertThat(userDTO.getUserFriends()).hasSize(2);
    }

    @Test(dataProvider = "Mapper-Provider")
    public void shouldThrowSourceObjectPreconditionError(ObjectMapper mapper) {
        // Given
        Exception caughtException = null;
        UserDTO userDTO = new UserDTO();

        // When
        try {
            mapper.mapObjects(null, userDTO);
        } catch(Exception exception) {
            caughtException = exception;
        }

        // Then
        assertThat(caughtException).isExactlyInstanceOf(NullPointerException.class).hasMessage("source object should not be null");
    }

    @Test(dataProvider = "Mapper-Provider")
    public void shouldThrowDestinationObjectPreconditionError(ObjectMapper mapper) {
        // Given
        Exception caughtException = null;
        User user = new User("dembol", 20);

        // When
        try {
            mapper.mapObjects(user, null);
        } catch(Exception exception) {
            caughtException = exception;
        }

        assertThat(caughtException).isExactlyInstanceOf(NullPointerException.class).hasMessage("destination object should not be null");
    }
}
