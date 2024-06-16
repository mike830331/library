package com.onpier.library.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	public List<User> getUsersWhoBorrowedBooks() {
		return libraryService.getUsersWhoBorrowedBooks();
	}

	@GetMapping("/users/nonTerminated")
	public List<User> getNonTerminatedUsersWithoutBorrowedBooks() {
		return libraryService.getNonTerminatedUsersWithoutBorrowedBooks();
	}

	/**
	 * Retrieves a list of users who borrowed books on a specific date.
	 *
	 * @param date the specific date to check for borrowed books
	 * @return a list of users who borrowed books on the specified date
	 */
	@GetMapping("/users/borrowedOn/{date}")
	public List<User> getUsersWhoBorrowedOnDate(
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		return libraryService.getUsersWhoBorrowedOnDate(date);
	}

	/**
	 * Retrieves a list of books borrowed by a specific user within a specified date
	 * range.
	 *
	 * @param user      the name of the user
	 * @param startDate the start date of the borrowing period
	 * @param endDate   the end date of the borrowing period
	 * @return a list of books borrowed by the user within the date range
	 */
	@GetMapping("/books/borrowedByUser")
	public List<Book> getBooksBorrowedByUserDateRange(@RequestParam String user,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
		return libraryService.getBooksBorrowedByUserDateRange(user, startDate, endDate);
	}

	@GetMapping("/books/available")
	public List<Book> getAvailableBooksBorrowed() {
		return libraryService.getAvailableBooksBorrowed();
	}
}
