package com.onpier.library.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Borrowed {
	@Id
	private String borrower;
	private String book;
	private LocalDate borrowedFrom;
	private LocalDate borrowedTo;

	public String getBorrower() {
		return borrower;
	}

	public void setBorrower(String borrower) {
		this.borrower = borrower;
	}

	public String getBook() {
		return book;
	}

	public void setBook(String book) {
		this.book = book;
	}

	public LocalDate getBorrowedFrom() {
		return borrowedFrom;
	}

	public void setBorrowedFrom(LocalDate borrowedFrom) {
		this.borrowedFrom = borrowedFrom;
	}

	public LocalDate getBorrowedTo() {
		return borrowedTo;
	}

	public void setBorrowedTo(LocalDate borrowedTo) {
		this.borrowedTo = borrowedTo;
	}

}
