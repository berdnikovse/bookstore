package com.example.bookstore.DAO;
import com.example.bookstore.models.Books;
import com.example.bookstore.models.Users;

import java.util.List;

public interface BooksDAO extends CommonDAO<Books, Long> {
    List<Books> getAllBooksByAuthor(String AuthorName);
    List<Books> getAllBooksByGenre(String GenreName);
    List<Books> getAllBooksByPrice(Integer PriceLow, Integer PriceUp);
    List<Books> getAllBooksByName(String Name);
    boolean addBookCopies(Users UserWhoAdd, Books BookToAdd, Integer BookNumber);
}