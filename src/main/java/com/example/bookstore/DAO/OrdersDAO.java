package com.example.bookstore.DAO;
import com.example.bookstore.models.Orders;
import com.example.bookstore.models.Users;
import com.example.bookstore.models.Books;
import java.util.List;

public interface OrdersDAO extends CommonDAO<Orders, Long> {
    List<Orders> getAllUserOrders(Long UserId);
    void changeStatus(Orders OrderToChange, String NewStatus);
    void addNewOrder(Users User, Books Book, Integer BookNumber, String DeliveryPlace);
}