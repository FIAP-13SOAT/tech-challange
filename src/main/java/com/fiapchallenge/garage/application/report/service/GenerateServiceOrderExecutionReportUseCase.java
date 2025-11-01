package com.fiapchallenge.garage.application.report.service;


import com.fiapchallenge.garage.application.report.command.GenerateServiceOrderExecutionReportCommand;

public interface GenerateServiceOrderExecutionReportUseCase {

    public byte [] handle(GenerateServiceOrderExecutionReportCommand command);
}
