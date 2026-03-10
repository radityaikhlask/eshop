package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/detail")
    public String paymentDetailFormPage() {
        return "paymentDetailForm";
    }

    @GetMapping("/detail/{paymentId}")
    public String paymentDetailPage(@PathVariable String paymentId, Model model) {
        if (paymentService != null) {
            model.addAttribute("payment", paymentService.getPayment(paymentId));
        }
        return "paymentDetail";
    }

    @GetMapping("/admin/list")
    public String paymentAdminListPage(Model model) {
        List<Payment> payments = new ArrayList<>();
        if (paymentService != null) {
            payments = paymentService.getAllPayments();
        }
        model.addAttribute("payments", payments);
        return "paymentAdminList";
    }

    @GetMapping("/admin/detail/{paymentId}")
    public String paymentAdminDetailPage(@PathVariable String paymentId, Model model) {
        if (paymentService != null) {
            model.addAttribute("payment", paymentService.getPayment(paymentId));
        }
        return "paymentAdminDetail";
    }

    @PostMapping("/admin/set-status/{paymentId}")
    public String paymentAdminSetStatusPage(@PathVariable String paymentId) {
        return "paymentAdminSetStatus";
    }
}