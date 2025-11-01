package com.fiapchallenge.garage.adapters.inbound.controller.report;

import com.fiapchallenge.garage.application.report.command.GenerateServiceOrderExecutionReportCommand;
import com.fiapchallenge.garage.application.report.service.GenerateServiceOrderExecutionReportUseCase;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/reports")
public class ReportController implements ReportControllerOpenApiSpec {

    GenerateServiceOrderExecutionReportUseCase reportUseCase;

    public ReportController(GenerateServiceOrderExecutionReportUseCase reportUseCase) {
        this.reportUseCase = reportUseCase;
    }

    @GetMapping
    public ResponseEntity<byte[]> getServiceOrderExecutionReport(
            @RequestParam(required = true)
            @DateTimeFormat(pattern = "dd-MM-yyyy")
            LocalDate startDate,
            @RequestParam(required = true)
            @DateTimeFormat(pattern = "dd-MM-yyyy")
            LocalDate endDate){

        byte[] pdfBytes = reportUseCase.handle(new GenerateServiceOrderExecutionReportCommand(startDate, endDate));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "tempo_execucao_os.pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

}
