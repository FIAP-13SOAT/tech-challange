package com.fiapchallenge.garage.adapters.inbound.controller.budget;

import com.fiapchallenge.garage.application.budget.GenerateBudgetUseCase;
import com.fiapchallenge.garage.application.budget.ApproveBudgetUseCase;
import com.fiapchallenge.garage.application.budget.RejectBudgetUseCase;
import com.fiapchallenge.garage.domain.budget.Budget;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/budgets")
@Tag(name = "Budget", description = "Budget management API")
public class BudgetController {
    private final GenerateBudgetUseCase generateBudgetUseCase;
    private final ApproveBudgetUseCase approveBudgetUseCase;
    private final RejectBudgetUseCase rejectBudgetUseCase;

    public BudgetController(GenerateBudgetUseCase generateBudgetUseCase, ApproveBudgetUseCase approveBudgetUseCase, RejectBudgetUseCase rejectBudgetUseCase) {
        this.generateBudgetUseCase = generateBudgetUseCase;
        this.approveBudgetUseCase = approveBudgetUseCase;
        this.rejectBudgetUseCase = rejectBudgetUseCase;
    }

    @Operation(summary = "Gerar orçamento", description = "Gera orçamento detalhado para uma ordem de serviço")
    @GetMapping("/service-order/{serviceOrderId}")
    public ResponseEntity<Budget> generateBudget(@PathVariable UUID serviceOrderId) {
        Budget budget = generateBudgetUseCase.handle(serviceOrderId);
        return ResponseEntity.ok(budget);
    }

    @Operation(summary = "Aprovar orçamento", description = "Aprova o orçamento de uma ordem de serviço")
    @PostMapping("/service-order/{serviceOrderId}/approve")
    public ResponseEntity<Budget> approveBudget(@PathVariable UUID serviceOrderId) {
        Budget budget = approveBudgetUseCase.handle(serviceOrderId);
        return ResponseEntity.ok(budget);
    }

    @Operation(summary = "Rejeitar orçamento", description = "Rejeita o orçamento de uma ordem de serviço")
    @PostMapping("/service-order/{serviceOrderId}/reject")
    public ResponseEntity<Budget> rejectBudget(@PathVariable UUID serviceOrderId) {
        Budget budget = rejectBudgetUseCase.handle(serviceOrderId);
        return ResponseEntity.ok(budget);
    }
}