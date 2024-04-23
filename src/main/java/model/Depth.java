package model;

import java.util.List;

public class Depth {

    private String lastUpdateId;

    private List<Object> bids;

    private List<Object> asks;

    public String getLastUpdateId() {
        return lastUpdateId;
    }

    public void setLastUpdateId(String lastUpdateId) {
        this.lastUpdateId = lastUpdateId;
    }

    public List<Object> getBids() {
        return bids;
    }

    public void setBids(List<Object> bids) {
        this.bids = bids;
    }

    public List<Object> getAsks() {
        return asks;
    }

    public void setAsks(List<Object> asks) {
        this.asks = asks;
    }
}
