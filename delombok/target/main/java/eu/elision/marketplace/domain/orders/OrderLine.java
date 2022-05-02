package eu.elision.marketplace.domain.orders;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.users.Vendor;
import javax.persistence.*;

/**
 * An orderline contains the product and the quantity and the ordernumber of the order it belongs to.
 */
@Entity
public class OrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int orderLineNumber;
    @ManyToOne
    private Vendor vendor;
    private String orderNumber;
    @ManyToOne
    private Product product;
    private int quantity;

    public OrderLine(int orderLineNumber, Vendor vendor, String orderNumber, Product product, int quantity) {
        this.orderLineNumber = orderLineNumber;
        this.vendor = vendor;
        this.orderNumber = orderNumber;
        this.product = product;
        this.quantity = quantity;
    }

    public OrderLine() {
    }

    /**
     * Get the total price of the order line
     *
     * @return the total price of the order line
     */
    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }

    @SuppressWarnings("all")
    public int getOrderLineNumber() {
        return this.orderLineNumber;
    }

    @SuppressWarnings("all")
    public Vendor getVendor() {
        return this.vendor;
    }

    @SuppressWarnings("all")
    public String getOrderNumber() {
        return this.orderNumber;
    }

    @SuppressWarnings("all")
    public Product getProduct() {
        return this.product;
    }

    @SuppressWarnings("all")
    public int getQuantity() {
        return this.quantity;
    }

    @SuppressWarnings("all")
    public void setOrderLineNumber(final int orderLineNumber) {
        this.orderLineNumber = orderLineNumber;
    }

    @SuppressWarnings("all")
    public void setVendor(final Vendor vendor) {
        this.vendor = vendor;
    }

    @SuppressWarnings("all")
    public void setOrderNumber(final String orderNumber) {
        this.orderNumber = orderNumber;
    }

    @SuppressWarnings("all")
    public void setProduct(final Product product) {
        this.product = product;
    }

    @SuppressWarnings("all")
    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }

    @Override
    @SuppressWarnings("all")
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof OrderLine)) return false;
        final OrderLine other = (OrderLine) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getOrderLineNumber() != other.getOrderLineNumber()) return false;
        if (this.getQuantity() != other.getQuantity()) return false;
        final Object this$vendor = this.getVendor();
        final Object other$vendor = other.getVendor();
        if (this$vendor == null ? other$vendor != null : !this$vendor.equals(other$vendor)) return false;
        final Object this$orderNumber = this.getOrderNumber();
        final Object other$orderNumber = other.getOrderNumber();
        if (this$orderNumber == null ? other$orderNumber != null : !this$orderNumber.equals(other$orderNumber)) return false;
        final Object this$product = this.getProduct();
        final Object other$product = other.getProduct();
        if (this$product == null ? other$product != null : !this$product.equals(other$product)) return false;
        return true;
    }

    @SuppressWarnings("all")
    protected boolean canEqual(final Object other) {
        return other instanceof OrderLine;
    }

    @Override
    @SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getOrderLineNumber();
        result = result * PRIME + this.getQuantity();
        final Object $vendor = this.getVendor();
        result = result * PRIME + ($vendor == null ? 43 : $vendor.hashCode());
        final Object $orderNumber = this.getOrderNumber();
        result = result * PRIME + ($orderNumber == null ? 43 : $orderNumber.hashCode());
        final Object $product = this.getProduct();
        result = result * PRIME + ($product == null ? 43 : $product.hashCode());
        return result;
    }

    @Override
    @SuppressWarnings("all")
    public String toString() {
        return "OrderLine(orderLineNumber=" + this.getOrderLineNumber() + ", vendor=" + this.getVendor() + ", orderNumber=" + this.getOrderNumber() + ", product=" + this.getProduct() + ", quantity=" + this.getQuantity() + ")";
    }
}
