package br.com.ermig.personal_financial.controllers.dtos;

import java.util.Date;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

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
public class NewExpenseDTO {
    
    @NotBlank
    @Size(min = 3, max = 255)
    private String personsName;
    
    @NotBlank
    @Size(min = 3, max = 255)
    private String description;

    @Past
    @NotNull
    private Date date;

    @Positive
    private Double value;
    
    @NotNull
    private Set<String> tags;

}
