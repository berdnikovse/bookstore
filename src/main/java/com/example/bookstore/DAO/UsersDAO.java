package com.example.bookstore.DAO;
import com.example.bookstore.models.Users;

public interface UsersDAO extends CommonDAO<Users, Long> {
    boolean checkHashPassword(String userNickname, String password);
    boolean addNewUser(String userNickname, String password);
}