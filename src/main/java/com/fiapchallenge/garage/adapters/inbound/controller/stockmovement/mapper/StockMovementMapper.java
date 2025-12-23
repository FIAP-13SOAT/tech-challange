package com.fiapchallenge.garage.adapters.inbound.controller.stockmovement.mapper;

import com.fiapchallenge.garage.adapters.inbound.controller.stockmovement.dto.StockMovementDTO;
import com.fiapchallenge.garage.domain.stockmovement.StockMovement;
import org.springframework.data.domain.Page;

public class StockMovementMapper {

    public static StockMovementDTO toResponseDTO(StockMovement stockMovement) {
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

    public static Page<StockMovementDTO> toResponseDTOPage(Page<StockMovement> stockMovementPage) {
        return stockMovementPage.map(StockMovementMapper::toResponseDTO);
    }
}