package eu.elision.marketplace.domain.orders;

import eu.elision.marketplace.domain.users.Customer;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The order class contains the orderNumber, orderLines and shipping address of an order. When the user is a vendor the orderLines should only be of products from the vendor.
 */
@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long orderNumber;
    @ManyToOne
    private Customer user;
    @OneToMany(cascade = CascadeType.MERGE)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<OrderLine> lines;
    private final LocalDate createdDate;

    /**
     * Public no args constructor. Initialises the orderlines array and created date
     */
    public Order()
    {
        lines = new ArrayList<>();
        createdDate = LocalDate.now();
    }

    /**
     * Get the total price of an order
     *
     * @return the total price of an order
     */
    public double getTotalPrice()
    {
        return lines.stream().mapToDouble(OrderLine::getTotalPrice).sum();
    }

    public int getProductCount()
    {
        return lines.stream().mapToInt(OrderLine::getQuantity).sum();
    }
}
