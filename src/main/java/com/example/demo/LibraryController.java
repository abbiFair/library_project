package com.example.demo;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.domain.Book;
import com.example.demo.domain.BookLoan;
import com.example.demo.domain.Borrower;
import com.example.demo.domain.LibraryBranch;
import com.example.demo.service.BookService;
import com.example.demo.service.BorrowerService;
import com.example.demo.service.LibraryBranchService;

@Controller
public class LibraryController {
	
	@Autowired
	BorrowerService borrowerService;
		
	@Autowired
	BookService bookService;
	
	@Autowired
	LibraryBranchService libraryBranchService;
	
    @GetMapping("/home")
    public String home(@SessionAttribute("borrower") Borrower borrower, Model model) {
    	Borrower bor = new Borrower();
    	bor = borrower;
    	model.addAttribute("borrower", bor);
        return "home";
    }
    
    @GetMapping("/register")
    public String register( Model model) {
        return "register";
    }
    
    @GetMapping("/editBorrower")
    public String editBorrower(@RequestParam(name="errormsg", required=false, defaultValue="") String errormsg, 
    		@SessionAttribute("borrower") Borrower borrower, Model model) {
    	Borrower bor = new Borrower();
    	bor = borrower;
    	model.addAttribute("borrower", bor);
    	model.addAttribute("errormsg", errormsg);
        return "editBorrower";
    }
    
    
    @RequestMapping(value = "/updateBorrower", method = RequestMethod.POST)
    public String editBorrower(@SessionAttribute("borrower") Borrower borrower, 
    		HttpServletRequest request, @RequestParam(name="errormsg", 
    		required=false, defaultValue="") String errormsg, Model model) {
    	
    	Borrower bor = new Borrower();
    	bor = borrower;
    	model.addAttribute("borrower", bor);
    	
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		
		if(phone.matches("[0-9-]+") && phone.length() <= 12 ) {
			//phone number is in correct format
		}
		else {
			//phone number is not in correct format
			errormsg = "Enter phone number as a 10 digit number";
			model.addAttribute("errormsg", errormsg);
			return "editBorrower";
		}
		String password = request.getParameter("password");
		
    	bor.setName(name);
    	bor.setPhone(phone);
    	bor.setAddress(address);
    	bor.setPassword(password);    	
    	borrowerService.updateBorrower(bor);
		
		return "home";
    }
    
    @RequestMapping(value = "/newBorrower", method = RequestMethod.POST)
    public String newBorrower(HttpServletRequest request, @RequestParam(name="errormsg", 
    required=false, defaultValue="") String errormsg, Model model) {
    	
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		if(phone.matches("[0-9]+") && phone.length() == 10) {
			//phone number is in correct format
		}
		else {
			//phone number is not in correct format
			errormsg = "Enter phone number as a 10 digit number";
			model.addAttribute("errormsg", errormsg);
			return "register";
		}
		String password = request.getParameter("password");
		
    	Borrower bor = new Borrower();
    	bor.setName(name);
    	bor.setPhone(phone);
    	bor.setAddress(address);
    	bor.setPassword(password);
    	
    	//Get All Card Numbers
    	List<String> CardNos_String = borrowerService.getCardNumbers();
    	List<Integer> CardNos_Integer = new ArrayList<>();
    	for(String s : CardNos_String) {
    		CardNos_Integer.add(Integer.parseInt(s));
    	}
    	Integer newCardNo_Int;
    	newCardNo_Int = 1;
    	while(CardNos_Integer.contains(newCardNo_Int)) {
    		newCardNo_Int += 1;
    	}
    	
    	String newCardNo_String;
    	newCardNo_String = newCardNo_Int.toString();
    	bor.setCardno(newCardNo_String);    	
    	borrowerService.insertBorrower(bor);
		
		return "home";
    }
    
