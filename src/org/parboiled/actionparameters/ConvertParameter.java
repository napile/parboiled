/*
 * Copyright (C) 2009 Mathias Doenitz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.parboiled.actionparameters;

import org.jetbrains.annotations.NotNull;
import org.parboiled.MatcherContext;
import org.parboiled.common.Converter;
import org.parboiled.common.Preconditions;

/**
 * An ActionParameter that takes two arguments, a string and a Converter. It evaluates to the result of the
 * Converter applied to the string.
 */
public class ConvertParameter implements ActionParameter {

    private final Object argument;
    private final Object converter;

    public ConvertParameter(Object argument, Object converter) {
        this.argument = argument;
        this.converter = converter;
    }

    public Object resolve(@NotNull MatcherContext<?> context) {
        Converter resolvedConverter = ActionParameterUtils.resolve(converter, context, Converter.class);
        Preconditions.checkNotNull(resolvedConverter);
        return resolvedConverter.parse(ActionParameterUtils.resolve(argument, context, String.class));
    }

    @Override
    public String toString() {
        return "convert using " + converter.getClass().getSimpleName() + '(' + argument + ')';
    }
}