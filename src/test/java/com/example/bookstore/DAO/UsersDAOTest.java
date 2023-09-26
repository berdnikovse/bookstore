package com.example.bookstore.DAO;

import com.example.bookstore.models.Users;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import com.example.bookstore.DAO.impl.UsersDAOImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class UsersDAOTest {

    @Autowired
    private UsersDAO UsersDAO = new UsersDAOImpl();

    @Autowired
    private SessionFactory sessionFactory;


    @Test
    void testSimpleManipulations() {
        Users new_user = new Users(null, "cardinal", "Armand Jean", "admin");
        UsersDAO.save(new_user);
        assertTrue(UsersDAO.getById(11L).checkPrivelege());
        assertEquals("Armand Jean", UsersDAO.getById(11L).getPassword());
        assertTrue(UsersDAO.checkHashPassword("cardinal", "Armand Jean"));
        assertFalse(UsersDAO.checkHashPassword("cardinal", "pwd"));
        UsersDAO.deleteById(11L);
        Users del_user = UsersDAO.getById(11L);
        assertNull(del_user);
    }

    @Test
    void testUserAdding() {
        assertTrue(UsersDAO.addNewUser("cardinal", "pwd"));
        assertEquals("pwd", UsersDAO.getById(11L).getPassword());
        assertFalse(UsersDAO.checkHashPassword("cardinal", "Armand Jean"));
        assertFalse(UsersDAO.addNewUser("cardinal", "pwd"));
        UsersDAO.deleteById(11L);
    }

    @BeforeEach
    void beforeEach() {
    }

    @BeforeAll
    @AfterEach
    void annihilation() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("ALTER SEQUENCE users_id_seq RESTART WITH 11;").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
