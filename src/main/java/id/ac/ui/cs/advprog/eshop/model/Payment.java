package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.UUID;

@Getter
public class Payment {
    private String id;
    private Order order;
    private String method;

    @Setter
    private String status;

    private Map<String, String> paymentData;

    public Payment(Order order, String method, Map<String, String> paymentData) {
        this.id = UUID.randomUUID().toString();
        this.order = order;
        this.method = method;
        this.paymentData = paymentData;

        if (method.equals("Voucher Code")) {
            this.status = isValidVoucher(paymentData) ? "SUCCESS" : "REJECTED";
        } else if (method.equals("Cash On Delivery")) {
            this.status = isValidCod(paymentData) ? "SUCCESS" : "REJECTED";
        }
    }

    private boolean isValidVoucher(Map<String, String> paymentData) {
        String voucherCode = paymentData.get("voucherCode");
        return voucherCode != null
                && voucherCode.length() == 16
                && voucherCode.startsWith("ESHOP")
                && voucherCode.replaceAll("[^0-9]", "").length() == 8;
    }

    private boolean isValidCod(Map<String, String> paymentData) {
        String address = paymentData.get("address");
        String deliveryFee = paymentData.get("deliveryFee");

        return address != null && !address.isEmpty()
                && deliveryFee != null && !deliveryFee.isEmpty();
    }
}