package com.eleks.academy.pharmagator.repositories.impl;

import com.eleks.academy.pharmagator.entities.Medicine;
import com.eleks.academy.pharmagator.repositories.MedicineRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MedicineRepositoryImpl implements MedicineRepository {
    @Override
    public List<Medicine> findAll() {
        return null;
    }

    @Override
    public Optional<Medicine> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Medicine save(Medicine medicine) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }
}
