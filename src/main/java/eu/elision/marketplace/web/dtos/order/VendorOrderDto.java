package eu.elision.marketplace.web.dtos.order;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public final class VendorOrderDto implements Serializable {
    private long orderNumber;
    private String customerName;
    private String orderDate;
    private double totalPrice;
    private int numberProducts;

    public VendorOrderDto(long orderNumber, String customerName, String orderDate, double totalPrice, int numberProducts) {
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.numberProducts = numberProducts;
    }

    public VendorOrderDto(String orderNumber) {
        this.orderNumber = Long.parseLong(orderNumber);
    }
}
