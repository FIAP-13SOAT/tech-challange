package com.fiapchallenge.garage.presenters.stockmovement;

import com.fiapchallenge.garage.adapters.inbound.controller.stockmovement.dto.StockMovementDTO;
import com.fiapchallenge.garage.domain.stockmovement.StockMovement;
import org.springframework.data.domain.Page;

public class StockMovementPresenter {

    public StockMovementDTO present(StockMovement stockMovement) {
        return new StockMovementDTO(
                stockMovement.getId(),
                stockMovement.getStockId(),
                stockMovement.getMovementType(),
                stockMovement.getQuantity(),
                stockMovement.getPreviousQuantity(),
                stockMovement.getNewQuantity(),
                stockMovement.getReason(),
                stockMovement.getCreatedAt()
        );
    }

    public Page<StockMovementDTO> present(Page<StockMovement> stockMovementPage) {
        return stockMovementPage.map(this::present);
    }
}
