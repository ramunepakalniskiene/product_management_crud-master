package com.example.products.comparators;

import com.example.products.Product;

import java.util.Comparator;

public class ProductComparatorByCategory implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {

        return   o1.getCategory().toString().compareTo(o2.getCategory().toString());
    }

}
