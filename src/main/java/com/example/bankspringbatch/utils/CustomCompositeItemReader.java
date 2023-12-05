package com.example.bankspringbatch.utils;

import org.springframework.batch.item.ItemReader;

import java.util.ArrayList;
import java.util.List;

public class CustomCompositeItemReader<T> implements ItemReader<T> {

    private List<ItemReader<T>> itemReaders = new ArrayList<>();
    private int currentIndex = 0;

    public void setItemReaders(List<ItemReader<T>> itemReaders) {
        this.itemReaders = itemReaders;
    }

    @Override
    public T read() throws Exception {
        T item = null;

        if (currentIndex < itemReaders.size()) {
            ItemReader<T> currentReader = itemReaders.get(currentIndex);
            item = currentReader.read();

            if (item == null) {
                // Move to the next reader if the current one is exhausted
                currentIndex++;
            }
        }

        return item;
    }
}
