package org.angisource.bookshop.service;

import org.angisource.bookshop.entity.Book;

import java.util.List;

public interface IBookService {

    List<Book> findAll();

    Book create(Book book);

    void delete(Long id);

    void delete(Book book);

    Book findByTitle(String title);
}
