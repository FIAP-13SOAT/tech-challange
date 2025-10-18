package com.fiapchallenge.garage.integration.fixtures;

import com.fiapchallenge.garage.application.customer.CreateCustomerUseCase;
import com.fiapchallenge.garage.domain.customer.CpfCnpj;
import com.fiapchallenge.garage.domain.customer.Customer;
import com.fiapchallenge.garage.domain.customer.command.CreateCustomerCommand;

import java.util.Random;

public class CustomerFixture {
    private static final Random random = new Random();

    public static Customer createCustomer(CreateCustomerUseCase createCustomerUseCase) {

        CpfCnpj cpfCnpj = new CpfCnpj(generateValidCpf());

        CreateCustomerCommand command = new CreateCustomerCommand(
            "John Doe",
            "john@example.com",
            "123456789",
            cpfCnpj
        );
        return createCustomerUseCase.handle(command);
    }

    public static Customer createCustomer(CreateCustomerUseCase createCustomerUseCase, String name, String email, String phone) {
        CpfCnpj cpfCnpj = new CpfCnpj(generateValidCpf());
        CreateCustomerCommand command = new CreateCustomerCommand(name, email, phone, cpfCnpj);
        return createCustomerUseCase.handle(command);
    }

    public static String generateValidCpf() {
        int[] cpf = new int[11];

        for (int i = 0; i < 9; i++) {
            cpf[i] = random.nextInt(10);
        }

        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += cpf[i] * (10 - i);
        }
        cpf[9] = sum % 11 < 2 ? 0 : 11 - (sum % 11);

        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += cpf[i] * (11 - i);
        }
        cpf[10] = sum % 11 < 2 ? 0 : 11 - (sum % 11);

        StringBuilder sb = new StringBuilder();
        for (int digit : cpf) {
            sb.append(digit);
        }

        return sb.toString();
    }
}
