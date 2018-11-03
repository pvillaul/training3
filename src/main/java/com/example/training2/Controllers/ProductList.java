package com.example.training2.Controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.example.training2.Models.Product;
import com.example.training2.Models.User;
import com.example.training2.Models.Error;
import com.example.training2.Repositories.ProductRepository;
import com.example.training2.Repositories.UserRepository;

@Controller
public class ProductList {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(value = {"/","/login"})
	public String login() {
		return "index";
	}
	
	@RequestMapping(value = {"/logout"})
	public String logout() {
		return "redirect:/login";
	}
	
	@Secured({"ROLE_USER","ROLE_ADMIN"})
	@GetMapping("/list")
	public String list(Model model, Authentication authentication) {
		String username = authentication.getName();
		User user;
		try {
			user = userRepository.findByNombre(username);
		} catch(Exception e) {
			throw new BadCredentialsException("User not found");
		}
		List<Product> productList = productRepository.findAll();
		model.addAttribute("productos",productList);
		model.addAttribute("user",user);
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