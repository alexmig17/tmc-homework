package com.epam.test.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface Dao<T, I extends Serializable> {

    Optional<T> get(I id);

    List<T> getAll();

    I update(T t);

    boolean delete(T t);
}


