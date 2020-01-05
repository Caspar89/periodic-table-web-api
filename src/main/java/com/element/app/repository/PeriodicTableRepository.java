package com.element.app.repository;

import com.element.app.domain.ElementEntity;
import com.element.app.database.InMemoryDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PeriodicTableRepository {
    @Autowired
    InMemoryDatabase inMemoryDatabase;

    public List<ElementEntity> getAllElements() {
        return inMemoryDatabase.getAllElements();
    }

    public List<ElementEntity> getElementsByGroup(String group) {
        return inMemoryDatabase.getElementsByGroup(group);
    }

    public List<ElementEntity> getElementsByPeriod(String period) {
        return inMemoryDatabase.getElementsByPeriod(period);
    }

    public Optional<ElementEntity> getElement(String atomicNumber) {
        return inMemoryDatabase.getElement(atomicNumber);
    }
}
