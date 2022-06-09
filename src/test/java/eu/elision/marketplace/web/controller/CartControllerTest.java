package eu.elision.marketplace.web.controller;

import eu.elision.marketplace.logic.Controller;
import org.apache.commons.lang3.RandomUtils;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class CartControllerTest
{
    @Autowired
    MockMvc mockMvc;
    @Mock
    Controller controller;

    //@Test
    void testCheckout() throws Exception
    {
        final long checkoutOrderId = RandomUtils.nextLong();
        when(controller.checkoutCart(any())).thenReturn(checkoutOrderId);

        mockMvc.perform(post("/checkout"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(String.valueOf(checkoutOrderId))));
    }

}