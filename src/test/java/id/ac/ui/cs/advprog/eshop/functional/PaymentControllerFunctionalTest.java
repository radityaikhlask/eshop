package id.ac.ui.cs.advprog.eshop.functional;

import id.ac.ui.cs.advprog.eshop.controller.PaymentController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PaymentControllerFunctionalTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new PaymentController())
                .build();
    }

    @Test
    void testGetPaymentDetailFormPage() throws Exception {
        mockMvc.perform(get("/payment/detail"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetPaymentDetailByIdPage() throws Exception {
        mockMvc.perform(get("/payment/detail/123"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetPaymentAdminListPage() throws Exception {
        mockMvc.perform(get("/payment/admin/list"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetPaymentAdminDetailPage() throws Exception {
        mockMvc.perform(get("/payment/admin/detail/123"))
                .andExpect(status().isOk());
    }

    @Test
    void testPostPaymentAdminSetStatusPage() throws Exception {
        mockMvc.perform(post("/payment/admin/set-status/123"))
                .andExpect(status().isOk());
    }
}