    @GetMapping("/login")
    public String login(@RequestParam(name="errormsg", required=false, defaultValue="") String errormsg, Model model) {
    	model.addAttribute("errormsg", errormsg);
    	return "login";
    }
    
	
    @RequestMapping(value = "/validateLogin", method = RequestMethod.POST)
    public String validateLogin(HttpServletRequest request, HttpSession session,
    		@RequestParam(name="errormsg", required=false, defaultValue="") String errormsg, Model model) {
    	
    	
    	String cardno = request.getParameter("cardno");
    	String password = request.getParameter("password");
    	
    	Borrower bor = new Borrower();
    	bor = borrowerService.getBorrower(cardno);
    	
    	if(bor == null){
    		errormsg = "Invalid Card Number";
    		model.addAttribute("errormsg", errormsg);
    		return "login";
    	}
    	
		if(bor.getPassword().equals(password)){
			session.setAttribute("borrower", bor);
			model.addAttribute("borrower", bor);
			return "home";
		}
		
		errormsg = "Invalid Card Number Password Combination";
		model.addAttribute("errormsg", errormsg);
		return "login";
    }
        
    @GetMapping("/viewbooks")
    public String displayBooks(@ModelAttribute Borrower borrower,@ModelAttribute LibraryBranch branch, Model model) {
    	//borrower = new Borrower();
    	//borrower.setCardno("000000000");
    	
    	List<Book> bookList = new ArrayList<Book>();
    	bookList = bookService.getBookList();
    	
    	branch = new LibraryBranch();
    	List<LibraryBranch> allBranches = new ArrayList<LibraryBranch>();
    	allBranches = libraryBranchService.getAllBranches();
    	
        model.addAttribute("bookList", bookList);
        model.addAttribute("borrower", borrower);
        model.addAttribute("allBranches", allBranches);
        model.addAttribute("branch", branch);
        
        return "viewbooks";
    }
    
    @RequestMapping(value = "/updatebooklist", method = RequestMethod.POST)
    public String updateBookByFilter(@ModelAttribute("borrower") Borrower borrower, @ModelAttribute("branch") LibraryBranch branch, Model model) {
    	//borrower = new Borrower();
    	//borrower.setCardno("000000000");
   	   	
    	List<LibraryBranch> allBranches = new ArrayList<LibraryBranch>();
    	allBranches = libraryBranchService.getAllBranches();
    	
    	List<Book> bookList = new ArrayList<Book>();
    	String branchid = branch.getBranchid();
    	
    	if(!(branchid.equals("0"))) {
    		bookList = bookService.getBookListByBranch(branch);
    	}
    	
    	model.addAttribute("bookList", bookList);
        model.addAttribute("borrower", borrower);
        model.addAttribute("allBranches", allBranches);
        model.addAttribute("branch", branch);
    	
    	return "viewbooksbybranch";
    }
    
    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    public String checkOutBooks(@ModelAttribute("borrower") Borrower borrower, @ModelAttribute("branch") LibraryBranch branch, Model model, HttpSession session) {
    	borrower.setCardno("12");
    	List<Book> checkoutbooks = borrower.getBooks();

    	model.addAttribute("checkoutbooks", checkoutbooks);
    	model.addAttribute("borrower", borrower);
    	session.setAttribute("borrower", borrower);
    	
    	return "checkout";
    }
    
    @RequestMapping(value = "/confirmcheckout", method = RequestMethod.POST)
    public void confirmCheckOut(@SessionAttribute("borrower") Borrower borrower, @ModelAttribute("branch") LibraryBranch branch, Model model) {
    	List<Book> checkoutbooks = borrower.getBooks();
    	BookLoan bookloan;
    	
    	for(Book book : checkoutbooks) {
    		bookloan = new BookLoan(book.getBookid(),branch.getBranchid(),borrower.getCardno());
    		bookService.insertBookLoan(bookloan);
    		System.out.println("Book loan for" + book.getTitle() + " has been inserted.");
    	}
    		
    	System.out.println(branch.getBranchid());
    	
    	//model.addAttribute("checkoutbooks", checkoutbooks);
    }


}