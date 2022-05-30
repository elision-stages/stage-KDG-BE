package eu.elision.marketplace.web.api.vat;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class VATClient {

    private String request(URL url, String data) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/xml");
        conn.setRequestProperty("Content-Length", String.valueOf(data.length()));

        OutputStream os = conn.getOutputStream();
        os.write(data.getBytes());
        StringBuilder sb = new StringBuilder();

        InputStream inputStream = conn.getInputStream();
        for (int ch; (ch = inputStream.read()) != -1; ) {
            sb.append((char) ch);
        }
        return sb.toString();
    }

    public Business checkVatService(String country, String number) {
        // Probably faster than parsing the whole XML result and having extra dependencies
        try {
            String requestXml =
                    "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tns1=\"urn:ec.europa.eu:taxud:vies:services:checkVat:types\" xmlns:impl=\"urn:ec.europa.eu:taxud:vies:services:checkVat\"><soap:Header></soap:Header><soap:Body><tns1:checkVat xmlns:tns1=\"urn:ec.europa.eu:taxud:vies:services:checkVat:types\" xmlns=\"urn:ec.europa.eu:taxud:vies:services:checkVat:types\"><tns1:countryCode>{{country}}</tns1:countryCode><tns1:vatNumber>{{vat}}</tns1:vatNumber></tns1:checkVat></soap:Body></soap:Envelope>"
                            .replace("{{country}}", country)
                            .replace("{{vat}}", number);

            String getCheckVatServiceURL = "https://ec.europa.eu/taxation_customs/vies/services/checkVatService";
            String result = this.request(new URL(getCheckVatServiceURL), requestXml);

            if(result.contains("<faultstring>INVALID_INPUT</faultstring>")) return null;
            if (!result.contains("<valid>true</valid>")) return null;
            if (result.contains("<soap:Fault>")) return new Business(); // Returns a result when the API is down, so it doesn't prevent registration

            Business business = new Business();

            business.setCountryCode(StringUtils.substringBetween(result, "<countryCode>", "</countryCode>"));
            business.setVatNumber(StringUtils.substringBetween(result, "<vatNumber>", "</vatNumber>"));
            business.setName(StringUtils.substringBetween(result, "<name>", "</name>"));
            business.setAddress(StringUtils.substringBetween(result, "<address>", "</address>"));

            return business;
        } catch (IOException e) {
            return new Business(); // Returns a result when the API is down, so it doesn't prevent registration
        }


    }

    public Business checkVatService(String vatNumber) {
        if (vatNumber.replaceAll("[^a-zA-Z\\d]", "").length() < 10) return null;
        return checkVatService(vatNumber.substring(0, 2), vatNumber.substring(2));
    }
}

