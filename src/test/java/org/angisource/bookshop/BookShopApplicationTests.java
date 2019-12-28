package org.angisource.bookshop;

import org.angisource.bookshop.entity.Book;
import org.angisource.bookshop.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
class BookShopApplicationTests {

    private final String BOOK_NAME = "Long Life";

    @Autowired
    private BookRepository bookRepository;

    @Test
    void contextLoads() {
    }

    @Test
    public void findBookWithIDEqualOne() {
        Optional<Book> foundBook = bookRepository.findById(1L);
        assertThat(foundBook.isPresent()).isEqualTo(true);
    }

    @Test
    public void givenUserInDBWhenFindOneByNameThenReturnOptionalWithUser() {
        Book book = new Book();
        book.setTitle(BOOK_NAME);
        bookRepository.save(book);

        Optional<Book> foundUser = bookRepository.findOneByName(BOOK_NAME);

        assertThat(foundUser.isPresent()).isEqualTo(true);

        assertThat(foundUser
                .get()
                .getTitle()).isEqualTo(BOOK_NAME);
    }

}
