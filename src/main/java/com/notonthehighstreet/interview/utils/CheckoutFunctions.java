package com.notonthehighstreet.interview.utils;

import com.notonthehighstreet.interview.models.Item;

public interface CheckoutFunctions {
    void scan(Item item);
    double total();
}
