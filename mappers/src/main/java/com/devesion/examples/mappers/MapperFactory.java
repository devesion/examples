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
import com.google.common.base.Preconditions;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dembol
 * @version $Revision$ $Date$
 */
@SuppressWarnings("unused")
public final class MapperFactory {

    private static Map<MapperSpecification, ObjectMapper<?, ?>> mappers = new HashMap<MapperSpecification, ObjectMapper<?, ?>>();

    static {
        registerMapper(ObjectMapperType.MANUAL, new ManualUserMapper(), User.class, UserDTO.class);
        registerMapper(ObjectMapperType.REFLECTION, new DoppioUserMapper(), User.class, UserDTO.class);
        registerMapper(ObjectMapperType.COMMONS, new CommonsMapper());
        registerMapper(ObjectMapperType.MODELMAPPER, new ModelMapper());
        registerMapper(ObjectMapperType.DOZER, new DozerMapper());
    }

    private MapperFactory() {
        throw new AssertionError();
    }

    public static void registerMapper(ObjectMapperType type, ObjectMapper<?, ?> mapper) {
        mappers.put(new MapperSpecification(type), mapper);
    }

    public static void registerMapper(ObjectMapperType type, ObjectMapper<?, ?> mapper, Class<?> classSrc, Class<?> classDst) {
        Preconditions.checkNotNull(mapper);
        mappers.put(new MapperSpecification(type, classSrc, classDst), mapper);
    }

    @SuppressWarnings("unchecked")
    public static <S, D> ObjectMapper<S, D> getMapper(ObjectMapperType type, Class<S> classSrc, Class<D> classDst) {
        for (Map.Entry<MapperSpecification, ObjectMapper<?, ?>> entry : mappers.entrySet()) {
            if (entry.getKey().canMap(type, classSrc, classDst)) {
                return (ObjectMapper<S, D>) entry.getValue();
            }
        }

        throw new IllegalArgumentException("unknown mapper " + type);
    }

    private static class MapperSpecification {
        @Getter
        private ObjectMapperType type;

        @Getter
        private Class<?> classSrc;

        @Getter
        private Class<?> classDst;

        MapperSpecification(ObjectMapperType type, Class<?> classSrc, Class<?> classDst) {
            this.type = type;
            this.classSrc = classSrc;
            this.classDst = classDst;
        }

        public boolean canMap(ObjectMapperType type, Class<?> classSrc, Class<?> classDst) {
            Preconditions.checkNotNull(type);
            Preconditions.checkNotNull(classSrc);
            Preconditions.checkNotNull(classDst);
            return (type.equals(this.type)) && (this.classSrc.isAssignableFrom(classSrc)) && (this.classDst.isAssignableFrom(this.classDst));
        }

        MapperSpecification(ObjectMapperType type) {
            this(type, Object.class, Object.class);
        }
    }
}
