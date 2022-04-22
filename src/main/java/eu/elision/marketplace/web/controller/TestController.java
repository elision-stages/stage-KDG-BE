package eu.elision.marketplace.web.controller;

import eu.elision.marketplace.web.webexceptions.NotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController
{
    @GetMapping("/hello")
    String hello()
    {
        return "hello";
    }

    @GetMapping("/testException")
    void testException(){
        throw new NotFoundException();
    }
}
