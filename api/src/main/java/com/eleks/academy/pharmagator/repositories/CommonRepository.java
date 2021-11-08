package com.eleks.academy.pharmagator.repositories;

import java.util.List;
import java.util.Optional;

public interface CommonRepository<T,ID> {
    List<T> findAll();

    Optional<T> findById(ID id);

    T save(T medicine);

    void deleteById(ID id);
}
