package com.example.products;

import com.example.products.comparators.ProductComparatorByCategory;
import com.example.products.comparators.ProductComparatorByDescription;
import com.example.products.comparators.ProductComparatorByQuantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    public ProductRepository productRepository;

    public List<Product> getAllProducts() {

        return productRepository.findAll();
    }

    public List<Product> getProductsSortedByName() {
        List<Product> list = productRepository.findAll();
        return list.stream().sorted(Comparator.comparing(Product::getName)).collect(Collectors.toList());

    }

    public List<Product> getProductsSortedByPrice() {
        List<Product> list = productRepository.findAll();
        return list.stream().sorted(Comparator.comparing(Product::getPrice)).collect(Collectors.toList());

    }
    public List<Product> getProductsSortedByDescription(){
        List<Product> listByDescription = productRepository.findAll();
        Collections.sort(listByDescription, new ProductComparatorByDescription());
        return listByDescription;
    }
    public List<Product> getProductsSortedByCategory(){
        List<Product> listByCategory = productRepository.findAll();
        Collections.sort(listByCategory, new ProductComparatorByCategory());
        return listByCategory;
    }


    public Product createProduct(Product product) throws Exception {
        if (product.getName().isEmpty() || product.getPrice() == 0 ||
                product.getCategory().toString() == null)
            throw new Exception("All lines should be filled in, please re-check");
        else {

            product.setQuantity(product.getInitial_quantity());
            productRepository.saveAndFlush(product);
        }
        return product;
    }


    public Product findProductById(Long id) throws Exception {

        for (Product product : productRepository.findAll()) {
            if (product.getId().equals(id))
                return productRepository.findById(id).get();
        }
        throw new Exception("Product not found");

    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public void updateProductQuantity(Product product) {
        product.setQuantity(product.getQuantity()-1);
        productRepository.saveAndFlush(product);
    }

    public Product updateProduct(Product product) throws Exception {

        for (Product currentProduct : productRepository.findAll()) {

            if (currentProduct.getId().equals(product.getId())) {
                currentProduct.setName(product.getName());
                currentProduct.setDescription(product.getDescription());
                currentProduct.setPrice(product.getPrice());
                currentProduct.setQuantity(product.getQuantity());
                currentProduct.setCategory(product.getCategory());
                productRepository.saveAndFlush(currentProduct);
                return currentProduct;
            }
        }

        throw new Exception("product not found!");

    }

    public Product findProductByName(String name) {
        return productRepository.findByName(name).get(0);

    }
}
