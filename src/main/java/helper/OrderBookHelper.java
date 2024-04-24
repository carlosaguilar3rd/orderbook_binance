package helper;

import java.util.List;

public class OrderBookHelper {

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
