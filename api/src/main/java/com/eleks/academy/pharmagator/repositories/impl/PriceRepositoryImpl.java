package com.eleks.academy.pharmagator.repositories.impl;

import com.eleks.academy.pharmagator.entities.Price;
import com.eleks.academy.pharmagator.entities.PriceId;
import com.eleks.academy.pharmagator.repositories.PriceRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PriceRepositoryImpl implements PriceRepository {
    @Override
    public List<Price> findAll() {
        return null;
    }

    @Override
    public Optional<Price> findById(PriceId priceId) {
        return Optional.empty();
    }

    @Override
    public Price save(Price medicine) {
        return null;
    }

    @Override
    public void deleteById(PriceId priceId) {

    }
}
