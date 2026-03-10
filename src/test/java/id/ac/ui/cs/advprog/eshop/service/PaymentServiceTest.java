package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    private Order order;
    private Map<String, String> voucherPaymentData;
    private Map<String, String> codPaymentData;
    private Payment voucherPayment;
    private Payment codPayment;

    @BeforeEach
    void setUp() {
        order = new Order(
                "13652556-012a-4c07-b546-54eb1396d79b",
                List.of(new Product()),
                1708560000L,
                "Safira Sudrajat"
        );

        voucherPaymentData = new HashMap<>();
        voucherPaymentData.put("voucherCode", "ESHOP1234ABC5678");

        codPaymentData = new HashMap<>();
        codPaymentData.put("address", "Jl. Margonda Raya");
        codPaymentData.put("deliveryFee", "10000");

        voucherPayment = new Payment(order, "Voucher Code", voucherPaymentData);
        codPayment = new Payment(order, "Cash On Delivery", codPaymentData);
    }

    @Test
    void testAddPaymentVoucher() {
        doReturn(voucherPayment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order, "Voucher Code", voucherPaymentData);

        assertNotNull(result);
        assertEquals("Voucher Code", result.getMethod());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testAddPaymentCashOnDelivery() {
        doReturn(codPayment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order, "Cash On Delivery", codPaymentData);

        assertNotNull(result);
        assertEquals("Cash On Delivery", result.getMethod());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testSetStatusSuccess() {
        Payment result = paymentService.setStatus(voucherPayment, "SUCCESS");

        assertEquals("SUCCESS", result.getStatus());
        assertEquals("SUCCESS", order.getStatus());
    }

    @Test
    void testSetStatusRejected() {
        Payment result = paymentService.setStatus(voucherPayment, "REJECTED");

        assertEquals("REJECTED", result.getStatus());
        assertEquals("FAILED", order.getStatus());
    }

    @Test
    void testSetStatusUnknownDoesNotChangeOrderStatus() {
        assertEquals("WAITING_PAYMENT", order.getStatus());

        Payment result = paymentService.setStatus(voucherPayment, "PENDING_REVIEW");

        assertEquals("PENDING_REVIEW", result.getStatus());
        assertEquals("WAITING_PAYMENT", order.getStatus());
    }

    @Test
    void testGetPaymentIfFound() {
        doReturn(voucherPayment).when(paymentRepository).findById(voucherPayment.getId());

        Payment result = paymentService.getPayment(voucherPayment.getId());

        assertNotNull(result);
        assertEquals(voucherPayment.getId(), result.getId());
    }

    @Test
    void testGetPaymentIfNotFound() {
        doReturn(null).when(paymentRepository).findById("zzzz");

        Payment result = paymentService.getPayment("zzzz");

        assertNull(result);
    }

    @Test
    void testGetAllPayments() {
        List<Payment> payments = List.of(voucherPayment, codPayment);
        doReturn(payments).when(paymentRepository).findAll();

        List<Payment> results = paymentService.getAllPayments();

        assertEquals(2, results.size());
        verify(paymentRepository, times(1)).findAll();
    }
}