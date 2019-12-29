package org.angisource.bookshop.controller;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.angisource.bookshop.entity.Book;
import org.angisource.bookshop.exception.NotFoundException;
import org.angisource.bookshop.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(path = "/book")
@RequiredArgsConstructor
@Setter
public class BookController {

    private static Logger LOGGER = LoggerFactory.getLogger(BookController.class);
    @Autowired
    private final BookService bookService;

    //@GetMapping(path = "/all")
    //@RequestMapping(value = "/all", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = {RequestMethod.GET})
    @RequestMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE, method = {RequestMethod.GET})
    @ResponseBody
    public ResponseEntity<List<Book>> getAll() {
        LOGGER.debug("************** getAllBooks **************");
        List<Book> books = bookService.findAll();
        return ResponseEntity.ok().body(books);
    }

    @RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = {RequestMethod.GET})
    public Book getById(@PathVariable Long id) {
        LOGGER.debug("************** Get Book with id: " + id + " **************");
        return bookService.findById(id).orElseThrow(() -> new NotFoundException("Book[id: " + id + "] not founded"));
    }

    @PostMapping(path = "/save")
    public ResponseEntity<Book> create(@RequestBody Book book) throws URISyntaxException {
        LOGGER.debug("**************create**************");
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
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        LOGGER.debug("**************delete**************");
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
