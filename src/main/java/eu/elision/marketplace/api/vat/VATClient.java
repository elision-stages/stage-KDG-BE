package eu.elision.marketplace.api.vat;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class VATClient {
    String checkVatServiceSource = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tns1=\"urn:ec.europa.eu:taxud:vies:services:checkVat:types\" xmlns:impl=\"urn:ec.europa.eu:taxud:vies:services:checkVat\"><soap:Header></soap:Header><soap:Body><tns1:checkVat xmlns:tns1=\"urn:ec.europa.eu:taxud:vies:services:checkVat:types\" xmlns=\"urn:ec.europa.eu:taxud:vies:services:checkVat:types\"><tns1:countryCode>{{country}}</tns1:countryCode><tns1:vatNumber>{{vat}}</tns1:vatNumber></tns1:checkVat></soap:Body></soap:Envelope>";
    String getCheckVatServiceURL = "https://ec.europa.eu/taxation_customs/vies/services/checkVatService";

    private String request(URL url, String data) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty( "Content-Type", "application/xml" );
        conn.setRequestProperty( "Content-Length", String.valueOf(data.length()));
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
            String out = checkVatServiceSource.replace("{{country}}", country);
            out = out.replace("{{vat}}", number);
            String result = this.request(new URL(this.getCheckVatServiceURL), out);
            if(!result.contains("<valid>true</valid>")) return null;
            Business vendor = new Business();
            vendor.countryCode = StringUtils.substringBetween(result, "<countryCode>", "</countryCode>");
            vendor.vatNumber = StringUtils.substringBetween(result, "<vatNumber>", "</vatNumber>");
            vendor.name = StringUtils.substringBetween(result, "<name>", "</name>");
            vendor.address = StringUtils.substringBetween(result, "<address>", "</address>");
            return vendor;
        }catch(IOException e) {
            return null;
        }
    }
}
