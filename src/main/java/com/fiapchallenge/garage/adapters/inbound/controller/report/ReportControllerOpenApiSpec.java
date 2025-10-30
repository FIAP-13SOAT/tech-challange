package com.fiapchallenge.garage.adapters.inbound.controller.report;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

@Tag(name = "Report", description = "Reports management API")
public interface ReportControllerOpenApiSpec {

    @Operation(
            summary = "Service Order Execution Report (PDF)",
            description = "Generates a PDF report of service orders executions within a specified date range."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Report generated successfully.",
                    content = @Content(
                            mediaType = "application/pdf",
                            schema = @Schema(type = "string", format = "binary", description = "PDF report file")
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid date range provided.",
                    content = @Content(mediaType = "text/plain")
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error while generating the report.",
                    content = @Content(mediaType = "text/plain")
            )
    })
    @GetMapping()
    ResponseEntity<byte[]> getServiceOrderExecutionReport(
            @Parameter(
                    name = "startDate",
                    description = "Start date of the period (format dd-MM-yyyy).",
                    schema = @Schema(type = "string", format = "date", example = "01-10-2025")
            )
            LocalDate startDate,

            @Parameter(
                    name = "endDate",
                    description = "End date of the period (format dd-MM-yyyy).",
                    schema = @Schema(type = "string", format = "date", example = "31-10-2025")
            )
            LocalDate endDate
    );
}