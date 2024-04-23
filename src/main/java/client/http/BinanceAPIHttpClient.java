package client.http;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class BinanceAPIHttpClient {

    private final String API_URL = "https://api.binance.com/api/v3/depth?";

    public String doGet(String symbol) throws Exception {
        StringBuilder sb = new StringBuilder(API_URL);
        sb.append("symbol=").append(symbol);
        sb.append("&limit=50");
        HttpGet request = new HttpGet(sb.toString());
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {

            return EntityUtils.toString(response.getEntity());
        }
    }

}
