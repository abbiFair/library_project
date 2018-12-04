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

import com.example.demo.domain.Book;
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
    public String home(@RequestParam("cardno") String cardno, 
    		@RequestParam("password") String password, Model model) {
        return "home";
    }
    
    @GetMapping("/register")
    public String home( Model model) {
        return "register";
    }
    
    @RequestMapping(value = "/newBorrower", method = RequestMethod.POST)
    public String newBorrower(HttpServletRequest request, Model model) {
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
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
    public String validateLogin(HttpServletRequest request, 
    		@RequestParam(name="errormsg", required=false, defaultValue="Invalid Login") String errormsg, Model model) {
    	
    	String cardno = request.getParameter("cardno");
    	String password = request.getParameter("password");
    	
    	Borrower bor = new Borrower();
    	bor = borrowerService.getBorrower(cardno);
    	
    	if(bor == null){
    		model.addAttribute("errormsg", errormsg);
    		return "login";
    	}
    	
		if(bor.getPassword().equals(password)){
			return "home";
		}
		
		model.addAttribute("errormsg", errormsg);
		return "login";
    }
        
    @GetMapping("/viewbooks")
    public String displayBooks(@ModelAttribute Borrower borrower,@ModelAttribute LibraryBranch branch, Model model) {
    	borrower = new Borrower();
    	borrower.setCardno("000000000");
        	
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
    public String updateBookByFilter(@ModelAttribute Borrower borrower, @ModelAttribute("branch") LibraryBranch branch, Model model) {
    	borrower = new Borrower();
        borrower.setCardno("000000000");
       	   	
        List<LibraryBranch> allBranches = new ArrayList<LibraryBranch>();
        allBranches = libraryBranchService.getAllBranches();
        	
        List<Book> bookList = new ArrayList<Book>();
        String branchid = branch.getBranchid();
        	
        if(branchid.equals("0") || branchid.equals("All")) {
        	bookList = bookService.getBookList();
        }else {
        	bookList = bookService.getBookListByBranch(branch);
        }
        	
        model.addAttribute("bookList", bookList);
        model.addAttribute("borrower", borrower);
        model.addAttribute("allBranches", allBranches);
        model.addAttribute("branch", branch);
        	
        return "viewbooks";
    }
    
    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    public void profile(@ModelAttribute("borrower") Borrower borrower, Model model) {
        List<Book> books = borrower.getBooks();
        	
        for(Book book : books)
        	System.out.println(book.getTitle());
        	
    }


}