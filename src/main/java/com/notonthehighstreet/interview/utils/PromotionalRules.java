package com.notonthehighstreet.interview.utils;

import com.notonthehighstreet.interview.models.Item;

import java.util.List;

public class PromotionalRules {

    public PromotionalRules() {}

    public double tenPercentDiscountForTotalsOver60GBP(double currentTotal) {
        double discount = 0;

        if (currentTotal >= 60)
            discount = currentTotal / 10;

        return discount;
    }

    public List<Item> priceReductionForBasketsWithTwoOrMoreTravelCardHolders(List<Item> basket) {
        double discount = 0;

        for (int i = 0; i < basket.size(); i++) {
            Item item = basket.get(i);
            if (item.name().equals("'Travel Card Holder'")) {
                Item holderWithUpdatedPrice = new Item(item.productId(), item.name(), 8.50);
                basket.set(i, holderWithUpdatedPrice);
            }
        }

        return basket;
    }
}
