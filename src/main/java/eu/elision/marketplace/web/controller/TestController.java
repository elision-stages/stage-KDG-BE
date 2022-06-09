package eu.elision.marketplace.web.controller;

import eu.elision.marketplace.exceptions.NotFoundException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Controller to handle test calls
 */
// TODO: 9/06/22 remove this from final codebase 
@RestController
public class TestController
{
    /**
     * Get mapping to test the rest api
     *
     * @return returns the string "hello"
     */
    @GetMapping("/hello")
    String hello()
    {
        return "hello v1.0.2";
    }

    /**
     * Get mapping to test the rest api
     *
     * @return returns the string "hello"
     */
    @Secured("ROLE_ADMIN")
    @GetMapping("/hellosecured")
    String helloSecured()
    {
        return "hello, but safe";
    }

    /**
     * Test the case when an exception is thrown. Should return "not found"
     */
    @GetMapping("/testException")
    void testException() {
        throw new NotFoundException();
    }

    @GetMapping("testExternal")
    String testExternalConnection() throws IOException {
        return request(new URL("https://www.google.com"), "GET");
    }

    @GetMapping("testAlgolia")
    String testExternalAlgoliaConnection() throws IOException {
        return request(new URL("https://EL070LM1BO.algolia.net/"), "POST");
    }

    private String request(URL url, String method) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod(method);

        StringBuilder sb = new StringBuilder();

        InputStream inputStream = conn.getInputStream();
        for (int ch; (ch = inputStream.read()) != -1; ) {
            sb.append((char) ch);
        }
        return sb.toString();
    }
}
