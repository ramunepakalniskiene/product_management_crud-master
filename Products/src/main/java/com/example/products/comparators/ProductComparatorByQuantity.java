package com.example.products.comparators;

import com.example.products.Product;

import java.util.Comparator;

public class ProductComparatorByQuantity implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {

        return (int) (o1.getPrice()-(o2.getPrice()));
    }
}
