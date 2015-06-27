package ru.yaal.hamcrest.verbose;

import java.util.Collection;

/**
 * @author yablokov a.
 */
class CollectionContainer {
    Collection<?> value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CollectionContainer container = (CollectionContainer) o;
        return !(value != null ? !value.equals(container.value) : container.value != null);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
