package eu.elision.marketplace.domain;

import eu.elision.marketplace.domain.orders.OrderLine;
import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.domain.users.Vendor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class TestOrderLine
{
    @Test
    void orderLineTotalPrice()
    {
        final double price = RandomUtils.nextDouble(1, 100);
        final int quantity = RandomUtils.nextInt();

        var ol = new OrderLine(RandomUtils.nextInt(), String.valueOf(RandomUtils.nextInt()), new Product(RandomUtils.nextLong(1, 100), price, RandomStringUtils.random(4), new Category(), new Vendor(), RandomStringUtils.random(4), new ArrayList<>(), new ArrayList<>()), quantity);
        assertThat(ol.getTotalPrice()).isEqualTo(price * quantity);
    }
}
