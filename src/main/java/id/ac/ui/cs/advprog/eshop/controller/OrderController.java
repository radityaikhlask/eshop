package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/create")
    public String createOrderPage(Model model) {
        Order order = new Order();
        model.addAttribute("order", order);
        return "createOrder";
    }

    @PostMapping("/create")
    public String createOrderPost(@ModelAttribute Order order,
                                  @RequestParam("productName") String productName,
                                  @RequestParam("productQuantity") int productQuantity) {
        List<Product> products = new ArrayList<>();

        Product product = new Product();
        product.setProductName(productName);
        product.setProductQuantity(productQuantity);
        products.add(product);

        Order newOrder = new Order(
                order.getId(),
                products,
                System.currentTimeMillis(),
                order.getAuthor()
        );

        orderService.createOrder(newOrder);
        return "redirect:/order/history";
    }

    @GetMapping("/history")
    public String historyFormPage() {
        return "orderHistoryForm";
    }

    @PostMapping("/history")
    public String orderHistoryListPage(@RequestParam("author") String author, Model model) {
        List<Order> orders = orderService.findAllByAuthor(author);
        model.addAttribute("orders", orders);
        return "orderHistoryList";
    }
    @GetMapping("/pay/{orderId}")
    public String orderPayPage(@PathVariable String orderId, Model model) {
        model.addAttribute("orderId", orderId);
        return "orderPay";
    }

    @PostMapping("/pay/{orderId}")
    public String orderPaySubmit(@PathVariable String orderId, Model model) {
        model.addAttribute("orderId", orderId);
        return "paymentDetail";
    }
}