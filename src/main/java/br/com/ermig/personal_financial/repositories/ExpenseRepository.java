package br.com.ermig.personal_financial.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ermig.personal_financial.entities.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
