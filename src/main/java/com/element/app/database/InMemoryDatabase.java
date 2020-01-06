package com.element.app.database;

import com.element.app.domain.ElementEntity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Repository
public class InMemoryDatabase {
    private Logger LOGGER = Logger.getLogger(InMemoryDatabase.class.getName());

    private List<ElementEntity> elementEntities;

    private String dataSource;

    public InMemoryDatabase(@Value("${data}") String newDataSource) {
        dataSource = newDataSource;
        elementEntities = getElementEntities();
    }

    public List<ElementEntity> getAllElements() {
        return elementEntities;
    }

    public List<ElementEntity> getElementsByGroup(String group) {
        return elementEntities.stream()
                .filter(elementEntity -> elementEntity.getGroup().equals(group))
                .collect(Collectors.toList());
    }

    public List<ElementEntity> getElementsByPeriod(String period) {
        return elementEntities.stream()
                .filter(elementEntity -> elementEntity.getPeriod().equals(period))
                .collect(Collectors.toList());
    }

    public Optional<ElementEntity> getElement(String atomicNumber) {
        return elementEntities.stream()
                .filter(elementEntity -> elementEntity.getAtomicNumber().equals(atomicNumber))
                .findFirst();
    }

    List<ElementEntity> getElementEntities() {
        List<ElementEntity> elementEntities = new ArrayList<>();
        try {
            JSONArray jsonArray = readPeriodicTable();
            for (Object jsonObject : jsonArray) {
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
            }
        } catch (IOException exception) {
            LOGGER.severe("Failed to read elements from json.");
            exception.printStackTrace();
        } catch (ParseException exception) {
            LOGGER.severe("Failed to parse elements from json.");
            exception.printStackTrace();
        }
        return elementEntities;
    }

    private JSONArray readPeriodicTable() throws ParseException, IOException {
        JSONParser parser = new JSONParser();
        InputStream inputStream =
                getClass().getClassLoader().getResourceAsStream(dataSource);
        StringBuilder textBuilder = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader
                (inputStream, Charset.forName(StandardCharsets.UTF_8.name())))) {
            int characterIndex;
            while ((characterIndex = reader.read()) != -1) {
                textBuilder.append((char) characterIndex);
            }
        }
        return (JSONArray) parser.parse(textBuilder.toString());
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
