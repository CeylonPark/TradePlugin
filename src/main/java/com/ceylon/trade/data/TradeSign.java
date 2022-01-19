package com.ceylon.trade.data;

import java.util.Objects;
import java.util.UUID;

public class TradeSign {
    private final UUID registrant;
    private final String title;
    private final String contents;

    public TradeSign(UUID registrant, String title, String contents) {
        this.registrant = registrant;
        this.title = title;
        this.contents = contents;
    }

    public UUID getRegistrant() {
        return this.registrant;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContents() {
        return this.contents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TradeSign tradeSign = (TradeSign) o;
        return Objects.equals(registrant, tradeSign.registrant) && Objects.equals(title, tradeSign.title) && Objects.equals(contents, tradeSign.contents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registrant, title, contents);
    }
}
