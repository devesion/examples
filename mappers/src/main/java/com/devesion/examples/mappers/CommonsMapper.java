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

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * @author dembol
 * @version $Revision$ $Date$
 */
class CommonsMapper extends AbstractMapper {

    @Override
    public void doMapObjects(Object src, Object dst) {
        try {
            BeanUtils.copyProperties(dst, src);
        } catch (IllegalAccessException e) {
            throw new UnsupportedOperationException(e);
        } catch (InvocationTargetException e) {
            throw new UnsupportedOperationException(e);
        }
    }
}
