package com.element.app.service;

import com.element.app.bean.Element;
import com.element.app.domain.ElementEntity;
import com.element.app.exception.ElementNotFoundException;
import com.element.app.repository.PeriodicTableRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PeriodicTableServiceTest {
    @Autowired
    private PeriodicTableService service;

    @MockBean
    private PeriodicTableRepository mockPeriodicTableRepository;

    @Test
    public void shouldReturnAllElementsOnSuccess() throws ElementNotFoundException {
        when(mockPeriodicTableRepository.getAllElements()).thenReturn(getSuccessMockRepositoryResponse());
        List<Element> result = service.getAllElements();
        assertEquals(result.get(0).getName(), "first");
        assertEquals(result.get(1).getName(), "second");
    }

    @Test(expected = ElementNotFoundException.class)
    public void shouldThrowAnErrorWhenThereAreNoElements() throws ElementNotFoundException {
        when(mockPeriodicTableRepository.getAllElements()).thenReturn(new ArrayList<>());
        service.getAllElements();
    }

    @Test
    public void shouldReturnElementsByGroupOnSuccess() throws ElementNotFoundException {
        when(mockPeriodicTableRepository.getElementsByGroup(anyString())).thenReturn(getSuccessMockRepositoryResponse());
        List<Element> result = service.getElementsByGroup("");
        assertEquals(result.get(0).getName(), "first");
        assertEquals(result.get(1).getName(), "second");
    }

    @Test(expected = ElementNotFoundException.class)
    public void shouldThrowAnErrorWhenThereAreNoElementsByGroup() throws ElementNotFoundException {
        when(mockPeriodicTableRepository.getElementsByGroup(anyString())).thenReturn(new ArrayList<>());
        service.getElementsByGroup("");
    }

    @Test
    public void shouldReturnElementsByPeriodOnSuccess() throws ElementNotFoundException {
        when(mockPeriodicTableRepository.getElementsByPeriod(anyString())).thenReturn(getSuccessMockRepositoryResponse());
        List<Element> result = service.getElementsByPeriod("");
        assertEquals(result.get(0).getName(), "first");
        assertEquals(result.get(1).getName(), "second");
    }

    @Test(expected = ElementNotFoundException.class)
    public void shouldThrowAnErrorWhenThereAreNoElementsByPeriod() throws ElementNotFoundException {
        when(mockPeriodicTableRepository.getElementsByPeriod(anyString())).thenReturn(new ArrayList<>());
        service.getElementsByPeriod("");
    }

    @Test
    public void shouldReturnSingleElementOnSuccess() throws ElementNotFoundException {
        when(mockPeriodicTableRepository.getElement(anyString())).thenReturn(getSuccessMockRepositoryResponseSingle());
        Element result = service.getElement("");
        assertEquals(result.getName(), "first");
    }

    @Test(expected = ElementNotFoundException.class)
    public void shouldThrowAnErrorWhenThereIsNoSingleElement() throws ElementNotFoundException {
        when(mockPeriodicTableRepository.getElement(anyString())).thenReturn(Optional.empty());
        service.getElement("");
    }

    private List<ElementEntity> getSuccessMockRepositoryResponse() {
        List<ElementEntity> mockElementEntities = new ArrayList<>();
        ElementEntity firstElement = new ElementEntity();
        firstElement.setName("first");
        firstElement.setAtomicNumber("1");
        mockElementEntities.add(firstElement);
        ElementEntity secondElement = new ElementEntity();
        secondElement.setName("second");
        secondElement.setAtomicNumber("2");
        mockElementEntities.add(secondElement);
        return mockElementEntities;
    }

    private Optional<ElementEntity> getSuccessMockRepositoryResponseSingle() {
        ElementEntity element = new ElementEntity();
        element.setName("first");
        element.setAtomicNumber("1");
        return Optional.of(element);
    }
}
