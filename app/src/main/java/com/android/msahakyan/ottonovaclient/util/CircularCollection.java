package com.android.msahakyan.ottonovaclient.util;

import java.util.Iterator;

/**
 * @author msahakyan
 */

class CircularCollection<T> implements Iterable<T> {

    private final Iterable<T> it;

    public CircularCollection(Iterable<T> it) {
        this.it = it;
    }

    @Override
    public Iterator<T> iterator() {
        return new CircularIterator();
    }

    private class CircularIterator implements Iterator<T> {
        // Start the ball rolling.
        Iterator<T> iter = it.iterator();

        @Override
        public boolean hasNext() {
            // Because structure is circular it always have a next.
            return true;
        }

        @Override
        public T next() {
            // Will loop until a collection has elements.
            while (!iter.hasNext()) {
                iter = it.iterator();
            }
            return iter.next();
        }
    }
}
