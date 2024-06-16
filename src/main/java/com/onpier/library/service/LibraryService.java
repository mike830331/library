package com.onpier.library.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onpier.library.model.Book;
import com.onpier.library.model.Borrowed;
import com.onpier.library.model.User;
import com.onpier.library.repo.DataRepository;
import com.opencsv.exceptions.CsvException;

import jakarta.annotation.PostConstruct;

@Service
public class LibraryService {

	@Autowired
	DataRepository dataRepository;

	private List<User> users;
	private List<Book> books;
	private List<Borrowed> borroweds;

	@PostConstruct
	public void loadData() throws IOException, CsvException {
		users = dataRepository.loadusers();
		books = dataRepository.loadBooks();
		borroweds = dataRepository.loadBorroweds();
	}

	public List<User> getUsersWhoBorrowedBooks() {
		// Filter users who have borrowed books
		return users.stream()
				.filter(user -> borroweds.stream()
						.anyMatch(b -> b.getBorrower().equals(user.getName() + "," + user.getFirstName())))
				.collect(Collectors.toList());
		// users filter the borrower list in borroweds
	}

	public List<User> getNonTerminatedUsersWithoutBorrowedBooks() {
		// Filter users who are non-terminated (memberTo is null) and do not have any
		// borrowed books currently
		LocalDate currentDate = LocalDate.now();
		return users.stream().filter(user -> user.getMemberTo() == null)
				.filter(user -> borroweds.stream()
						.noneMatch(borrowed -> borrowed.getBorrower().equals(user.getName() + "," + user.getFirstName())
								&& borrowed.getBorrowedFrom().isBefore(currentDate)
								&& borrowed.getBorrowedTo().isAfter(currentDate)))
				.collect(Collectors.toList());
	}

	public List<User> getUsersWhoBorrowedOnDate(LocalDate date) {
		// Get a list of users who borrowed books on a specific date
		return users.stream()
				.filter(user -> borroweds.stream()
						.anyMatch(b -> b.getBorrower().equals(user.getName() + "," + user.getFirstName())
								&& !b.getBorrowedFrom().isAfter(date) && !b.getBorrowedTo().isBefore(date)))
				.collect(Collectors.toList());
	}

	public List<Book> getBooksBorrowedByUserDateRange(String user, LocalDate startDate, LocalDate endDate) {
		// Filter borrowed records for the specified user and date range
		List<String> borrowedBooks = borroweds.stream().filter(borrowed -> user.equals(borrowed.getBorrower())
				&& (borrowed.getBorrowedFrom().isAfter(startDate) || borrowed.getBorrowedFrom().isEqual(startDate))
				&& (borrowed.getBorrowedTo().isBefore(endDate) || borrowed.getBorrowedFrom().isEqual(endDate)))
				.map(Borrowed::getBook).distinct().collect(Collectors.toList());

		// Filter books that have been borrowed by the user within the date range
		return books.stream().filter(book -> borrowedBooks.contains(book.getTitle())).collect(Collectors.toList());
	}

	public List<Book> getAvailableBooksBorrowed() {
		// Get a list of books that are currently available (not borrowed)
		// Exclude books that are still borrowed
		LocalDate currentDate = LocalDate.now();
		return books.stream()
				.filter(book -> borroweds.stream()
						.noneMatch(b -> b.getBook().equals(book.getTitle()) && b.getBorrowedTo().isAfter(currentDate)))
				.collect(Collectors.toList());
	}

}
