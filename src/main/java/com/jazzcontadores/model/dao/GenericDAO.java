/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.model.dao;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Venegas
 */
public interface GenericDAO<T, ID extends Serializable> {

    T findById(ID id);

    List<T> findAll();
    
    //List<T> findByExample(T exampleInstance, String... excludeProperty); 

    T makePersistent(T entity);

    void makeTransient(T entity);

    void flush();

    void clear();
}
