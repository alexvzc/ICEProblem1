/*
 * Copyright (C) 2017 Alejandro Vazquez

 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package mx.avc.iceproblem1;

import static java.lang.String.format;
import java.util.Map.Entry;
import java.util.Objects;
import static java.util.Objects.hash;

/**
 * Utilities missing from Java
 * @author alexv
 */
public interface Utils {

    public static <K,V> Entry<K, V> pair(K key, V value) {
        return new Entry<K, V>() {
            @Override public K getKey() { return key; }

            @Override public V getValue() { return value; }

            @Override public V setValue(V value) {
                throw new UnsupportedOperationException("Immutable");
            }

            @Override  public boolean equals(Object obj) {
                if(this == obj) {
                    return true;
                }
                if(obj == null || !(obj instanceof Entry)) {
                    return false;
                }
                Entry<?,?> that = (Entry<?,?>)obj;
                return Objects.equals(key, that.getKey())
                        && Objects.equals(value, that.getValue());
            }

            @Override public int hashCode() { return hash(key, value); }

            @Override public String toString() {
                return format("pair(key=%s,value=%s)", key, value);
            }
        };
    }
}