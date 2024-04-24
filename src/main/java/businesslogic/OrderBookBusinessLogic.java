package businesslogic;

import client.http.BinanceAPIHttpClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import helper.MathHelper;
import helper.OrderBookHelper;
import model.Depth;
import model.OrderBook;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class OrderBookBusinessLogic {

    private BinanceAPIHttpClient apiHttpClient = new BinanceAPIHttpClient();

    private Double previousVolume = 0.0;

    private Map<String, Double> symbolPreviousVolumeMap = new HashMap<>();

    private int counter = 0;

    public void mainLogic(String symbol) {
        if (counter == 0) {
            System.out.println("SYMBOL" + "\t\t" + "BID" + "\t\t\t\t\t" + "ASK" + "\t\t\t" + "Volume Change");
            counter++;
        }

        try {
            String symbolParam = symbol.replace("/", "");
            String apiResponse = apiHttpClient.doGet(symbolParam);
            JsonObject jsonObject = JsonParser.parseString(apiResponse).getAsJsonObject();
//            System.out.println(jsonObject);
            Depth depth = new Gson().fromJson(jsonObject, Depth.class);
            double currentVolume = getUSDTVolume(depth);
            String highestBid = String.valueOf(depth.getBids().get(0));
            String lowestAsk = String.valueOf(depth.getAsks().get(0));

            double volumeChange = 0.0;
            if (counter == 1 || counter == 2) {
                counter++;
            } else {
                double prevVolume = symbolPreviousVolumeMap.get(symbol) == null ? this.previousVolume : symbolPreviousVolumeMap.get(symbol);
                volumeChange = MathHelper.round(prevVolume - currentVolume, 2);
            }

            OrderBook orderBook = new OrderBook(symbol, highestBid, lowestAsk, volumeChange);
            System.out.println(orderBook.getSymbol() + "\t" + highestBid + "\t" + lowestAsk + "\t" + orderBook.getVolumeChange());
            symbolPreviousVolumeMap.put(symbol, currentVolume);
//            setPreviousVolume(currentVolume);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double getUSDTVolume(Depth depth) throws Exception {
        double volumeAmount = OrderBookHelper.computeVolumeAmount(depth.getBids(), depth.getAsks());
        return volumeAmount;
    }

    public Double getPreviousVolume() {
        return previousVolume;
    }

    public void setPreviousVolume(Double previousVolume) {
        this.previousVolume = previousVolume;
    }

    public static void main(String[] args) throws Exception {
        OrderBookBusinessLogic orderBookBusinessLogic = new OrderBookBusinessLogic();
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(() -> {

//            System.out.println("PREVIOUS VOLUME: " + orderBookBusinessLogic.previousVolume);
            orderBookBusinessLogic.mainLogic("BTC/USDT");
            orderBookBusinessLogic.mainLogic("ETH/USDT");
        }, 0, 10, TimeUnit.SECONDS);
    }
}
