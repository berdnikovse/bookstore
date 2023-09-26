package com.example.bookstore.DAO.impl;
import com.example.bookstore.DAO.BooksDAO;
import com.example.bookstore.DAO.OrdersDAO;
import com.example.bookstore.models.Orders;
import com.example.bookstore.models.Users;
import com.example.bookstore.models.Books;
import org.springframework.stereotype.Repository;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
@Repository
public class BooksDAOImpl extends CommonDAOImpl<Books, Long> implements BooksDAO {
    public BooksDAOImpl(){
        super(Books.class);
    }

    @Override
    public List<Books> getAllBooksByAuthor(String AuthorName) {
        try (Session session = sessionFactory.openSession()) {
            Query<Books> query = session.createQuery("FROM Books WHERE author LIKE :gotName", Books.class)
                    .setParameter("gotName", likeExpr(AuthorName));
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    @Override
    public List<Books> getAllBooksByGenre(String GenreName) {
        try (Session session = sessionFactory.openSession()) {
            Query<Books> query = session.createQuery("FROM Books WHERE genre LIKE :gotName", Books.class)
                    .setParameter("gotName", likeExpr(GenreName));
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    @Override
    public List<Books> getAllBooksByPrice(Integer PriceLow, Integer PriceUp) {
        try (Session session = sessionFactory.openSession()) {
            Query<Books> query = session.createQuery("FROM Books WHERE price >= :priceLow AND price <= :priceUp", Books.class);
            query.setParameter("priceLow", PriceLow);
            query.setParameter("priceUp", PriceUp);
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    @Override
    public List<Books> getAllBooksByName(String Name) {
        try (Session session = sessionFactory.openSession()) {
            Query<Books> query = session.createQuery("FROM Books WHERE name LIKE :gotName", Books.class)
                    .setParameter("gotName", likeExpr(Name));
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    @Override
    public boolean addBookCopies(Users UserWhoAdd, Books BookToAdd, Integer BookNumber) {
        if (UserWhoAdd.checkPrivelege()) {
            /*try (Session session = sessionFactory.openSession()) {
                Query query = session.createQuery("UPDATE Books SET book_number = :gotNumber WHERE id = :gotId");
                query.setParameter("gotNumber", BookToAdd.getBookNumber() + BookNumber);
                query.setParameter("gotId", BookToAdd.getId());
            }*/
            BookToAdd.addCopies(BookNumber);
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                session.saveOrUpdate(BookToAdd);
                session.getTransaction().commit();
            }
            return true;
        }
        return false;
    }


    private String likeExpr(String param) {
        return "%" + param + "%";
    }
}
