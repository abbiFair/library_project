package com.example.demo;
import java.util.ArrayList;
import java.util.Iterator;
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
    public String home(@SessionAttribute("borrower") Borrower borrower, Model model, HttpSession session) {
    	Borrower bor = new Borrower();
    	bor = borrower;
    	model.addAttribute("borrower", bor);
    	
    	List<BookLoan> myLoans = new ArrayList<BookLoan>();
    	myLoans = bookService.getBookLoanByCard(bor.getCardno());
    	
    	List<BookLoan> fixedBooks = new ArrayList<BookLoan>(); 
		for(BookLoan book: myLoans) {
			if(book.getRating() == null) {
				book.setRating("Not Rated");
			}
			fixedBooks.add(book);
		}
		
    	model.addAttribute("myLoans", fixedBooks);
    	session.setAttribute("myLoans", fixedBooks);

        return "home";
    }
    
    @GetMapping("/ratebooks{bookid}-{branchid}")
    public String ratebooks(@PathVariable String bookid, @PathVariable String branchid, 
    		@SessionAttribute("borrower") Borrower borrower, Model model, HttpSession session) {
    	Borrower bor = new Borrower();
    	bor = borrower;
    	Book ratedBook = new Book();
    	ratedBook.setBookid(bookid);
    	model.addAttribute("borrower", bor);
    	BookLoan bookloan = new BookLoan(bookid, branchid, bor.getCardno());
    	ratedBook = bookService.getBook(ratedBook);
    	bookloan.setTitle(ratedBook.getTitle());
    	model.addAttribute("bookloan", bookloan);
    	session.setAttribute("bookloan", bookloan);
        return "ratebooks";
    }
    
    @GetMapping("/submitRatings{bookid}-{branchid}")
    public String submitRatings(@PathVariable String bookid, @PathVariable String branchid,
    		@SessionAttribute("borrower") Borrower borrower,
    		HttpServletRequest request, Model model, HttpSession session) {
    	Borrower bor = new Borrower();
    	bor = borrower;
    	model.addAttribute("borrower", bor);
    	
		String rating = request.getParameter("rating");
		
		BookLoan bookloan = new BookLoan(bookid, branchid, bor.getCardno());
		bookloan.setRating(rating);
		
		bookService.updateBookLoan(bookloan);
		
		List<BookLoan> myLoans = new ArrayList<BookLoan>();
    	myLoans = bookService.getBookLoanByCard(bor.getCardno());
    	
    	List<BookLoan> fixedBooks = new ArrayList<BookLoan>(); 
		for(BookLoan book: myLoans) {
			if(book.getRating() == null) {
				book.setRating("Not Rated");
			}
			fixedBooks.add(book);
		}
		
    	model.addAttribute("myLoans", fixedBooks);
    	session.setAttribute("myLoans", fixedBooks);
    	
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
    	
		List<BookLoan> myLoans = new ArrayList<BookLoan>();
    	myLoans = bookService.getBookLoanByCard(bor.getCardno());
    	
    	List<BookLoan> fixedBooks = new ArrayList<BookLoan>(); 
		for(BookLoan book: myLoans) {
			if(book.getRating() == null) {
				book.setRating("Not Rated");
			}
			fixedBooks.add(book);
		}
		model.addAttribute("borrower", bor);
    	model.addAttribute("myLoans", fixedBooks);
		return "home";
    }
    
    @RequestMapping(value = "/newBorrower", method = RequestMethod.POST)
    public String newBorrower(HttpServletRequest request, @RequestParam(name="errormsg", 
    required=false, defaultValue="") String errormsg, Model model, HttpSession session) {
    	
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		if(phone.matches("[0-9-]+") && phone.length() <= 10) {
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
		
    	model.addAttribute("borrower", bor);
    	session.setAttribute("borrower", bor);
    	errormsg = "Your Card Number is: " + newCardNo_String;
    	model.addAttribute("errormsg", errormsg);
		return "home_newuser";
    }
    
    @GetMapping("/login")
    public String login(@RequestParam(name="errormsg", required=false, defaultValue="") String errormsg,
    		Model model) {
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
			
			List<BookLoan> myLoans = new ArrayList<BookLoan>();
	    	myLoans = bookService.getBookLoanByCard(bor.getCardno());
	    	
	    	List<BookLoan> fixedBooks = new ArrayList<BookLoan>(); 
			for(BookLoan book: myLoans) {
				if(book.getRating() == null) {
					book.setRating("Not Rated");
				}
				fixedBooks.add(book);
			}
			
	    	model.addAttribute("myLoans", fixedBooks);
	    	session.setAttribute("myLoans", fixedBooks);
	    	
	    	if(fixedBooks.isEmpty()) {
	    		return "home_newuser";
	    	}
	    	
			return "home";
		}
		
		errormsg = "Invalid Card Number Password Combination";
		model.addAttribute("errormsg", errormsg);
		return "login";
    }
        
    @GetMapping("/viewbooks")
    public String displayBooks(@SessionAttribute("borrower") Borrower bor, HttpServletRequest request, @ModelAttribute Borrower borrower, 
    		@ModelAttribute LibraryBranch branch, Model model) {

    	String action = request.getParameter("action");
    	List<BookLoan> myLoans = new ArrayList<BookLoan>();

    	if ("Edit Profile".equals(action)) {
    		model.addAttribute("borrower", bor);
    	    return "editBorrower";
    	}
    	else if ("Logout".equals(action)) {
    	    return "login";
    	}
    	if("Profile".equals(action)) {
        	myLoans = bookService.getBookLoanByCard(bor.getCardno());
        	
        	List<BookLoan> fixedBooks = new ArrayList<BookLoan>(); 
    		for(BookLoan book: myLoans) {
    			if(book.getRating() == null) {
    				book.setRating("Not Rated");
    			}
    			fixedBooks.add(book);
    		}
    		
        	model.addAttribute("myLoans", fixedBooks);
    		return "home";
    	}
    	
    	List<Book> bookList = new ArrayList<Book>();
    	List<Book> unavailList = new ArrayList<Book>();
    	List<Book> availList = new ArrayList<Book>();
    	bookList = bookService.getBookList();
    	
		for(Book book: bookList) {
			if(book.getNumberavailable().equals("0")) {
				unavailList.add(book);
			}
			else {
				availList.add(book);
			}
    	}
    	
    	branch = new LibraryBranch();
    	List<LibraryBranch> allBranches = new ArrayList<LibraryBranch>();
    	allBranches = libraryBranchService.getAllBranches();
    	
    	model.addAttribute("unavailList", unavailList);
        model.addAttribute("availList", availList);
        model.addAttribute("borrower", borrower);
        model.addAttribute("allBranches", allBranches);
        model.addAttribute("branch", branch);
        
        return "viewbooks";
    }
    
    @RequestMapping(value = "/updatebooklist", method = RequestMethod.POST)
    public String updateBookByFilter(@SessionAttribute("borrower") Borrower borrower, @ModelAttribute("branch") LibraryBranch branch, Model model) {
    	List<LibraryBranch> allBranches = new ArrayList<LibraryBranch>();
    	allBranches = libraryBranchService.getAllBranches();
    	
    	List<Book> bookList = new ArrayList<Book>();
    	List<Book> unavailList = new ArrayList<Book>();
    	List<Book> availList = new ArrayList<Book>();
    	String branchid = branch.getBranchid();
    	
    	if(!(branchid.equals("0"))) {
    		bookList = bookService.getBookListByBranch(branch);
    		for(Book book: bookList) {
    			if(book.getRating() == null) {
    				book.setRating("Not Rated");
    			}
    			
    			if(book.getNumberavailable().equals("0")) {
    				unavailList.add(book);
    			}
    			else {
    				availList.add(book);
    			}
        	}
    	}
    	
    	model.addAttribute("unavailList", unavailList);
    	model.addAttribute("availList", availList);
        model.addAttribute("borrower", borrower);
        model.addAttribute("allBranches", allBranches);
        model.addAttribute("branch", branch);
    	
        if(unavailList.isEmpty() && !availList.isEmpty()) {
        	return "viewbooksbybranch_availonly";
        }
        else if(availList.isEmpty() && !unavailList.isEmpty()) {
        	return "viewbooksbybranch_noneavail";
        }
        else if(availList.isEmpty() && unavailList.isEmpty()) {
        	return "viewbooks";
        }
        
    	return "viewbooksbybranch";
    }
    
    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    public String checkOutBooks(@SessionAttribute("borrower") Borrower bor, @ModelAttribute("borrower") Borrower borrower, 
    		@ModelAttribute("branch") LibraryBranch branch, Model model, HttpSession session) {
    	// Update session object borrower to include the borrower's books that are being checked out
    	List<Book> checkoutbooks = borrower.getBooks();
    	
    	if(checkoutbooks.isEmpty()) {
        	List<BookLoan> myLoans = new ArrayList<BookLoan>();
        	myLoans = bookService.getBookLoanByCard(bor.getCardno());
        	List<BookLoan> fixedBooks = new ArrayList<BookLoan>(); 
    		for(BookLoan book: myLoans) {
    			if(book.getRating() == null) {
    				book.setRating("Not Rated");
    			}
    			fixedBooks.add(book);
    		}
        	
        	model.addAttribute("myLoans", fixedBooks);
        	session.setAttribute("myLoans", fixedBooks);
        	model.addAttribute("borrower", bor);
        	return "home";
    	}
    	
    	Borrower completedBorrower = bor;
    	completedBorrower.setBooks(checkoutbooks);
    	
    	// Prepare book loan objects to verify check out
    	BookLoan bookloan;
    	Boolean duplicate = false;
    	List<BookLoan> myLoans = new ArrayList<BookLoan>();
    	myLoans = bookService.getBookLoanByCard(bor.getCardno());
    	List<Book> verifiedBooks = new ArrayList<Book>();
    	List<Book> duplicateBooks = new ArrayList<Book>();
    	
    	for(Book book : checkoutbooks) {
    		bookloan = new BookLoan(book.getBookid(),branch.getBranchid(),bor.getCardno());
    		
    		for(BookLoan loan: myLoans) {
    			if(loan.getBookid().equals(bookloan.getBookid()) && loan.getBranchid().equals(bookloan.getBranchid())) {
    				duplicateBooks.add(book);
    				duplicate = true;
    			}
    		}
    		
    		if(!duplicate) {
    			verifiedBooks.add(book);
    		}
    		
    		duplicate = false;
    	}
    	
    	completedBorrower.setBooks(verifiedBooks);
    	
    	model.addAttribute("verifiedbooks", verifiedBooks);
    	model.addAttribute("duplicatebooks", duplicateBooks);
        session.setAttribute("branch", branch);
    	model.addAttribute("borrower", bor);
    	
    	if(verifiedBooks.isEmpty() && !duplicateBooks.isEmpty()) {
    		return "checkout_novalidbooks";
    	}
    	else if(!verifiedBooks.isEmpty() && duplicateBooks.isEmpty()) {
    		return "checkout_normallistonly";
    	}
    	
    	return "checkout";
    }
    
    @RequestMapping(value = "/confirmcheckout", method = RequestMethod.POST)
    public String confirmCheckOut(@SessionAttribute("borrower") Borrower borrower, @SessionAttribute("branch") LibraryBranch libraryBranch,
    		@ModelAttribute("branch") LibraryBranch branch, HttpServletRequest request, Model model, HttpSession session) {
    	
    	String action = request.getParameter("action");
    	List<LibraryBranch> allBranches = new ArrayList<LibraryBranch>();
    	
    	if ("Cancel".equals(action)) {
    		allBranches = libraryBranchService.getAllBranches();
    		model.addAttribute("allBranches", allBranches); 	
    	    return "viewbooks";
    	}
    	
    	BookLoan bookloan;
    	
    	for(Book book : borrower.getBooks()) {
    		bookloan = new BookLoan(book.getBookid(),libraryBranch.getBranchid(),borrower.getCardno());
    		bookService.insertBookLoan(bookloan);
    		System.out.println("Book loan for" + book.getTitle() + " has been inserted.");
    	}
    	
    	
    	
    	List<BookLoan> myLoans = new ArrayList<BookLoan>();
    	myLoans = bookService.getBookLoanByCard(borrower.getCardno());
    	List<BookLoan> fixedBooks = new ArrayList<BookLoan>(); 
		for(BookLoan book: myLoans) {
			if(book.getRating() == null) {
				book.setRating("Not Rated");
			}
			fixedBooks.add(book);
		}
    	
    	model.addAttribute("myLoans", fixedBooks);
    	session.setAttribute("myLoans", fixedBooks);
    	model.addAttribute("borrower", borrower);
    	
    	
    	return "home";
    }
}