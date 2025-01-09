package com.example.transfersApi.utils.calcs;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import com.example.transfersApi.dto.TransfersDTO;
import com.example.transfersApi.utils.rules.Rule;
import com.example.transfersApi.utils.rules.RulesProgram;

public class Calcs {
  public Double calculateFee(TransfersDTO dto){
    RulesProgram rulesClass = new RulesProgram();
    List<Rule> rules = rulesClass.readRules();

    Integer daysCount = this.calculateDaysFromToday(dto.getScheduledDate());
    Rule targetRule = rulesClass.findRule(rules, daysCount);
    
    Double fee1 = targetRule.getFee();
    Double fee2 = ((dto.getOriginalValue() / 100) * targetRule.getUpTo());

    return fee1 + fee2;
  }

  private Integer calculateDaysFromToday(Date receivedDate) {
    LocalDate receivedLocalDate = receivedDate.toInstant()
                                              .atZone(java.time.ZoneId.of("UTC")) 
                                              .toLocalDate();
    
    LocalDate currentDate = LocalDate.now(java.time.ZoneId.of("UTC"));
    long daysBetween = ChronoUnit.DAYS.between(currentDate, receivedLocalDate);
    
    return (int) daysBetween;
  }
}
