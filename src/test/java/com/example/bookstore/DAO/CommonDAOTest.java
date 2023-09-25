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
public class CommonDAOTest {

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
        UsersDAO.deleteById(11L);
        Users del_user = UsersDAO.getById(11L);
        assertNull(del_user);
    }

    @BeforeEach
    void beforeEach() {
    }

    @BeforeAll
    @AfterEach
    void annihilation() {

    }
}
