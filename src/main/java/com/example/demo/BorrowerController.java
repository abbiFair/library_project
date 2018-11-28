package com.example.demo;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.domain.Borrower;
import com.example.demo.service.BorrowerService;

@Controller
public class BorrowerController {
	
	@Autowired
	BorrowerService borrowerService;

    @GetMapping("/validateLogin/{cardno}{password}")
    public String validateLogin(@PathVariable String cardno, @PathVariable String password, @ModelAttribute Borrower borrower, Model model) {
    	
    	List<Borrower> borList = new ArrayList<Borrower>();
    	borList = borrowerService.getBorList();
    	Borrower bor = new Borrower();
		for(Borrower b : borList){
			if(b.getCardno().equals(cardno) && b.getPassword().equals(password)){
				bor = b;
			}
		}
		
        model.addAttribute("borList", borList);
        return "bor";
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