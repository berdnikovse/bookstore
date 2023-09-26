package com.example.bookstore.DAO;

import com.example.bookstore.DAO.impl.UsersDAOImpl;
import com.example.bookstore.DAO.BooksDAO;
import com.example.bookstore.models.Orders;
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
public class OrdersDAOTest {

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
        assertEquals(3, OrdersDAO.getAllUserOrders(2L).size());
        assertEquals(0, OrdersDAO.addNewOrder(UsersDAO.getById(2L),BooksDAO.getById(1L),
                10000, "Хвоя"));
        assertEquals(11L, OrdersDAO.addNewOrder(UsersDAO.getById(2L),BooksDAO.getById(1L),
                1, "Хвоя"));
        assertEquals(425, OrdersDAO.getOrderPrice(OrdersDAO.getById(11L)));
        OrdersDAO.changeStatus(OrdersDAO.getById(11L),"в пути");
        assertEquals("в пути", OrdersDAO.getById(11L).getStatus());
        OrdersDAO.deleteById(11L);
        BooksDAO.addBookCopies(UsersDAO.getById(1L), BooksDAO.getById(1L), 1);

    }


    @BeforeEach
    void beforeEach() {
    }

    @BeforeAll
    @AfterEach
    void annihilation() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("ALTER SEQUENCE orders_id_seq RESTART WITH 11;").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
