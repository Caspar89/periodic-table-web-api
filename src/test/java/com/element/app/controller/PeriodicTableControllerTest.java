package com.element.app.controller;

import com.element.app.bean.Element;
import com.element.app.exception.ElementNotFoundException;
import com.element.app.service.PeriodicTableService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PeriodicTableControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    PeriodicTableService mockPeriodicTableService;

    @Test
    public void shouldReturnOkOnPing() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/ping"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnOkOnSuccessForAllElements() throws Exception {
        when(mockPeriodicTableService.getAllElements()).thenReturn(getSuccessMockRepositoryResponse());
        mvc.perform(MockMvcRequestBuilders.get("/elements"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnNotFoundWhenAnErrorIsThrownForAllElements() throws Exception {
        when(mockPeriodicTableService.getAllElements()).thenThrow(new ElementNotFoundException(""));
        mvc.perform(MockMvcRequestBuilders.get("/elements"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnOkOnSuccessForGroups() throws Exception {
        when(mockPeriodicTableService.getElementsByGroup("1")).thenReturn(getSuccessMockRepositoryResponse());
        mvc.perform(MockMvcRequestBuilders.get("/elements/group/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnNotFoundWhenAnErrorIsThrownForGroups() throws Exception {
        when(mockPeriodicTableService.getElementsByGroup("1")).thenThrow(new ElementNotFoundException(""));
        mvc.perform(MockMvcRequestBuilders.get("/elements/group/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnOkOnSuccessForPeriods() throws Exception {
        when(mockPeriodicTableService.getElementsByPeriod("1")).thenReturn(getSuccessMockRepositoryResponse());
        mvc.perform(MockMvcRequestBuilders.get("/elements/period/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnNotFoundWhenAnErrorIsThrownForPeriods() throws Exception {
        when(mockPeriodicTableService.getElementsByPeriod("1")).thenThrow(new ElementNotFoundException(""));
        mvc.perform(MockMvcRequestBuilders.get("/elements/period/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnOkOnSuccessForSingleElement() throws Exception {
        when(mockPeriodicTableService.getElement("1")).thenReturn(getSuccessMockRepositoryResponseSingle());
        mvc.perform(MockMvcRequestBuilders.get("/element/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnNotFoundWhenAnErrorIsThrownForSingleElement() throws Exception {
        when(mockPeriodicTableService.getElement("1")).thenThrow(new ElementNotFoundException(""));
        mvc.perform(MockMvcRequestBuilders.get("/element/1"))
                .andExpect(status().isNotFound());
    }

    private List<Element> getSuccessMockRepositoryResponse() {
        List<Element> mockElementEntities = new ArrayList<>();
        Element firstElement = new Element();
        firstElement.setName("first");
        firstElement.setAtomicNumber("1");
        mockElementEntities.add(firstElement);
        Element secondElement = new Element();
        secondElement.setName("second");
        secondElement.setAtomicNumber("2");
        mockElementEntities.add(secondElement);
        return mockElementEntities;
    }

    private Element getSuccessMockRepositoryResponseSingle() {
        Element element = new Element();
        element.setName("first");
        element.setAtomicNumber("1");
        return element;
    }
}
