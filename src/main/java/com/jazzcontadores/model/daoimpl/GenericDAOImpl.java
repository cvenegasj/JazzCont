/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.model.daoimpl;

import com.jazzcontadores.model.dao.GenericDAO;
import com.jazzcontadores.util.HibernateUtil;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Venegas
 */
public abstract class GenericDAOImpl<T, ID extends Serializable> implements GenericDAO<T, ID> {

    private Class<T> persistentClass;
    private Session session;

    public GenericDAOImpl() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    public void setSession(Session s) {
        this.session = s;
    }

    protected Session getSession() {
        if (session == null) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }

        return session;
    }

    public Class<T> getPersistentClass() {
        return persistentClass;
    }

    @Override
    public T findById(ID id) {
        T entity;

        entity = (T) getSession().load(getPersistentClass(), id);

        return entity;
    }

    @Override
    public List<T> findAll() {
        List<T> entities;

        entities = (List<T>) getSession().createQuery("from " + getPersistentClass().getSimpleName() + " x").list();

        return entities;
    }

    @Override
    public T makePersistent(T entity) {
        getSession().saveOrUpdate(entity);
        return entity;
    }

    @Override
    public void makeTransient(T entity) {
        getSession().delete(entity);
    }

    @Override
    public void flush() {
        getSession().flush();
    }

    @Override
    public void clear() {
        getSession().clear();
    }
    /* En caso de usar Criteria API
     protected List<T> findByCriteria(Criterion... criterion) {
     Criteria crit = getSession().createCriteria(getPersistentClass());

     for (Criterion c : criterion) {
     crit.add(c);
     }

     return crit.list();
     }*/
}
