package com.fiapchallenge.garage.presenters.report;

public class ReportPresenter {

    public ReportViewModel present(byte[] pdfBytes) {
        return new ReportViewModel(pdfBytes, "tempo_execucao_os.pdf", "application/pdf");
    }
}
