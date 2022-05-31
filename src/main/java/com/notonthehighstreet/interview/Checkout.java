package com.notonthehighstreet.interview;

import com.notonthehighstreet.interview.models.Item;
import com.notonthehighstreet.interview.utils.CheckoutFunctions;
import com.notonthehighstreet.interview.utils.PromotionalRules;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Checkout implements CheckoutFunctions {
    private List<Item> basket;
    private final PromotionalRules promoRules;
    private double currentTotal;

    //Keep track of travel card holders as they are scanned so know when to apply discount
    private int travelCardTotal = 0;

    public Checkout(PromotionalRules promoRules) {
        this.promoRules = promoRules;
        basket = new ArrayList<>();
    }

    public void setCurrentTotal(double currentTotal) {
        this.currentTotal = currentTotal;
    }

    public double getCurrentTotal() {
        return currentTotal;
    }

    @Override
    public void scan(Item item) {
        if(item.name().equals("'Travel Card Holder'")) {
            travelCardTotal += 1;
        }

        //scanning items is to add items to the basket and update the current total
        this.basket.add(item);
        currentTotal += item.price();
    }

    @Override
    public double total() {
        if(travelCardTotal >= 2) {
            //update basket with new prices for travel card holders
            basket = promoRules.priceReductionForBasketsWithTwoOrMoreTravelCardHolders(basket);
            //recalculate value of whole basket
            currentTotal = 0;
            for(Item item: basket) {
                currentTotal += item.price();
            }
        }

        //total is to return the total with discounts applied (if any).
        //ensure that price is correctly rounded to the nearest penny to avoid errors.
        DecimalFormat df = new DecimalFormat("0.00");
        double finalTotal = currentTotal - promoRules.tenPercentDiscountForTotalsOver60GBP(currentTotal);
        df.setRoundingMode(RoundingMode.UP);

        return Double.parseDouble(df.format(finalTotal));
    }
}
