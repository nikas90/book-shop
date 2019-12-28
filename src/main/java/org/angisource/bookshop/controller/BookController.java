package org.angisource.bookshop.controller;

import org.angisource.bookshop.entity.Book;
import org.angisource.bookshop.repository.BookRepository;
import org.angisource.bookshop.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(path = "/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public String helloWorld(){
        return "aaa";
    }

    @GetMapping(path = "/all")
    public @ResponseBody List<Book> getAllBooks() {
        // This returns a JSON or XML with the users
        return bookService.findAll();
    }

    @PostMapping(path = "/save")
    public ResponseEntity<Book> create(@RequestBody Book book) throws URISyntaxException {
        Book createdBook = bookService.create(book);
        if (createdBook == null) {
            return ResponseEntity.notFound().build();
        } else {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdBook.getId())
                    .toUri();

            return ResponseEntity.created(uri)
                    .body(createdBook);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
