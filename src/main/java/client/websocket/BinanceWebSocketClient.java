package client.websocket;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class BinanceWebSocketClient extends WebSocketClient {

    public BinanceWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    private String binanceMessage;

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Opened connection");
        // Subscribe to BTC/USDT and ETH/USDT order book streams
        this.send("{\"method\": \"SUBSCRIBE\", \"params\": [\"btcusdt@depth20\", \"ethusdt@depth20\"], \"id\": 1}");
    }

    @Override
    public void onMessage(String message) {
//        setBinanceMessage(message);
        System.out.println("Received: " + message);
        // Here you would parse the message and update your order book accordingly
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Closed with exit code " + code + " additional info: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

    public static void main(String[] args) throws URISyntaxException {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

        BinanceWebSocketClient client = new BinanceWebSocketClient(new URI("wss://stream.binance.com:9443/stream?streams=btcusdt@depth30/ethusdt@depth30"));
        client.connect();


    }

    public String getBinanceMessage() {
        return binanceMessage;
    }

    public void setBinanceMessage(String binanceMessage) {
        this.binanceMessage = binanceMessage;
    }
}
