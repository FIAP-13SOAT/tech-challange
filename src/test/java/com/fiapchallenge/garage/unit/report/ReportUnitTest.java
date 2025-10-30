package com.fiapchallenge.garage.unit.report;

import com.fiapchallenge.garage.application.report.command.GenerateServiceOrderExecutionReportCommand;
import com.fiapchallenge.garage.application.report.service.GenerateServiceOrderExecutionReportService;
import com.fiapchallenge.garage.domain.serviceorderexecution.ServiceOrderExecution;
import com.fiapchallenge.garage.domain.serviceorderexecution.ServiceOrderExecutionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportUnitTest {

    @Mock
    private ServiceOrderExecutionRepository serviceOrderExecutionRepository;

    @InjectMocks
    private GenerateServiceOrderExecutionReportService generateServiceOrderExecutionReportService;

    private GenerateServiceOrderExecutionReportCommand command;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    @BeforeEach
    void setUp() {
        startDate = LocalDate.of(2023, 10, 1);
        endDate = LocalDate.of(2023, 10, 31);
        command = new GenerateServiceOrderExecutionReportCommand(startDate, endDate);

        startDateTime = startDate.atStartOfDay();
        endDateTime = endDate.atTime(23, 59, 59);
    }


    @Test
    @DisplayName("Criacao de relatorio")
    void shouldReturnNonEmptyByteArrayWhenServiceOrderExecutionsAreFound() {
        ServiceOrderExecution soe = mock(ServiceOrderExecution.class);
        UUID serviceOrderId = UUID.randomUUID();
        LocalDateTime executionStart = LocalDateTime.of(2023, 10, 15, 9, 0);
        LocalDateTime executionEnd = LocalDateTime.of(2023, 10, 15, 17, 0);

        when(soe.getServiceOrderId()).thenReturn(serviceOrderId);
        when(soe.getStartDate()).thenReturn(executionStart);
        when(soe.getEndDate()).thenReturn(executionEnd);

        List<ServiceOrderExecution> mockList = List.of(soe);

        when(serviceOrderExecutionRepository.findByStartDateBetweenOrderByStartDateAsc(startDateTime, endDateTime))
                .thenReturn(mockList);

        byte[] result = generateServiceOrderExecutionReportService.handle(command);

        assertNotNull(result, "O array de bytes não deve ser nulo.");
        assertTrue(result.length > 0, "O array de bytes deve conter dados (PDF gerado).");

        verify(serviceOrderExecutionRepository, times(1))
                .findByStartDateBetweenOrderByStartDateAsc(startDateTime, endDateTime);
    }

    @Test
    @DisplayName("Criacao de relatorio quando nao há execucoes.")
    void shouldReturnNonEmptyByteArrayWhenNoServiceOrderExecutionsAreFound() {
        when(serviceOrderExecutionRepository.findByStartDateBetweenOrderByStartDateAsc(startDateTime, endDateTime))
                .thenReturn(Collections.emptyList());

        byte[] result = generateServiceOrderExecutionReportService.handle(command);

        assertNotNull(result, "O array de bytes não deve ser nulo.");
        assertTrue(result.length > 0, "O array de bytes deve conter dados (PDF gerado).");

        verify(serviceOrderExecutionRepository, times(1))
                .findByStartDateBetweenOrderByStartDateAsc(startDateTime, endDateTime);
    }

}
