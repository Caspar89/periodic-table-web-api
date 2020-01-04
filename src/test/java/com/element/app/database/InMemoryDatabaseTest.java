package com.element.app.database;

import com.element.app.domain.ElementEntity;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InMemoryDatabaseTest {
    @Autowired
    private InMemoryDatabase database;

    @Test
    public void shouldReadTheJsonFileCorrectly() throws IOException, ParseException {
        List<ElementEntity> elementEntities = database.getElementEntities();
        assertEquals("Hydrogen", elementEntities.get(0).getName());
        assertEquals("n/a", elementEntities.get(0).getAlternativeName());
        assertEquals("colorless gas", elementEntities.get(0).getAppearance());
        assertEquals("1", elementEntities.get(0).getAtomicNumber());
        assertEquals("1", elementEntities.get(0).getPeriod());
        assertEquals("H", elementEntities.get(0).getAtomicSymbol());
    }

    @Test
    public void shouldRemoveAnyNonNumbersFromTheGroupCorrectly() throws IOException, ParseException {
        List<ElementEntity> elementEntities = database.getElementEntities();
        assertEquals("1", elementEntities.get(0).getGroup());
    }

    @Test
    public void shouldReturn0IfTheGroupWasNotFound() throws IOException, ParseException {
        List<ElementEntity> elementEntities = database.getElementEntities();
        assertEquals("0", elementEntities.get(88).getGroup());
    }

    @Test
    public void shouldParseTheYearCorrectly() throws IOException, ParseException {
        List<ElementEntity> elementEntities = database.getElementEntities();
        assertEquals("1766", elementEntities.get(0).getDiscoveryYear());
    }

    @Test
    public void shouldParseTheYearCorrectlyWhenItContainsNonNumberCharacters() throws IOException, ParseException {
        List<ElementEntity> elementEntities = database.getElementEntities();
        assertEquals("1961–1971", elementEntities.get(102).getDiscoveryYear());
        assertEquals("2003, first announced", elementEntities.get(111).getDiscoveryYear());
    }

    @Test
    public void shouldParseTheYearCorrectlyWhenItIsTheOnlyFieldInDiscovery() throws IOException, ParseException {
        List<ElementEntity> elementEntities = database.getElementEntities();
        assertEquals("3000", elementEntities.get(50).getDiscoveryYear());
    }

    @Test
    public void shouldParseASingleDiscovererCorrectly() throws IOException, ParseException {
        List<ElementEntity> elementEntities = database.getElementEntities();
        assertEquals(elementEntities.get(0).getDiscoverers().length, 1);
        assertEquals("[Henry Cavendish]", Arrays.toString(elementEntities.get(0).getDiscoverers()));
    }

    @Test
    public void shouldParseUnknownDiscoverersToAnEmptyArray() throws IOException, ParseException {
        List<ElementEntity> elementEntities = database.getElementEntities();
        assertEquals(elementEntities.get(50).getDiscoverers().length, 0);
        assertEquals("[]", Arrays.toString(elementEntities.get(50).getDiscoverers()));
    }

    @Test
    public void shouldParseMultipleDiscoverersSplitByAndCorrectly() throws IOException, ParseException {
        List<ElementEntity> elementEntities = database.getElementEntities();
        assertEquals(elementEntities.get(1).getDiscoverers().length, 2);
        assertEquals("[Pierre Janssen, Norman Lockyer]", Arrays.toString(elementEntities.get(1).getDiscoverers()));
    }

    @Test
    public void shouldParseMultipleDiscoverersSplitByCommaCorrectly() throws IOException, ParseException {
        List<ElementEntity> elementEntities = database.getElementEntities();
        assertEquals(elementEntities.get(112).getDiscoverers().length, 2);
        assertEquals("[Joint Institute for Nuclear Research (JINR), Lawrence Livermore National Laboratory (LLNL)]", Arrays.toString(elementEntities.get(112).getDiscoverers()));
    }

    @Test
    public void shouldRemoveAnyExcessYearsFromDiscoverersCorrectly() throws IOException, ParseException {
        List<ElementEntity> elementEntities = database.getElementEntities();
        assertEquals(elementEntities.get(111).getDiscoverers().length, 3);
        assertEquals("[RIKEN, Joint Institute for Nuclear Research, Lawrence Livermore National Laboratory]", Arrays.toString(elementEntities.get(111).getDiscoverers()));
    }

    @Test
    public void shouldSanitizeYearsFromDiscoverersCorrectlyByRemovingNewLines() throws IOException, ParseException {
        List<ElementEntity> elementEntities = database.getElementEntities();
        assertEquals(elementEntities.get(111).getDiscoverers().length, 3);
        assertEquals("[RIKEN, Joint Institute for Nuclear Research, Lawrence Livermore National Laboratory]", Arrays.toString(elementEntities.get(111).getDiscoverers()));
    }

    @Test
    public void shouldRetainAnyOtherBrackersFromDiscoverersCorrectly() throws IOException, ParseException {
        List<ElementEntity> elementEntities = database.getElementEntities();
        assertEquals(elementEntities.get(112).getDiscoverers().length, 2);
        assertEquals("[Joint Institute for Nuclear Research (JINR), Lawrence Livermore National Laboratory (LLNL)]", Arrays.toString(elementEntities.get(112).getDiscoverers()));
    }
}
