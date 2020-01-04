package com.element.app.database;

import com.element.app.bean.Element;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Repository
public class InMemoryDatabase {
    private Logger LOGGER = Logger.getLogger(InMemoryDatabase.class.getName());

    private List<Element> elements;

    public InMemoryDatabase() throws IOException, ParseException {
        elements = getElements();
    }

    public List<Element> getAll() {
        return elements;
    }

    public List<Element> getByGroup(Integer group) {
        return elements.stream()
                .filter(element -> element.getGroup().equals(group))
                .collect(Collectors.toList());
    }

    public List<Element> getByPeriod() {
        return elements;
    }

    List<Element> getElements() throws IOException, ParseException {
        int counter = 0;
        JSONParser parser = new JSONParser();
        ClassLoader classLoader = InMemoryDatabase.class.getClassLoader();
        JSONArray jsonArray = (JSONArray) parser.parse(new String(Files.readAllBytes(new File(Objects.requireNonNull(classLoader.getResource("periodic_table.json")).getFile()).toPath())));

        List<Element> elements = new ArrayList<>();

        for (Object jsonObject : jsonArray) {
            try {
                Element element = new Element();
                JSONObject object = ((JSONObject) jsonObject);
                element.setName(object.get("name").toString());
                element.setAlternativeName(object.get("alternative_name").toString());
                element.setGroup(getGroup(object.get("group_block").toString()));
                element.setAppearance(object.get("appearance").toString());
                element.setAtomicNumber(object.get("atomic_number").toString());
                element.setDiscoveryYear(getYear(object.get("discovery").toString()));
                element.setDiscoverers(getDiscoverers(element.getDiscoveryYear(), (object.get("discovery").toString())));
                System.out.println(counter + ": " + element.getGroup());
                element.setPeriod((object.get("period").toString()));
                element.setAtomicSymbol((object.get("symbol").toString()));
                elements.add(element);
                counter++;
            } catch (Exception e) {
                LOGGER.info("Could not parse element from json, skipping.");
            }
        }
        return elements;
    }

    private String getYear(String unparsedYear) {
        String reversedYear = reverseString(unparsedYear);
        if (')' == reversedYear.charAt(0)) {
            Pattern pattern = Pattern.compile(".+?(?=\\()");
            Matcher matcher = pattern.matcher(reversedYear);
            if (matcher.find())
            {
                String match = reverseString(matcher.group(0));
                return match.substring(0, match.length() - 1);
            }
        } else if (unparsedYear.matches(".*\\d.*") && !unparsedYear.contains("(")) {
            return unparsedYear;
        }
        return "unknown";
    }

    private String[] getDiscoverers(String year, String discoverers) {
        if (year.equals(discoverers) || discoverers.equals("n/a")) {
            return new String[0];
        }
        return discoverers.replace(" (" + year + ")", "").replaceAll("\\([\\d\\sa-z,]+\\)", "").split(", | and ");
    }

    private String getGroup(String group) {
        String parsedGroup = group.replaceAll("\\D+","");
        if (parsedGroup.equals("")) {
            return "0";
        }
        return parsedGroup;
    }

    private String reverseString(String input) {
        String reverse = "";
        for (int i = input.length() - 1; i >= 0; i--)
        {
            reverse += input.charAt(i);
        }
        return reverse;
    }
}
