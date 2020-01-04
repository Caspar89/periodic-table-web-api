package com.element.app.database;

import com.element.app.bean.Element;
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
        List<Element> elements = database.getElements();
        assertEquals("Hydrogen", elements.get(0).getName());
        assertEquals("n/a", elements.get(0).getAlternativeName());
        assertEquals("colorless gas", elements.get(0).getAppearance());
        assertEquals("1", elements.get(0).getAtomicNumber());
        assertEquals("1", elements.get(0).getPeriod());
        assertEquals("H", elements.get(0).getAtomicSymbol());
    }

    @Test
    public void shouldRemoveAnyNonNumbersFromTheGroupCorrectly() throws IOException, ParseException {
        List<Element> elements = database.getElements();
        assertEquals("1", elements.get(0).getGroup());
    }

    @Test
    public void shouldReturn0IfTheGroupWasNotFound() throws IOException, ParseException {
        List<Element> elements = database.getElements();
        assertEquals("0", elements.get(88).getGroup());
    }

    @Test
    public void shouldParseTheYearCorrectly() throws IOException, ParseException {
        List<Element> elements = database.getElements();
        assertEquals("1766", elements.get(0).getDiscoveryYear());
    }

    @Test
    public void shouldParseTheYearCorrectlyWhenItContainsNonNumberCharacters() throws IOException, ParseException {
        List<Element> elements = database.getElements();
        assertEquals("1961–1971", elements.get(102).getDiscoveryYear());
        assertEquals("2003, first announced", elements.get(111).getDiscoveryYear());
    }

    @Test
    public void shouldParseASingleDiscovererCorrectly() throws IOException, ParseException {
        List<Element> elements = database.getElements();
        assertEquals("[Henry Cavendish]", Arrays.toString(elements.get(0).getDiscoverers()));
    }

    @Test
    public void shouldParseMultipleDiscoverersCorrectlySplitByAnd() throws IOException, ParseException {
        List<Element> elements = database.getElements();
        assertEquals(elements.get(1).getDiscoverers().length, 2);
        assertEquals("[Pierre Janssen, Norman Lockyer]", Arrays.toString(elements.get(1).getDiscoverers()));
    }

    @Test
    public void shouldParseMultipleDiscoverersCorrectlySplitByComma() throws IOException, ParseException {
        List<Element> elements = database.getElements();
        assertEquals(elements.get(112).getDiscoverers().length, 2);
        assertEquals("[Joint Institute for Nuclear Research (JINR), Lawrence Livermore National Laboratory (LLNL)]", Arrays.toString(elements.get(112).getDiscoverers()));
    }

    @Test
    public void shouldRemoveAnyExcessYearsFromDiscoverersCorrectly() throws IOException, ParseException {
        List<Element> elements = database.getElements();
        assertEquals(elements.get(111).getDiscoverers().length, 2);
        assertEquals("[RIKEN \n" +
                "Joint Institute for Nuclear Research, Lawrence Livermore National Laboratory]", Arrays.toString(elements.get(111).getDiscoverers()));
    }

    @Test
    public void shouldRetainAnyOtherBrackersFromDiscoverersCorrectly() throws IOException, ParseException {
        List<Element> elements = database.getElements();
        assertEquals(elements.get(112).getDiscoverers().length, 2);
        assertEquals("[Joint Institute for Nuclear Research (JINR), Lawrence Livermore National Laboratory (LLNL)]", Arrays.toString(elements.get(112).getDiscoverers()));
    }
}
