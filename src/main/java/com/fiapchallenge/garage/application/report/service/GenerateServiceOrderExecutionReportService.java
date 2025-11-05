package com.fiapchallenge.garage.application.report.service;

import com.fiapchallenge.garage.application.report.command.GenerateServiceOrderExecutionReportCommand;
import com.fiapchallenge.garage.domain.serviceorderexecution.ServiceOrderExecution;
import com.fiapchallenge.garage.domain.serviceorderexecution.ServiceOrderExecutionRepository;
import com.fiapchallenge.garage.shared.exception.ReportErrorException;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class GenerateServiceOrderExecutionReportService implements GenerateServiceOrderExecutionReportUseCase {

    private final ServiceOrderExecutionRepository serviceOrderExecutionRepository;

    public GenerateServiceOrderExecutionReportService(ServiceOrderExecutionRepository serviceOrderExecutionRepository) {
        this.serviceOrderExecutionRepository = serviceOrderExecutionRepository;
    }

    @Override
    public byte[] handle(GenerateServiceOrderExecutionReportCommand command) {

        List<ServiceOrderExecution> serviceOrdesExecutions = serviceOrderExecutionRepository.findByStartDateBetweenOrderByStartDateAsc(command.startDate().atStartOfDay(), command.endDate().atTime(23, 59, 59));

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);

            try (Document document = new Document(pdf)) {
                Paragraph title = new Paragraph("Tempo de Execução de OS")
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFontSize(20)
                        .setBold()
                        .setMarginBottom(15);
                document.add(title);
                document.add(createTable(serviceOrdesExecutions));
            }

            return baos.toByteArray();

        } catch (IOException e) {
            throw new ReportErrorException("Report generation failed");
        }
    }

    private Table createTable(List<ServiceOrderExecution> serviceOrdesExecutions) {
        float[] cols = {14, 7, 7, 5};
        Table table = new Table(UnitValue.createPercentArray(cols));
        table.setWidth(UnitValue.createPercentValue(100));

        table.addHeaderCell("OS");
        table.addHeaderCell("Início");
        table.addHeaderCell("Fim");
        table.addHeaderCell("Tempo (Horas:Minutos)");

        for (ServiceOrderExecution soe : serviceOrdesExecutions) {
            table.addCell(soe.getServiceOrderId().toString());
            table.addCell(soe.getStartDate().toString());
            if (soe.getEndDate() == null) {
                table.addCell("-");
                Duration duration = Duration.between(soe.getStartDate(), LocalDateTime.now());
                table.addCell(String.format("%d:%02d", duration.toHours(), duration.toMinutesPart()));
            } else {
                table.addCell(soe.getEndDate().toString());
                Duration duration = Duration.between(soe.getStartDate(), soe.getEndDate());
                table.addCell(String.format("%d:%02d", duration.toHours(), duration.toMinutesPart()));
            }
        }
        return table;
    }

}
