package com.element.app.service;

import com.element.app.bean.Element;
import com.element.app.domain.ElementEntity;
import com.element.app.exception.ElementNotFoundException;
import com.element.app.repository.PeriodicTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PeriodicTableService {
    @Autowired
    PeriodicTableRepository periodicTableRepository;

    public List<Element> getAllElements() throws ElementNotFoundException {
        List<Element> elements = generateElements(periodicTableRepository.getAllElements());
        if (elements.size() == 0) {
            throw new ElementNotFoundException("Could not find all elements");
        }
        return elements;
    }

    public List<Element> getElementsByGroup(String group) throws ElementNotFoundException {
        List<Element> elements = generateElements(periodicTableRepository.getElementsByGroup(group));
        if (elements.size() == 0) {
            throw new ElementNotFoundException("Could not find any elements for group " + group + ".");
        }
        return elements;
    }

    public List<Element> getElementsByPeriod(String period) throws ElementNotFoundException {
        List<Element> elements = generateElements(periodicTableRepository.getElementsByPeriod(period));
        if (elements.size() == 0) {
            throw new ElementNotFoundException("Could not find any elements for group " + period + ".");
        }
        return elements;
    }

    public Element getElement(String atomicNumber) throws ElementNotFoundException {
        Element element = generateElement(periodicTableRepository.getElement(atomicNumber));
        if (element.getAtomicNumber() == null) {
            throw new ElementNotFoundException("Could not find an element with atomic number " + atomicNumber + ".");
        }
        return element;
    }

    private List<Element> generateElements(List<ElementEntity> elementEntities) {
        List<Element> elements = new ArrayList<>();
        elementEntities.forEach(elementEntity -> {
            Element element = new Element();
            element.setName(elementEntity.getName());
            element.setAtomicNumber(elementEntity.getAtomicNumber());
            elements.add(element);
        });
        return elements;
    }

    private Element generateElement(Optional<ElementEntity> elementEntity) {
        Element element = new Element();
        if (elementEntity.isPresent()) {
            element.setAtomicNumber(elementEntity.get().getAtomicNumber());
            element.setName(elementEntity.get().getName());
            element.setDiscoveryYear(elementEntity.get().getDiscoveryYear());
            element.setDiscoverers(elementEntity.get().getDiscoverers());
            element.setAppearance(elementEntity.get().getAppearance());
            element.setAlternativeName(elementEntity.get().getAlternativeName());
            element.setGroup(elementEntity.get().getGroup());
            element.setPeriod(elementEntity.get().getPeriod());
            element.setAtomicSymbol(elementEntity.get().getAtomicSymbol());
        }
        return element;
    }
}
