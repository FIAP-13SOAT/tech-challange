package com.fiapchallenge.garage.application.customer.list;

import com.fiapchallenge.garage.domain.customer.Customer;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListCustomerUseCase {

    Page<Customer> handle(CustomerFilterCmd filter, Pageable pageable);

    @Schema(name = "CustomerFilter", description = "Filtros para busca de clientes")
    public record CustomerFilterCmd(
        @Schema(description = "Nome do cliente", example = "John") String name,
        @Schema(description = "Email do cliente", example = "john@example.com") String email,
        @Schema(description = "CPF ou CNPJ do cliente", example = "123.456.789-00") String cpfCnpj
    ) {
        public boolean hasFilters() {
            return name != null || email != null || cpfCnpj != null;
        }
    }
}
