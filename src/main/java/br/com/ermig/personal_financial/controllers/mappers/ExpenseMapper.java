package br.com.ermig.personal_financial.controllers.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import br.com.ermig.personal_financial.controllers.dtos.ExpenseDTO;
import br.com.ermig.personal_financial.controllers.dtos.NewExpenseDTO;
import br.com.ermig.personal_financial.entities.Expense;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {

    ExpenseDTO fromExpenseToExpenseDTO(Expense expenseDTO);
    Expense fromNewExpenseToExpense(NewExpenseDTO newExpenseDTO);
    List<ExpenseDTO> fromExpenseToExpenseDTO(List<Expense> expenseDTO);
    
}
