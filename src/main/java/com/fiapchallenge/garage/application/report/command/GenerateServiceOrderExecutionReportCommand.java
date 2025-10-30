package com.fiapchallenge.garage.application.report.command;

import java.time.LocalDate;

public record GenerateServiceOrderExecutionReportCommand(
        LocalDate startDate,
        LocalDate endDate
) {

}
