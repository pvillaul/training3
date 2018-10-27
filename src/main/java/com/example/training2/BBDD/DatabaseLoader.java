package com.example.training2.BBDD;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.training2.Repositories.ProductRepository;
import com.example.training2.Models.Product;
import java.util.List;

import javax.annotation.PostConstruct;

public class DatabaseLoader {
	@Autowired
	private ProductRepository productRepository;
	
	@PostConstruct
	private void initDatabase() {
		productRepository.save(
				new Product("1818","Fanta","Bebida",7)
		);
		
		List<Product> productList = productRepository.findAll();
		productList.stream().forEach( product -> System.out.println("Code: " + product.getCode()) );
	}

}
