package com.moodscapes.backend.moodscapes.backend.function;

import java.util.Objects;
import java.util.function.BiConsumer;

@FunctionalInterface
public interface TriConsumer<T, U, V> {

    void accept(T t, U u, V v);

}
