package org.angisource.bookshop.service;

import org.angisource.bookshop.entity.Book;

import java.util.List;
import java.util.Optional;

public interface IBookService {

    List<Book> findAll();

    Optional<Book> findById(Long id);

    Book create(Book book);

    void delete(Long id);

    void delete(Book book);

    Optional<Book> findFirstByTitle(String title);
}
