package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {

    PaymentRepository paymentRepository;
    Payment payment1;
    Payment payment2;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();

        Order order = new Order(
                "13652556-012a-4c07-b546-54eb1396d79b",
                List.of(new Product()),
                1708560000L,
                "Safira Sudrajat"
        );

        Map<String, String> voucherPaymentData = new HashMap<>();
        voucherPaymentData.put("voucherCode", "ESHOP1234ABC5678");

        Map<String, String> codPaymentData = new HashMap<>();
        codPaymentData.put("address", "Jl. Margonda Raya");
        codPaymentData.put("deliveryFee", "10000");

        payment1 = new Payment(order, "Voucher Code", voucherPaymentData);
        payment2 = new Payment(order, "Cash On Delivery", codPaymentData);
    }

    @Test
    void testSaveCreate() {
        Payment result = paymentRepository.save(payment1);

        Payment findResult = paymentRepository.findById(payment1.getId());

        assertEquals(payment1.getId(), result.getId());
        assertEquals(payment1.getId(), findResult.getId());
        assertEquals(payment1.getMethod(), findResult.getMethod());
        assertEquals(payment1.getStatus(), findResult.getStatus());
    }

    @Test
    void testFindByIdIfIdFound() {
        paymentRepository.save(payment1);
        paymentRepository.save(payment2);

        Payment findResult = paymentRepository.findById(payment2.getId());

        assertEquals(payment2.getId(), findResult.getId());
        assertEquals(payment2.getMethod(), findResult.getMethod());
        assertEquals(payment2.getStatus(), findResult.getStatus());
    }

    @Test
    void testFindByIdIfIdNotFound() {
        paymentRepository.save(payment1);

        Payment findResult = paymentRepository.findById("zzzz");

        assertNull(findResult);
    }

    @Test
    void testFindAll() {
        paymentRepository.save(payment1);
        paymentRepository.save(payment2);

        List<Payment> paymentList = paymentRepository.findAll();

        assertEquals(2, paymentList.size());
    }
}