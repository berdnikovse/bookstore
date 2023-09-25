package com.example.bookstore.DAO.impl;

import com.example.bookstore.DAO.OrdersDAO;
import com.example.bookstore.models.Orders;
import com.example.bookstore.models.Users;
import com.example.bookstore.models.Books;
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
    public void addNewOrder(Users User, Books Book, Integer BookNumber,
                            String DeliveryPlace) {
        save(new Orders(null, User, Book, BookNumber, DeliveryPlace, "в обработке"));
    }
}
