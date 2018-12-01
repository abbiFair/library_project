package com.example.demo;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.domain.Borrower;
import com.example.demo.service.BorrowerService;

@Controller
public class LibraryController {
	
	@Autowired
	BorrowerService borrowerService;

    @GetMapping("/home")
    public String home(@RequestParam("cardno") String cardno, 
    		@RequestParam("password") String password, Model model) {
        return "home";
    }
    
    
    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }
    
	
    @RequestMapping(value = "/validateLogin", method = RequestMethod.POST)
    public String validateLogin(HttpServletRequest request, Model model) {
    	
    	String cardno = request.getParameter("cardno");
    	String password = request.getParameter("password");
    	
    	Borrower bor = new Borrower();
    	bor = borrowerService.getBorrower(cardno);
    	
    	if(bor == null){
    		return "login";
    	}
    	
		if(bor.getPassword().equals(password)){
			return "home";
		}
		return "login";
    }
    
    @GetMapping("/addBorrower/{name}{phone}{address}{password}")
    public String addBorrower(@PathVariable String name, @PathVariable String phone, @PathVariable String address, 
    		@PathVariable String password, @ModelAttribute Borrower borrower, Model model) {
    	
    	Borrower bor = new Borrower();
    	bor.setName(name);
    	bor.setPhone(phone);
    	bor.setAddress(address);
    	bor.setPassword(password);
    	//TO DO Set Card Number
    	
    	borrowerService.insertBorrower(bor);    	
        model.addAttribute("borrower", bor);
        return "addBorrower";
    }


}