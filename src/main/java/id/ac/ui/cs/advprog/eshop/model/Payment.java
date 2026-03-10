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
            String voucherCode = paymentData.get("voucherCode");
            if (voucherCode != null
                    && voucherCode.length() == 16
                    && voucherCode.startsWith("ESHOP")
                    && voucherCode.replaceAll("[^0-9]", "").length() == 8) {
                this.status = "SUCCESS";
            } else {
                this.status = "REJECTED";
            }
        } else if (method.equals("Cash On Delivery")) {
            String address = paymentData.get("address");
            String deliveryFee = paymentData.get("deliveryFee");

            if (address != null && !address.isEmpty()
                    && deliveryFee != null && !deliveryFee.isEmpty()) {
                this.status = "SUCCESS";
            } else {
                this.status = "REJECTED";
            }
        }
    }
}