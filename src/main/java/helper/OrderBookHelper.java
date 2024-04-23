package helper;

import client.websocket.BinanceWebSocketClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class OrderBookHelper {
    public static void main(String[] args) throws URISyntaxException {
        BinanceWebSocketClient client = new BinanceWebSocketClient(new URI("wss://stream.binance.com:9443/stream?streams=btcusdt@depth/ethusdt@depth"));
        client.connect();

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

        executorService.scheduleAtFixedRate(() -> {
            // Display the latest message every 10 seconds
            System.out.println("Latest message: " + client.getBinanceMessage());
        }, 0, 10, TimeUnit.SECONDS);
    }

    public static double computeVolumeAmount(List<Object> bidList, List<Object> askList) {
        double bidTotal = 0;
        double askTotal = 0;

        if (bidList == null || bidList.isEmpty()) {
        } else {
            for (Object bid : bidList) {
//                System.out.printf("Bid Pair: " + bid);
                String tempBid = String.valueOf(bid).replaceAll("[\\[\\]]", "");
//                System.out.printf("After Bid Pair: " + tempBid);
                String[] bidTrade = String.valueOf(tempBid).split(",");
                double price = Double.parseDouble(bidTrade[0]);
                double quantity = Double.parseDouble(bidTrade[1]);
                double bidTradeProduct = price * quantity;
                bidTotal = MathHelper.round(bidTotal + bidTradeProduct, 2);
            }
        }

        if (askList == null || askList.isEmpty()) {
        } else {
            for (Object ask : askList) {
                String tempAsk = String.valueOf(ask).replaceAll("[\\[\\]]", "");
                String[] bidTrade = String.valueOf(tempAsk).split(",");
                double price = Double.parseDouble(bidTrade[0]);
                double quantity = Double.parseDouble(bidTrade[1]);
                double askTradeProduct = price * quantity;
                askTotal = MathHelper.round(askTotal + askTradeProduct, 2);
            }
        }

        return MathHelper.round(bidTotal + askTotal, 2);
    }

}
