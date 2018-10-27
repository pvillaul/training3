package com.example.training2.Repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.example.training2.Models.Product;

public interface ProductRepository extends CrudRepository<Product, String>{
	List<Product> findAll();
	Product findByCode(String code);
}
