package br.com.ermig.personal_financial.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.ermig.personal_financial.controllers.dtos.ExpenseDTO;
import br.com.ermig.personal_financial.controllers.dtos.NewExpenseDTO;
import br.com.ermig.personal_financial.services.ExpenseService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(ExpenseController.EXPENSE_BASE_URL)
public class ExpenseController {

    protected static final String EXPENSE_BASE_URL = "/expense";
    private final ExpenseService expenseService;

    @GetMapping
    public Flux<ExpenseDTO> getAllExpenses() {
       return expenseService.listAll();
    }

    @GetMapping("{id}")
    public Mono<ExpenseDTO> findById(@PathVariable Long id) {
        return expenseService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ExpenseDTO> createNew(@RequestBody NewExpenseDTO newExpenseDTO) {
        return ConstraintValidation.validate(newExpenseDTO, expenseService::createNew);
    }

}
