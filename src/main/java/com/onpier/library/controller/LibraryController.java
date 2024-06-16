package com.onpier.library.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onpier.library.model.Book;
import com.onpier.library.model.User;
import com.onpier.library.service.LibraryService;

@RestController
public class LibraryController {

	@Autowired
	private LibraryService libraryService;
	
	@GetMapping("/users/borrowed")
	public List<User> getUsersWhoBorrowedBooks(){
		return libraryService.getUsersWhoBorrowedBooks();
	}
	
	@GetMapping("/users/nonTerminated")
	public List<User> getNonTerminatedUsersWithoutBorrowedBooks(){
		return libraryService.getNonTerminatedUsersWithoutBorrowedBooks();
	}
	
	@GetMapping("/users/borrowedOn/{date}")
	public List<User> getNonTerminatedUsersWithoutBorrowedBooks(@PathVariable LocalDate date){
		return libraryService.getNonTerminatedUsersWithoutBorrowedBooks(date);
	}
	
	@GetMapping("/books/borrowedByUser")
	public List<Book> getBooksBorrowedByUserDateRange(@RequestParam String user, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate){
		return libraryService.getBooksBorrowedByUserDateRange(user,startDate,endDate);
	}
	
	@GetMapping("/books/available")
	public List<Book> getAvailableBooksBorrowed(){
		return libraryService.getAvailableBooksBorrowed();
	}
}
