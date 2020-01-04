package com.element.app.service;

import com.element.app.bean.Element;
import com.element.app.domain.ElementEntity;
import com.element.app.repository.PeriodicTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PeriodicTableService {
    @Autowired
    PeriodicTableRepository periodicTableRepository;

    public List<Element> getAllElements() {
        return generateReturnObject(periodicTableRepository.getAllElements());
    }

    public List<Element> getElementsByGroup(String group) {
        return generateReturnObject(periodicTableRepository.getElementsByGroup(group));
    }

    public List<Element> getElementsByPeriod(String period) {
        return generateReturnObject(periodicTableRepository.getElementsByPeriod(period));
    }

    private List<Element> generateReturnObject(List<ElementEntity> elementEntities) {
        List<Element> elements = new ArrayList<>();
        elementEntities.forEach(elementEntity -> {
            Element element = new Element();
            element.setName(elementEntity.getName());
            element.setAtomicNumber(elementEntity.getAtomicNumber());
            elements.add(element);
        });
        return elements;
    }
}
