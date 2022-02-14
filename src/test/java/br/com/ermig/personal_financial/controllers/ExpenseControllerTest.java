package br.com.ermig.personal_financial.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Set;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import br.com.ermig.personal_financial.controllers.dtos.ExpenseDTO;
import br.com.ermig.personal_financial.controllers.dtos.NewExpenseDTO;

@ActiveProfiles("test")
@AutoConfigureWebTestClient(timeout = "10000")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExpenseControllerTest {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Order(10)
    public void testCreateNewFromNullOrEmptyObject_ExpectedBadRequest() throws Exception {
        NewExpenseDTO newExpenseDTO = new NewExpenseDTO();
        webTestClient
            .post().uri(ExpenseController.EXPENSE_BASE_URL)
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(newExpenseDTO)
            .exchange()
            .expectStatus().isBadRequest()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody().jsonPath("$.message").isEqualTo("Date não deve ser nulo|Description não deve estar em branco|PersonsName não deve estar em branco|Tags não deve ser nulo");
    }

    @Test
    @Order(20)
    public void testCreateNewFromNullOrEmptyObject_ExpectedSuccess() throws Exception {
        NewExpenseDTO newExpenseDTO = getNewExpenseDTO();
        webTestClient
            .post().uri(ExpenseController.EXPENSE_BASE_URL)
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(newExpenseDTO)
            .exchange()
            .expectStatus().isCreated()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody(ExpenseDTO.class).value(expenseDTO ->
                assertEqualsExpense(newExpenseDTO, expenseDTO)
            );
    }

    @Test
    @Order(30)
    public void testFindById() throws Exception {
        NewExpenseDTO newExpenseDTO = getNewExpenseDTO();
        webTestClient
            .get().uri(ExpenseController.EXPENSE_BASE_URL + "/1")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody(ExpenseDTO.class).value(expenseDTO ->
                assertEqualsExpense(newExpenseDTO, expenseDTO)
            );
    }

    @Test
    @Order(40)
    public void testListAllExpenses() throws Exception {
        NewExpenseDTO newExpenseDTO = getNewExpenseDTO();
        webTestClient
            .get().uri(ExpenseController.EXPENSE_BASE_URL)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBodyList(ExpenseDTO.class).value(expenseDTOs -> {
                ExpenseDTO expenseDTO = expenseDTOs.get(0);
                assertEqualsExpense(newExpenseDTO, expenseDTO);
            });
    }

    private void assertEqualsExpense(NewExpenseDTO newExpenseDTO, ExpenseDTO expenseDTO) {
        assertEquals(1l, expenseDTO.getId());
        assertEquals(newExpenseDTO.getPersonsName(), expenseDTO.getPersonsName());
        assertEquals(newExpenseDTO.getDate(), expenseDTO.getDate());
        assertEquals(newExpenseDTO.getDescription(), expenseDTO.getDescription());
        assertEquals(newExpenseDTO.getValue(), expenseDTO.getValue());
        assertEquals(newExpenseDTO.getTags(), expenseDTO.getTags());
    }

    private NewExpenseDTO getNewExpenseDTO() throws ParseException {
        return NewExpenseDTO.builder()
            .personsName("Roberto Marinho Jr.")
            .date(DATE_FORMAT.parse("15/06/2019 19:46:53"))
            .description("Iate no Caribe")
            .value(1583467.98)
            .tags(Set.of("férias", "caribe", "farra"))
            .build();
    }

}
