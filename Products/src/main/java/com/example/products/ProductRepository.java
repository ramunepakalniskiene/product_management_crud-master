package com.example.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    void deleteById(Long id);



    @Override
    List<Product> findAllById(Iterable<Long> longs);


    Optional<Product> findById(Long id);
    List<Product> findByName(String name);

}
