package com.fiapchallenge.garage.adapters.inbound.rest.report;

import com.fiapchallenge.garage.application.report.service.GenerateServiceOrderExecutionReportUseCase;
import com.fiapchallenge.garage.controllers.report.ReportController;
import com.fiapchallenge.garage.presenters.report.ReportPresenter;
import com.fiapchallenge.garage.presenters.report.ReportViewModel;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/reports")
public class ReportResource implements ReportResourceOpenApiSpec {

    private final ReportController reportController;

    public ReportResource(GenerateServiceOrderExecutionReportUseCase reportUseCase) {
        this.reportController = new ReportController(reportUseCase, new ReportPresenter());
    }

    @Override
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<byte[]> getServiceOrderExecutionReport(
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate
    ) {
        ReportViewModel report = reportController.getServiceOrderExecutionReport(startDate, endDate);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(report.contentType()));
        headers.setContentDispositionFormData("attachment", report.fileName());
        return ResponseEntity.ok().headers(headers).body(report.content());
    }
}
