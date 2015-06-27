package ru.yaal.hamcrest.verbose;

import java.util.Arrays;

/**
 * @author yablokov a.
 */
class ArrayContainer {
    Object[] value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArrayContainer container = (ArrayContainer) o;
        return Arrays.equals(value, container.value);
    }

    @Override
    public int hashCode() {
        return value != null ? Arrays.hashCode(value) : 0;
    }
}
