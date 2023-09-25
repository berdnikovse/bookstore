package com.example.bookstore.DAO.impl;

import com.example.bookstore.models.Users;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.example.bookstore.DAO.UsersDAO;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class UsersDAOImpl extends CommonDAOImpl<Users, Long> implements UsersDAO {

    public UsersDAOImpl(){
        super(Users.class);
    }

    @Override
    public boolean checkHashPassword(String userNickname, String password) {
        try (Session session = sessionFactory.openSession()) {
            Query<Users> query = session.createQuery("FROM Users WHERE nickname = :gotName", Users.class)
                    .setParameter("gotName", userNickname);
            if (query.getResultList().isEmpty())
                return false;
            Users userToCheck = query.getResultList().get(0);

            return userToCheck.getPassword().hashCode() == password.hashCode();
        }
    }

}