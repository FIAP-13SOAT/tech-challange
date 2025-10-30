package com.fiapchallenge.garage.application.report.service.command;

import java.time.LocalDate;

public record GenerateServiceOrderExecutionReportCommand(
        LocalDate startDate,
        LocalDate endDate
) {

}
