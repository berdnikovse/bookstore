package com.example.bookstore.DAO;

import com.example.bookstore.DAO.impl.UsersDAOImpl;
import com.example.bookstore.models.Books;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import com.example.bookstore.DAO.impl.OrdersDAOImpl;
import com.example.bookstore.DAO.impl.BooksDAOImpl;
import com.example.bookstore.models.Users;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class BooksDAOTest {

    @Autowired
    private OrdersDAO OrdersDAO = new OrdersDAOImpl();
    @Autowired
    private UsersDAO UsersDAO = new UsersDAOImpl();
    @Autowired
    private BooksDAO BooksDAO = new BooksDAOImpl();

    @Autowired
    private SessionFactory sessionFactory;


    @Test
    void testSimpleManipulations() {
        assertEquals(2, BooksDAO.getAllBooksByAuthor("Тоня Мюллер").size());
        assertEquals(1, BooksDAO.getAllBooksByGenre("Триллер").size());
        assertNull(BooksDAO.getAllBooksByGenre("Троллер"));
        assertEquals(10, BooksDAO.getAllBooksByPrice(0,10000).size());
        assertEquals(1, BooksDAO.getAllBooksByName("иллюзия").size());
    }

    @Test
    void testAddNewBook() {
        Books new_book = new Books(null, "Новая книга", "Новый автор", "мягкая", "Новый жанр", 1,1,1,"аннотация");
        BooksDAO.save(new_book);
        assertFalse(BooksDAO.addBookCopies(UsersDAO.getById(2L), BooksDAO.getById(11L), 1));
        assertTrue(BooksDAO.addBookCopies(UsersDAO.getById(1L), BooksDAO.getById(11L), 1));
        assertEquals(2, BooksDAO.getById(11L).getBookNumber());
        assertEquals(1, BooksDAO.getById(11L).getBookPrice());
        BooksDAO.deleteById(11L);
    }


    @BeforeEach
    void beforeEach() {
    }

    @BeforeAll
    @AfterEach
    void annihilation() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("ALTER SEQUENCE books_id_seq RESTART WITH 11;").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
