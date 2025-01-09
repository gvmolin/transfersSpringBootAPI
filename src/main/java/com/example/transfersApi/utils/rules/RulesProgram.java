package com.example.transfersApi.utils.rules;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RulesProgram {

  public List<Rule> readRules() {
    File jsonFile = new File("src\\main\\java\\com\\example\\transfersApi\\utils\\rules\\rulesData.json");

    if (!jsonFile.exists()) {
      throw new RuntimeException("File not found: " + jsonFile.getPath());
    }

    ObjectMapper objectMapper = new ObjectMapper();
    List<Rule> dataList = null;

    try {
      dataList = objectMapper.readValue(jsonFile, objectMapper.getTypeFactory().constructCollectionType(List.class, Rule.class));
    } catch (IOException e) {
      throw new RuntimeException("Error reading the JSON file: " + e.getMessage());
    }

    return dataList;
  }

  public Rule findRule(List<Rule> rules, Integer daysCount) {
    if (rules == null || rules.isEmpty()) {
      return null;
    }

    Rule target = null;
    for (Rule rule : rules) {
      String[] parts = rule.getDays().split("-");

      int start = Integer.parseInt(parts[0]);
      int end = Integer.parseInt(parts[1]);

      if (start <= daysCount && end >= daysCount) {
        target = rule; 
        break;  
      }
    }

    return target; 
  }

}
