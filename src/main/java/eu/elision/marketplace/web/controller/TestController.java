package eu.elision.marketplace.web.controller;

import eu.elision.marketplace.web.webexceptions.NotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController
{
    /**
     * Get mapping to test the rest api
     * @return returns the string "hello"
     */
    @GetMapping("/hello")
    String hello()
    {
        return "hello";
    }

    /**
     * Test the case when an exception is thrown. Should return "not found"
     */
    @GetMapping("/testException")
    void testException(){
        throw new NotFoundException();
    }
}
