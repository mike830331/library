package com.onpier.library.repo;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.onpier.library.model.Book;
import com.onpier.library.model.Borrowed;
import com.onpier.library.model.User;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

@Repository
public class DataRepository {
	private List<User> users = new ArrayList<>();
	private List<Book> books = new ArrayList<>();
	private List<Borrowed> borroweds = new ArrayList<>();

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

	public List<User> loadusers() throws IOException, CsvException {
		try (CSVReader reader = new CSVReader(
				new InputStreamReader(getClass().getResourceAsStream("/users.csv"), StandardCharsets.UTF_8))) {
			List<String[]> lines = reader.readAll();
			for (int i = 1; i < lines.size(); i++) {
				String[] line = lines.get(i);
				User user = new User();
				user.setName(line[0]);
				user.setFirstName(line[1]);
				user.setMemberSince(line[2].isEmpty() ? null : LocalDate.parse(line[2], formatter));
				user.setMemberTo(line[3].isEmpty() ? null : LocalDate.parse(line[3], formatter));
				user.setGender(line[4]);
				users.add(user);
			}
		}
		return users;
	}

	public List<Book> loadBooks() throws IOException, CsvException {
		try (CSVReader reader = new CSVReader(
				new InputStreamReader(getClass().getResourceAsStream("/book.csv"), StandardCharsets.UTF_8))) {
			List<String[]> lines = reader.readAll();
			for (int i = 1; i < lines.size(); i++) {
				String[] line = lines.get(i);
				Book book = new Book();
				book.setTitle(line[0]);
				book.setAuthor(line[1]);
				book.setGenre(line[2]);
				book.setPublisher(line[3]);
				books.add(book);
			}
		}
		return books;
	}

	public List<Borrowed> loadBorroweds() throws IOException, CsvException {
		try (CSVReader reader = new CSVReader(
				new InputStreamReader(getClass().getResourceAsStream("/book.csv"), StandardCharsets.UTF_8))) {
			List<String[]> lines = reader.readAll();
			for (int i = 1; i < lines.size(); i++) {
				String[] line = lines.get(i);
				Borrowed borrowed = new Borrowed();
				borrowed.setBorrower(line[0]);
				borrowed.setBook(line[1]);
				borrowed.setBorrowedFrom(line[2].isEmpty() ? null : LocalDate.parse(line[2], formatter));
				borrowed.setBorrowedTo(line[3].isEmpty() ? null : LocalDate.parse(line[3], formatter));
				borroweds.add(borrowed);
			}
		}
		return borroweds;
	}

	public List<User> getUsers() {
		return users;
	}

	public List<Book> getBooks() {
		return books;
	}

	public List<Borrowed> getBorroweds() {
		return borroweds;
	}

}
