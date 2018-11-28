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

    @GetMapping("/borrowers")
    public String getBorrowers(Model model) {
    	List<Borrower> borList = new ArrayList<Borrower>();
    	borList = borrowerService.getBorList();
        model.addAttribute("borList", borList);
        return "borLanding";
    }
    
    @GetMapping("/addBorrower")
    public String addBorrower(@PathVariable String password, @ModelAttribute Borrower borrower, Model model) {
    	//empList = employeeService.getEmpList();
        model.addAttribute("borrower", borrower);
        return "addBorrower";
    }


}