package eu.elision.marketplace.web.dtos.order;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Dto to transfer data of an order
 */
@Getter
@Setter
public final class OrderDto implements Serializable {
    private long orderNumber;
    private String customerName;
    private String orderDate;
    private double totalPrice;
    private int numberProducts;

    /**
     * Public constructor to make a new orderDto
     *
     * @param orderNumber    the order number of the original order
     * @param customerName   the full name of the customer who made the order
     * @param orderDate      the date the order was created
     * @param totalPrice     the total price of the order
     * @param numberProducts the number of products in the order
     */
    public OrderDto(long orderNumber, String customerName, String orderDate, double totalPrice, int numberProducts) {
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.numberProducts = numberProducts;
    }
}
