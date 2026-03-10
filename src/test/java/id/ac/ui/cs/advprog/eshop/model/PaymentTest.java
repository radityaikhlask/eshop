package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    private Order order;
    private Map<String, String> voucherPaymentData;
    private Map<String, String> codPaymentData;
    private Map<String, String> invalidVoucherPaymentData;
    private Map<String, String> invalidCodPaymentData;

    @BeforeEach
    void setUp() {
        order = new Order(
                "13652556-012a-4c07-b546-54eb1396d79b",
                java.util.List.of(new Product()),
                1708560000L,
                "Safira Sudrajat"
        );

        voucherPaymentData = new HashMap<>();
        voucherPaymentData.put("voucherCode", "ESHOP1234ABC5678");

        codPaymentData = new HashMap<>();
        codPaymentData.put("address", "Jl. Margonda Raya");
        codPaymentData.put("deliveryFee", "10000");

        invalidVoucherPaymentData = new HashMap<>();
        invalidVoucherPaymentData.put("voucherCode", "INVALIDCODE");

        invalidCodPaymentData = new HashMap<>();
        invalidCodPaymentData.put("address", "");
        invalidCodPaymentData.put("deliveryFee", "10000");
    }

    @Test
    void testCreateVoucherPaymentSuccess() {
        Payment payment = new Payment(order, "Voucher Code", voucherPaymentData);

        assertNotNull(payment.getId());
        assertEquals("Voucher Code", payment.getMethod());
        assertEquals("SUCCESS", payment.getStatus());
        assertEquals(voucherPaymentData, payment.getPaymentData());
    }

    @Test
    void testCreateVoucherPaymentRejected() {
        Payment payment = new Payment(order, "Voucher Code", invalidVoucherPaymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreateCashOnDeliveryPaymentSuccess() {
        Payment payment = new Payment(order, "Cash On Delivery", codPaymentData);

        assertNotNull(payment.getId());
        assertEquals("Cash On Delivery", payment.getMethod());
        assertEquals("SUCCESS", payment.getStatus());
        assertEquals(codPaymentData, payment.getPaymentData());
    }

    @Test
    void testCreateCashOnDeliveryPaymentRejected() {
        Payment payment = new Payment(order, "Cash On Delivery", invalidCodPaymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testSetStatus() {
        Payment payment = new Payment(order, "Voucher Code", voucherPaymentData);

        payment.setStatus("REJECTED");

        assertEquals("REJECTED", payment.getStatus());
    }
}