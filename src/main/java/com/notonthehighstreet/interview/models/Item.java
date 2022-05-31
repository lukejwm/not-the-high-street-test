package com.notonthehighstreet.interview.models;

import java.util.Objects;

public record Item (String productId, String name, double price) {
    public Item {
        //Ensure that object type data is not null (primitives are always not null)
        Objects.requireNonNull(productId);
        Objects.requireNonNull(name);
    }
}
