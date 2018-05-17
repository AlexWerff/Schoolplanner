package com.applicationswp.datacontroller;

import java.util.List;

/**
 * Interface fuer DatenController
 * Wird von allen DatenControllern implementiert
 */
public interface IEntryList<T> {
    void save();
    void load();
    boolean addEntry(T value);
    boolean removeEntry(T value);
    boolean updateEntry(T value);
    List<T> getEntries();
}
