package br.com.ermig.personal_financial.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.ermig.personal_financial.controllers.dtos.ExpenseDTO;
import br.com.ermig.personal_financial.controllers.dtos.NewExpenseDTO;
import br.com.ermig.personal_financial.controllers.mappers.ExpenseMapper;
import br.com.ermig.personal_financial.repositories.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    
    private final ExpenseMapper expenseMapper;
    private final ExpenseRepository expenseRepository;

    public Mono<ExpenseDTO> createNew(NewExpenseDTO newExpenseDTO) {
        return Optional.ofNullable(newExpenseDTO)
            .map(expenseMapper::fromNewExpenseToExpense)
            .map(expenseRepository::save)
            .map(expenseMapper::fromExpenseToExpenseDTO)
            .map(Mono::just)
            .orElseThrow(() -> new IllegalArgumentException());
    }

    public Flux<ExpenseDTO> listAll() {
        return Flux.fromStream(
            expenseRepository.findAll()
                .stream()
                .map(expenseMapper::fromExpenseToExpenseDTO)
        );
    }

    public Mono<ExpenseDTO> findById(Long receivedId) {
        return Optional.ofNullable(receivedId)
            .filter(id -> id > 0)
            .flatMap(expenseRepository::findById)
            .map(expenseMapper::fromExpenseToExpenseDTO)
            .map(Mono::just)
            .orElseThrow(() -> new IllegalArgumentException());
    }

}
