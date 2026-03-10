package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentControllerTest {

    private PaymentController paymentController;
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        paymentController = new PaymentController();
        paymentService = mock(PaymentService.class);
    }

    @Test
    void testPaymentDetailFormPage() {
        String view = paymentController.paymentDetailFormPage();
        assertEquals("paymentDetailForm", view);
    }

    @Test
    void testPaymentDetailPage() {
        Model model = new ConcurrentModel();
        String view = paymentController.paymentDetailPage("123", model);
        assertEquals("paymentDetail", view);
    }

    @Test
    void testPaymentAdminListPage() {
        Model model = new ConcurrentModel();
        String view = paymentController.paymentAdminListPage(model);
        assertEquals("paymentAdminList", view);
    }

    @Test
    void testPaymentAdminDetailPage() {
        Model model = new ConcurrentModel();
        String view = paymentController.paymentAdminDetailPage("123", model);
        assertEquals("paymentAdminDetail", view);
    }

    @Test
    void testPaymentAdminSetStatusPage() {
        String view = paymentController.paymentAdminSetStatusPage("123");
        assertEquals("paymentAdminSetStatus", view);
    }

    @Test
    void testPaymentDetailPageWithServiceAddsPaymentToModel() {
        Payment payment = mock(Payment.class);
        when(paymentService.getPayment("123")).thenReturn(payment);
        ReflectionTestUtils.setField(paymentController, "paymentService", paymentService);

        Model model = new ConcurrentModel();
        String view = paymentController.paymentDetailPage("123", model);

        assertEquals("paymentDetail", view);
        assertTrue(model.containsAttribute("payment"));
        assertSame(payment, model.getAttribute("payment"));
        verify(paymentService).getPayment("123");
    }

    @Test
    void testPaymentAdminListPageWithServiceAddsPaymentsToModel() {
        List<Payment> payments = List.of(mock(Payment.class));
        when(paymentService.getAllPayments()).thenReturn(payments);
        ReflectionTestUtils.setField(paymentController, "paymentService", paymentService);

        Model model = new ConcurrentModel();
        String view = paymentController.paymentAdminListPage(model);

        assertEquals("paymentAdminList", view);
        assertTrue(model.containsAttribute("payments"));
        assertSame(payments, model.getAttribute("payments"));
        verify(paymentService).getAllPayments();
    }

    @Test
    void testPaymentAdminDetailPageWithServiceAddsPaymentToModel() {
        Payment payment = mock(Payment.class);
        when(paymentService.getPayment("123")).thenReturn(payment);
        ReflectionTestUtils.setField(paymentController, "paymentService", paymentService);

        Model model = new ConcurrentModel();
        String view = paymentController.paymentAdminDetailPage("123", model);

        assertEquals("paymentAdminDetail", view);
        assertTrue(model.containsAttribute("payment"));
        assertSame(payment, model.getAttribute("payment"));
        verify(paymentService).getPayment("123");
    }
}