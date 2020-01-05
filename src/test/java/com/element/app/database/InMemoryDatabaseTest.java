package com.element.app.database;

import com.element.app.domain.ElementEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InMemoryDatabaseTest {
    @Autowired
    private InMemoryDatabase database;

    @Test
    public void shouldReturnAllElements() {
        List<ElementEntity> elementEntities = database.getAllElements();
        assertEquals(elementEntities.size(), 117);
    }

    @Test
    public void shouldReturnAllElementsByGroup() {
        List<ElementEntity> elementEntities = database.getElementsByGroup("1");
        assertEquals(elementEntities.size(), 7);
    }

    @Test
    public void shouldReturnNoElementsByGroupIfNoneFound() {
        List<ElementEntity> elementEntities = database.getElementsByGroup("99");
        assertEquals(elementEntities.size(), 0);
    }

    @Test
    public void shouldReturnAllElementsByPeriod() {
        List<ElementEntity> elementEntities = database.getElementsByPeriod("1");
        assertEquals(elementEntities.size(), 2);
    }

    @Test
    public void shouldReturnNoElementsByPeriodIfNoneFound() {
        List<ElementEntity> elementEntities = database.getElementsByPeriod("99");
        assertEquals(elementEntities.size(), 0);
    }

    @Test
    public void shouldReturnASingleElementAsAnOptional() {
        Optional<ElementEntity> elementEntityOptional = database.getElement("1");
        assertTrue(elementEntityOptional.isPresent());
    }

    @Test
    public void shouldReturnASingleElementAsAnOptionalWhenNoneExist() {
        Optional<ElementEntity> elementEntityOptional = database.getElement("300");
        assertTrue(!elementEntityOptional.isPresent());
    }

    @Test
    public void shouldReadTheJsonFileCorrectly() {
        List<ElementEntity> elementEntities = database.getElementEntities();
        assertEquals("Hydrogen", elementEntities.get(0).getName());
        assertEquals("n/a", elementEntities.get(0).getAlternativeName());
        assertEquals("colorless gas", elementEntities.get(0).getAppearance());
        assertEquals("1", elementEntities.get(0).getAtomicNumber());
        assertEquals("1", elementEntities.get(0).getPeriod());
        assertEquals("H", elementEntities.get(0).getAtomicSymbol());
    }

    @Test
    public void shouldRemoveAnyNonNumbersFromTheGroupCorrectly() {
        List<ElementEntity> elementEntities = database.getElementEntities();
        assertEquals("1", elementEntities.get(0).getGroup());
    }

    @Test
    public void shouldReturn0IfTheGroupWasNotFound() {
        List<ElementEntity> elementEntities = database.getElementEntities();
        assertEquals("0", elementEntities.get(88).getGroup());
    }

    @Test
    public void shouldParseTheYearCorrectly() {
        List<ElementEntity> elementEntities = database.getElementEntities();
        assertEquals("1766", elementEntities.get(0).getDiscoveryYear());
    }

    @Test
    public void shouldParseTheYearCorrectlyWhenItContainsNonNumberCharacters() {
        List<ElementEntity> elementEntities = database.getElementEntities();
        assertEquals("1961–1971", elementEntities.get(102).getDiscoveryYear());
        assertEquals("2003, first announced", elementEntities.get(111).getDiscoveryYear());
    }

    @Test
    public void shouldParseTheYearCorrectlyWhenItIsTheOnlyFieldInDiscovery() {
        List<ElementEntity> elementEntities = database.getElementEntities();
        assertEquals("3000", elementEntities.get(50).getDiscoveryYear());
    }

    @Test
    public void shouldParseASingleDiscovererCorrectly() {
        List<ElementEntity> elementEntities = database.getElementEntities();
        assertEquals(elementEntities.get(0).getDiscoverers().length, 1);
        assertEquals("[Henry Cavendish]", Arrays.toString(elementEntities.get(0).getDiscoverers()));
    }

    @Test
    public void shouldParseUnknownDiscoverersToAnEmptyArray() {
        List<ElementEntity> elementEntities = database.getElementEntities();
        assertEquals(elementEntities.get(50).getDiscoverers().length, 0);
        assertEquals("[]", Arrays.toString(elementEntities.get(50).getDiscoverers()));
    }

    @Test
    public void shouldParseMultipleDiscoverersSplitByAndCorrectly() {
        List<ElementEntity> elementEntities = database.getElementEntities();
        assertEquals(elementEntities.get(1).getDiscoverers().length, 2);
        assertEquals("[Pierre Janssen, Norman Lockyer]", Arrays.toString(elementEntities.get(1).getDiscoverers()));
    }

    @Test
    public void shouldParseMultipleDiscoverersSplitByCommaCorrectly() {
        List<ElementEntity> elementEntities = database.getElementEntities();
        assertEquals(elementEntities.get(112).getDiscoverers().length, 2);
        assertEquals("[Joint Institute for Nuclear Research (JINR), Lawrence Livermore National Laboratory (LLNL)]", Arrays.toString(elementEntities.get(112).getDiscoverers()));
    }

    @Test
    public void shouldRemoveAnyExcessYearsFromDiscoverersCorrectly() {
        List<ElementEntity> elementEntities = database.getElementEntities();
        assertEquals(elementEntities.get(111).getDiscoverers().length, 3);
        assertEquals("[RIKEN, Joint Institute for Nuclear Research, Lawrence Livermore National Laboratory]", Arrays.toString(elementEntities.get(111).getDiscoverers()));
    }

    @Test
    public void shouldSanitizeYearsFromDiscoverersCorrectlyByRemovingNewLines() {
        List<ElementEntity> elementEntities = database.getElementEntities();
        assertEquals(elementEntities.get(111).getDiscoverers().length, 3);
        assertEquals("[RIKEN, Joint Institute for Nuclear Research, Lawrence Livermore National Laboratory]", Arrays.toString(elementEntities.get(111).getDiscoverers()));
    }

    @Test
    public void shouldRetainAnyOtherBrackersFromDiscoverersCorrectly() {
        List<ElementEntity> elementEntities = database.getElementEntities();
        assertEquals(elementEntities.get(112).getDiscoverers().length, 2);
        assertEquals("[Joint Institute for Nuclear Research (JINR), Lawrence Livermore National Laboratory (LLNL)]", Arrays.toString(elementEntities.get(112).getDiscoverers()));
    }
}
