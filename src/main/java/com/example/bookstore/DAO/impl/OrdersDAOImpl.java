package com.example.bookstore.DAO.impl;

import com.example.bookstore.DAO.BooksDAO;
import com.example.bookstore.DAO.OrdersDAO;
import com.example.bookstore.models.Orders;
import com.example.bookstore.models.Users;
import com.example.bookstore.models.Books;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class OrdersDAOImpl extends CommonDAOImpl<Orders, Long> implements OrdersDAO {
    public OrdersDAOImpl(){
        super(Orders.class);
    }

    @Override
    public List<Orders> getAllUserOrders(Long UserId) {
        List<Orders> ret = new ArrayList<>();
        for (Orders order : getAll()) {
            if (Objects.equals(order.getUser_id().getId(), UserId)) {
                ret.add(order);
            }
        }
        return ret;
    }

    @Override
    public void changeStatus(Orders OrderToChange, String NewStatus) {
        OrderToChange.setStatus(NewStatus);
        save(OrderToChange);
    }

    @Override
    public Integer getOrderPrice(Orders Order) {
        return Order.getBook_number() * Order.getBook_id().getBookPrice();
    }

    @Override
    public Long addNewOrder(Users User, Books Book, Integer BookNumber,
                            String DeliveryPlace) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            if (Book.getBookNumber() < BookNumber) {
                return 0L;
            }
            Orders new_order = new Orders(null, User, Book, BookNumber, DeliveryPlace, "в обработке");
            /*Query query = session.createQuery("UPDATE Books SET book_number = :gotNumber WHERE id = :gotId");
            query.setParameter("gotNumber", Book.getBookNumber() - BookNumber);
            query.setParameter("gotId", Book.getId());*/
            Book.addCopies(BookNumber);
            session.saveOrUpdate(Book);
            Long new_id = (Long) session.save(new_order);
            session.getTransaction().commit();
            return new_id;
        }
    }
}
