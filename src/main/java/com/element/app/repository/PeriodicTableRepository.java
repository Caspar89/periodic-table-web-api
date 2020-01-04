package com.element.app.repository;

import com.element.app.bean.Element;
import com.element.app.database.InMemoryDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PeriodicTableRepository {
    @Autowired
    InMemoryDatabase inMemoryDatabase;

    public List<Element> getAllElements() {
        return inMemoryDatabase.getAll();
    }
}
