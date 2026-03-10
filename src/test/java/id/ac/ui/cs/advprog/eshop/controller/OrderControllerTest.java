package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    private OrderController orderController;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderController = new OrderController();
        orderService = mock(OrderService.class);
        ReflectionTestUtils.setField(orderController, "orderService", orderService);
    }

    @Test
    void testCreateOrderPage() {
        Model model = new ConcurrentModel();
        String view = orderController.createOrderPage(model);
        assertEquals("createOrder", view);
    }

    @Test
    void testHistoryFormPage() {
        String view = orderController.historyFormPage();
        assertEquals("orderHistoryForm", view);
    }

    @Test
    void testOrderPayPage() {
        Model model = new ConcurrentModel();
        String view = orderController.orderPayPage("123", model);
        assertEquals("orderPay", view);
    }

    @Test
    void testOrderPaySubmit() {
        Model model = new ConcurrentModel();
        String view = orderController.orderPaySubmit("123", model);
        assertEquals("paymentDetail", view);
    }

    @Test
    void testCreateOrderPost() {
        Order inputOrder = new Order();

        String view = orderController.createOrderPost(inputOrder, "Apple", 3);

        assertEquals("redirect:/order/history", view);
        verify(orderService, times(1)).createOrder(any(Order.class));
    }

    @Test
    void testOrderHistoryListPage() {
        List<Order> orders = List.of(new Order());
        when(orderService.findAllByAuthor("Alice")).thenReturn(orders);

        Model model = new ConcurrentModel();
        String view = orderController.orderHistoryListPage("Alice", model);

        assertEquals("orderHistoryList", view);
        assertTrue(model.containsAttribute("orders"));
        assertSame(orders, model.getAttribute("orders"));
        verify(orderService).findAllByAuthor("Alice");
    }
}