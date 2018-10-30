package com.example.training2.Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.example.training2.Models.Product;
import com.example.training2.Models.Error;
import com.example.training2.Repositories.ProductRepository;

@Controller
public class ProductList {
	private Product prod = new Product("12345","Coca-Cola","Bebida",5);
	private Map<String, Product> productos = new HashMap<String, Product>() {{ put ("12345",prod); }};
	
	@Autowired
	private ProductRepository productRepository;
	
	@RequestMapping(value = {"/","/login"})
	public String login() {
		return "index";
	}
	/*
	@GetMapping("/")
	public String route() {
		return "index";
	}*/
	@Secured({"ROLE_USER","ROLE_ADMIN"})
	@GetMapping("/list")
	public String list(Model model) {
		List<Product> productList = productRepository.findAll();
		model.addAttribute("productos",productList);
		return "list";
	}
	
	@Secured({"ROLE_USER","ROLE_ADMIN"})
	@GetMapping("/show/{id}")
	public String show(@PathVariable String id, Model model) {
		model.addAttribute("product",productRepository.findByCode(id));
		return "resume";
	}
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/remove/{id}")
	public String delete(@PathVariable String id, Model model) {
		if(productRepository.findByCode(id) != null) {
			productRepository.delete(productRepository.findByCode(id));
			//List<Product> productList = productRepository.findAll();
			//model.addAttribute("productos",productList);
			return "redirect:/list";
		} else {
			Error error = new Error("Error al Eliminar","No se ha podido eliminar el producto selecionado","list","Atras");
			model.addAttribute("error",error);
			return "aviso";
		}
	}
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/addForm")
	public String addProd(@ModelAttribute Product product) {
		return "addForm";
	}
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String newProd(@ModelAttribute("product") Product product,BindingResult result, ModelMap model) {
		if(productRepository.findByCode(product.getCode()) == null) {
			model.addAttribute("code", product.getCode());
	        model.addAttribute("name", product.getName());
	        model.addAttribute("description", product.getDescription());
	        model.addAttribute("price", product.getPrice());
	        //productos.put(product.getCode(), product);
	        productRepository.save(new Product(product.getCode(),product.getName(),product.getDescription(),product.getPrice()));
	        return "resume";
		} else {
			Error error = new Error("Error al Crear el Producto","El codigo introducido coincide con un producto existente","addForm","Volver al formulario");
			model.addAttribute("error",error);
			return "aviso";
		}
	}
}