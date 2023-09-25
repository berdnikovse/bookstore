package com.example.bookstore.DAO;

import com.example.bookstore.models.CommonEntity;

import java.util.Collection;

public interface CommonDAO<T extends CommonEntity<ID>, ID> {
    T getById(ID id);

    Collection<T> getAll();

    void save(T entity);

    void deleteById(ID id);

}