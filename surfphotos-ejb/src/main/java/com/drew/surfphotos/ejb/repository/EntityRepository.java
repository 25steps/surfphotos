package com.drew.surfphotos.ejb.repository;

import java.util.Optional;

//Т тип сущности, ID тип айдишника
public interface EntityRepository<T,ID> {

    Optional<T> findById(ID id);

    void create(T entity);

    void update(T entity);

    void delete(T entity);

    void flush();// для того чтобы синхронизироваться с базой данных

    T getProxyInstance(ID id);// для того чтобы создавать прокси объекты, чтобы налаживать связь
}
