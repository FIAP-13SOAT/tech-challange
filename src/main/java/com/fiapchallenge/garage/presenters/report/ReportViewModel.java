package com.fiapchallenge.garage.presenters.report;

public record ReportViewModel(
        byte[] content,
        String fileName,
        String contentType
) {
}
