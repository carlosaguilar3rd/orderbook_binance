package model;

public class OrderBook {

    public OrderBook(String symbol, String bid, String ask, double volumeChange) {
        this.symbol = symbol;
        this.bid = bid;
        this.ask = ask;
        this.volumeChange = volumeChange;
    }

    private String symbol;

    private String bid;

    private String ask;

    private double volumeChange;

    public double getVolumeChange() {
        return volumeChange;
    }

    public void setVolumeChange(double volumeChange) {
        this.volumeChange = volumeChange;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }
}
