package com.fiapchallenge.garage.domain.customer;

import com.fiapchallenge.garage.shared.exception.SoatValidationException;

import java.util.Objects;

public class CpfCnpj {

    private final String value;

    public CpfCnpj(String value) {
        if (!isValid(value)) {
            throw new SoatValidationException("Invalid CPF/CNPJ: " + value);
        }
        this.value = value;
    }

    private boolean isValid(String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }

        String cleanValue = value.replaceAll("\\D", "");

        if (cleanValue.length() == 11) {
            return isValidCpf(cleanValue);
        } else if (cleanValue.length() == 14) {
            return isValidCnpj(cleanValue);
        }

        return false;
    }

    private boolean isValidCpf(String cpf) {
        if (cpf.matches("(\\d)\\1{10}")) return false;

        int[] digits = cpf.chars().map(c -> c - '0').toArray();

        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += digits[i] * (10 - i);
        }
        int firstCheck = 11 - (sum % 11);
        if (firstCheck >= 10) firstCheck = 0;

        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += digits[i] * (11 - i);
        }
        int secondCheck = 11 - (sum % 11);
        if (secondCheck >= 10) secondCheck = 0;

        return digits[9] == firstCheck && digits[10] == secondCheck;
    }

    private boolean isValidCnpj(String cnpj) {
        if (cnpj.matches("(\\d)\\1{13}")) return false;

        int[] digits = cnpj.chars().map(c -> c - '0').toArray();
        int[] weights1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] weights2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        int sum = 0;
        for (int i = 0; i < 12; i++) {
            sum += digits[i] * weights1[i];
        }
        int firstCheck = sum % 11 < 2 ? 0 : 11 - (sum % 11);

        sum = 0;
        for (int i = 0; i < 13; i++) {
            sum += digits[i] * weights2[i];
        }
        int secondCheck = sum % 11 < 2 ? 0 : 11 - (sum % 11);

        return digits[12] == firstCheck && digits[13] == secondCheck;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CpfCnpj cpfCnpj = (CpfCnpj) o;
        return Objects.equals(value, cpfCnpj.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
