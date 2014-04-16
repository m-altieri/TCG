
package com.skiwi.tcg.model.objects;

import com.skiwi.tcg.model.cards.Card;
import com.skiwi.tcg.utils.Checker;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 *
 * @author Frank van Heeswijk
 */
public class Hand implements Iterable<Card> {
    private final List<Card> list = new ArrayList<>();
    private final int capacity;

    public Hand(final int capacity) {
        Checker.checkArgument(capacity > 0, "capacity should be strictly positive");
        this.capacity = capacity;
    }

    public boolean isFull() {
        return (list.size() == capacity);
    }

    public void add(final Card card) {
        Objects.requireNonNull(card);
        Checker.checkState(!isFull(), "hand is full");
        list.add(card);
    }

    public Card get(final int index) {
        checkIndex(index);
        return list.get(index);
    }

    public Card play(final int index) {
        checkIndex(index);
        return list.remove(index);
    }

    public void swap(final int indexOne, final int indexTwo) {
        checkIndex(indexOne);
        checkIndex(indexTwo);
        Collections.swap(list, indexOne, indexTwo);
    }
    
    @Override
    public String toString() {
        return "Hand(" + capacity + ", " + list + ")";
    }

    @Override
    public Iterator<Card> iterator() {
        return list.iterator();
    }
    
    @Override
    public void forEach(final Consumer<? super Card> action) {
        Objects.requireNonNull(action);
        list.forEach(action);
    }
    
    @Override
    public Spliterator<Card> spliterator() {
        return list.spliterator();
    }
    
    public Stream<Card> stream() {
        return list.stream();
    }
    
    public Stream<Card> parallelStream() {
        return list.parallelStream();
    }
    
    private void checkIndex(final int index) {
        Checker.checkIndex(index >= 0 && index < list.size(), "index should be between 0 and " + list.size());
    }
}