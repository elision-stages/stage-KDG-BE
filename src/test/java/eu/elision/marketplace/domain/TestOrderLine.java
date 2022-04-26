package eu.elision.marketplace.domain;

import eu.elision.marketplace.domain.orders.OrderLine;
import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.users.Vendor;
import eu.elision.marketplace.services.helpers.HelperMethods;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class TestOrderLine
{
    @Test
    void orderLineTotalPrice()
    {
        final double price = HelperMethods.randomDouble();
        final int quantity = HelperMethods.randomInt();

        var ol = new OrderLine(HelperMethods.randomInt(), new Vendor(), String.valueOf(HelperMethods.randomInt()), new Product(price, new Vendor(), HelperMethods.randomString(5), new ArrayList<>(), new ArrayList<>()), quantity);
        assertThat(ol.getTotalPrice()).isEqualTo(price * quantity);
    }
}
