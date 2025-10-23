package com.fiapchallenge.garage.unit.customer;

import com.fiapchallenge.garage.domain.customer.CpfCnpj;
import com.fiapchallenge.garage.shared.exception.SoatValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CpfCnpjTest {

    @ParameterizedTest
    @ValueSource(strings = {"11144477735", "12345678909", "98765432100"})
    void shouldCreateValidCpf(String cpf) {
        assertDoesNotThrow(() -> new CpfCnpj(cpf));
    }

    @ParameterizedTest
    @ValueSource(strings = {"111.444.777-35", "123.456.789-09", "987.654.321-00"})
    void shouldCreateValidFormattedCpf(String cpf) {
        assertDoesNotThrow(() -> new CpfCnpj(cpf));
    }

    @ParameterizedTest
    @ValueSource(strings = {"11222333000181", "12345678000195", "11444777000161"})
    void shouldCreateValidCnpj(String cnpj) {
        assertDoesNotThrow(() -> new CpfCnpj(cnpj));
    }

    @ParameterizedTest
    @ValueSource(strings = {"11.222.333/0001-81", "12.345.678/0001-95", "11.444.777/0001-61"})
    void shouldCreateValidFormattedCnpj(String cnpj) {
        assertDoesNotThrow(() -> new CpfCnpj(cnpj));
    }

    @Test
    void shouldThrowExceptionForNullValue() {
        assertThrows(SoatValidationException.class, () -> new CpfCnpj(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   "})
    void shouldThrowExceptionForEmptyOrBlankValues(String value) {
        assertThrows(SoatValidationException.class, () -> new CpfCnpj(value));
    }

    @ParameterizedTest
    @ValueSource(strings = {"00000000000", "11111111111", "22222222222"})
    void shouldThrowExceptionForCpfWithAllSameDigits(String cpf) {
        assertThrows(SoatValidationException.class, () -> new CpfCnpj(cpf));
    }

    @ParameterizedTest
    @ValueSource(strings = {"00000000000000", "11111111111111", "22222222222222"})
    void shouldThrowExceptionForCnpjWithAllSameDigits(String cnpj) {
        assertThrows(SoatValidationException.class, () -> new CpfCnpj(cnpj));
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345678901", "11144477736", "98765432101"})
    void shouldThrowExceptionForInvalidCpf(String cpf) {
        assertThrows(SoatValidationException.class, () -> new CpfCnpj(cpf));
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345678000100", "11222333000180", "98765432000101"})
    void shouldThrowExceptionForInvalidCnpj(String cnpj) {
        assertThrows(SoatValidationException.class, () -> new CpfCnpj(cnpj));
    }

    @Test
    void shouldReturnCorrectValue() {
        String value = "11144477735";
        CpfCnpj cpfCnpj = new CpfCnpj(value);
        assertEquals(value, cpfCnpj.getValue());
    }

    @Test
    void shouldBeEqualWhenSameValue() {
        CpfCnpj cpfCnpj1 = new CpfCnpj("11144477735");
        CpfCnpj cpfCnpj2 = new CpfCnpj("11144477735");
        assertEquals(cpfCnpj1, cpfCnpj2);
    }

    @Test
    void shouldHaveSameHashCodeWhenSameValue() {
        CpfCnpj cpfCnpj1 = new CpfCnpj("11144477735");
        CpfCnpj cpfCnpj2 = new CpfCnpj("11144477735");
        assertEquals(cpfCnpj1.hashCode(), cpfCnpj2.hashCode());
    }

    @Test
    void shouldReturnValueAsString() {
        String value = "11144477735";
        CpfCnpj cpfCnpj = new CpfCnpj(value);
        assertEquals(value, cpfCnpj.toString());
    }
}
