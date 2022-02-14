package br.com.ermig.personal_financial.controllers.dtos;

import java.util.Date;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDTO {
    
    private Long id;
    private String personsName;
    private String description;
    private Date date;
    private Double value;
    private Set<String> tags;

}
