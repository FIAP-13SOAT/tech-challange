package com.fiapchallenge.garage.integration.serviceorder;

import com.fiapchallenge.garage.application.customer.create.CreateCustomerService;
import com.fiapchallenge.garage.application.serviceorder.create.CreateServiceOrderService;
import com.fiapchallenge.garage.application.servicetype.create.CreateServiceTypeService;
import com.fiapchallenge.garage.application.vehicle.create.CreateVehicleService;
import com.fiapchallenge.garage.domain.customer.Customer;
import com.fiapchallenge.garage.domain.quote.Quote;
import com.fiapchallenge.garage.domain.quote.QuoteRepository;
import com.fiapchallenge.garage.domain.quote.QuoteStatus;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderStatus;
import com.fiapchallenge.garage.domain.user.UserRole;
import com.fiapchallenge.garage.domain.vehicle.Vehicle;
import com.fiapchallenge.garage.integration.BaseIntegrationTest;
import com.fiapchallenge.garage.integration.fixtures.CustomerFixture;
import com.fiapchallenge.garage.integration.fixtures.ServiceOrderFixture;
import com.fiapchallenge.garage.integration.fixtures.VehicleFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class FinishServiceOrderDiagnosisIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CreateCustomerService createCustomerService;

    @Autowired
    private CreateVehicleService createVehicleService;

    @Autowired
    private CreateServiceOrderService createServiceOrderService;

    @Autowired
    private CreateServiceTypeService createServiceTypeService;

    @Autowired
    private ServiceOrderRepository serviceOrderRepository;

    @Autowired
    private QuoteRepository quoteRepository;

    @Test
    @DisplayName("Deve criar um orçamento quando o diagnóstico da ordem de serviço for finalizado")
    void shouldCreateQuoteWhenServiceOrderDiagnosisIsFinished() throws Exception {
        Customer customer = CustomerFixture.createCustomer(createCustomerService,"João Jorge","jj@gmail.com","11999999999","84327109541");
        Vehicle vehicle = VehicleFixture.createVehicle(customer.getId(), createVehicleService);
        ServiceOrder serviceOrder = ServiceOrderFixture.createServiceOrder(vehicle.getId(), customer.getId(), createServiceOrderService, createServiceTypeService, serviceOrderRepository);

        //  aprovação do orçamento
        serviceOrder.startDiagnostic();
        serviceOrderRepository.save(serviceOrder);

        mockMvc.perform(post("/service-orders/" + serviceOrder.getId() + "/finish-diagnosis")
                        .header("Authorization", getAuthTokenForRole(UserRole.MECHANIC)))
                .andExpect(status().isOk());

        ServiceOrder updatedServiceOrder = serviceOrderRepository.findByIdOrThrow(serviceOrder.getId());
        Quote quote = quoteRepository.findByServiceOrderIdOrThrow(serviceOrder.getId());

        assertEquals(ServiceOrderStatus.AWAITING_APPROVAL, updatedServiceOrder.getStatus());
        assertEquals( QuoteStatus.PENDING,quote.getStatus());
    }

}