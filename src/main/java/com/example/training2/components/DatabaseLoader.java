package com.example.training2.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.example.training2.Repositories.ProductRepository;
import com.example.training2.Repositories.UserRepository;
import com.example.training2.Models.Product;
import com.example.training2.Models.User;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

@Component
public class DatabaseLoader {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private UserRepository userRepository;
	
	@PostConstruct
	private void initDatabase() {
		productRepository.save(
				new Product("1818","Fanta","Bebida",7)
		);
		
		List<Product> productList = productRepository.findAll();
		productList.stream().forEach( product -> System.out.println("Code: " + product.getCode()) );
		
		List<GrantedAuthority> adminRoles = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"),
                new SimpleGrantedAuthority("ROLE_ADMIN"));
        List<GrantedAuthority> userRoles = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
        userRepository.save(new User("root", "root1", adminRoles));
        userRepository.save(new User("user", "user1", userRoles));
	}

}
