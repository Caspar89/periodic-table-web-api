package com.element.app.database;

import com.element.app.domain.ElementEntity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Repository
public class InMemoryDatabase {
    private Logger LOGGER = Logger.getLogger(InMemoryDatabase.class.getName());

    private List<ElementEntity> elementEntities;

    public InMemoryDatabase() throws IOException, ParseException {
        elementEntities = getElementEntities();
    }

    public List<ElementEntity> getAll() {
        return elementEntities;
    }

    public List<ElementEntity> getByGroup(String group) {
        return elementEntities.stream()
                .filter(elementEntity -> elementEntity.getGroup().equals(group))
                .collect(Collectors.toList());
    }

    public List<ElementEntity> getByPeriod(String period) {
        return elementEntities.stream()
                .filter(elementEntity -> elementEntity.getPeriod().equals(period))
                .collect(Collectors.toList());
    }

    List<ElementEntity> getElementEntities() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        ClassLoader classLoader = InMemoryDatabase.class.getClassLoader();
        JSONArray jsonArray = (JSONArray) parser.parse(new String(Files.readAllBytes(new File(Objects.requireNonNull(classLoader.getResource("periodic_table.json")).getFile()).toPath())));

        List<ElementEntity> elementEntities = new ArrayList<>();

        for (Object jsonObject : jsonArray) {
            try {
                ElementEntity elementEntity = new ElementEntity();
                JSONObject object = ((JSONObject) jsonObject);
                elementEntity.setName(object.get("name").toString());
                elementEntity.setAlternativeName(object.get("alternative_name").toString());
                elementEntity.setGroup(getGroup(object.get("group_block").toString()));
                elementEntity.setAppearance(object.get("appearance").toString());
                elementEntity.setAtomicNumber(object.get("atomic_number").toString());
                elementEntity.setDiscoveryYear(getYear(object.get("discovery").toString()));
                elementEntity.setDiscoverers(getDiscoverers(elementEntity.getDiscoveryYear(), (object.get("discovery").toString())));
                elementEntity.setPeriod((object.get("period").toString()));
                elementEntity.setAtomicSymbol((object.get("symbol").toString()));
                elementEntities.add(elementEntity);
            } catch (Exception e) {
                LOGGER.info("Could not parse element from json, skipping.");
            }
        }
        return elementEntities;
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
        return discoverers
                .replace(" (" + year + ")", "")
                .replaceAll("\n", "and ")
                .replaceAll("\\([\\d\\sa-z,]+\\)", "")
                .split(", | and ");
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
