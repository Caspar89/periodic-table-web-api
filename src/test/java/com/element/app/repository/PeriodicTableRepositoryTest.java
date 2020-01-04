package com.element.app.repository;

import com.element.app.database.InMemoryDatabase;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;


public class PeriodicTableRepositoryTest {

    @MockBean
    InMemoryDatabase mockInMemoryDatabase;

    @Resource
    private PeriodicTableRepository repository;

    @Test
    public void shouldSaveAnElementToThePeriodicTableDatabase() {
    }
}
