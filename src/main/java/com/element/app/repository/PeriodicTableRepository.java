package com.element.app.repository;

import com.element.app.domain.ElementEntity;
import com.element.app.database.InMemoryDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PeriodicTableRepository {
    @Autowired
    InMemoryDatabase inMemoryDatabase;

    public List<ElementEntity> getAllElements() {
        return inMemoryDatabase.getAll();
    }

    public List<ElementEntity> getElementsByGroup(String group) {
        return inMemoryDatabase.getByGroup(group);
    }

    public List<ElementEntity> getElementsByPeriod(String period) {
        return inMemoryDatabase.getByPeriod(period);
    }
}
