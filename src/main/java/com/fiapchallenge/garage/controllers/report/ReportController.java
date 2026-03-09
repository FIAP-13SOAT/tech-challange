package com.fiapchallenge.garage.controllers.report;

import com.fiapchallenge.garage.application.report.command.GenerateServiceOrderExecutionReportCommand;
import com.fiapchallenge.garage.application.report.service.GenerateServiceOrderExecutionReportUseCase;
import com.fiapchallenge.garage.presenters.report.ReportPresenter;
import com.fiapchallenge.garage.presenters.report.ReportViewModel;

import java.time.LocalDate;

public class ReportController {

    private final GenerateServiceOrderExecutionReportUseCase reportUseCase;
    private final ReportPresenter reportPresenter;

    public ReportController(
            GenerateServiceOrderExecutionReportUseCase reportUseCase,
            ReportPresenter reportPresenter
    ) {
        this.reportUseCase = reportUseCase;
        this.reportPresenter = reportPresenter;
    }

    public ReportViewModel getServiceOrderExecutionReport(LocalDate startDate, LocalDate endDate) {
        byte[] pdfBytes = reportUseCase.handle(new GenerateServiceOrderExecutionReportCommand(startDate, endDate));
        return reportPresenter.present(pdfBytes);
    }
}
