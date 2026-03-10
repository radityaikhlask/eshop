package id.ac.ui.cs.advprog.eshop.functional;

import id.ac.ui.cs.advprog.eshop.controller.OrderController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OrderPayFunctionalTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new OrderController())
                .build();
    }

    @Test
    void testGetOrderPayPage() throws Exception {
        mockMvc.perform(get("/order/pay/123"))
                .andExpect(status().isOk());
    }

    @Test
    void testPostOrderPayPage() throws Exception {
        mockMvc.perform(post("/order/pay/123"))
                .andExpect(status().isOk());
    }
}