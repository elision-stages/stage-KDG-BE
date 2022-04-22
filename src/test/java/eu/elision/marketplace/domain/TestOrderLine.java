package eu.elision.marketplace.domain;

import eu.elision.marketplace.domain.orders.OrderLine;
import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.users.Vendor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class TestOrderLine
{
    @Test
    void orderLineTotalPrice()
    {
        var ol = new OrderLine(1, new Vendor(), "1", new Product(2, new Vendor(), "product", new ArrayList<>(), new ArrayList<>()), 2);
        assertThat(ol.getTotalPrice()).isEqualTo(4.0);
    }
}
