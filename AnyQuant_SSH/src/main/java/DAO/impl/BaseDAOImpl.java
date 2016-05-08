package DAO.impl;

import DAO.BaseDAO;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

import static org.hibernate.criterion.Restrictions.*;

/**
 * @author Qiang
 * @date 16/5/4
 */
@Repository
public class BaseDAOImpl implements BaseDAO {
    /**
     * Autowired 自动装配 相当于get() set()
     */
    @Autowired
    protected SessionFactory sessionFactory;


    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }


    public Session getNewSession() {
        return sessionFactory.openSession();
    }

    public void flush() {
        getSession().flush();
    }

    public void clear() {
        getSession().clear();
    }


    public Object load(Class<?> c, String id) {
        Session session = getSession();
        return session.get(c, id);
    }

    @Override
    public Object load(Class<?> c, int id) {
        Session session = getSession();
        return session.get(c, id);
    }

    @Override
    public Object load(Class<?> c, Serializable key) {
        Session session = getSession();
        return session.get(c,key);
    }


    /**
     * 获取所有信息
     * @param c
     * @return
     */
    public List<?> getAllList(Class<?> c) {
        String hql = "from " + c.getName();
        Session session = getSession();
        return session.createQuery(hql).list();

    }

    /**
     * 获取总数量
     * @param c
     * @return
     */
    public long getTotalCount(Class<?> c) {
        Session session = getNewSession();
        String hql = "select count(*) from " + c.getName();
        Long count = (Long)session.createQuery(hql).uniqueResult();
        session.close();
        return count != null ? count : 0;
    }

    /**
     * 保存
     * @param bean
     */
    public void save(Object bean) {
        try {
            Session session = getNewSession();
            session.save(bean);
            session.flush();
            session.clear();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新
     * @param bean
     */
    public void update(Object bean) {
        Session session = getNewSession();
        session.update(bean);
        session.flush();
        session.clear();
        session.close();
    }

    /**
     * 删除
     * @param bean
     */
    public void delete(Object bean) {

        Session session = getNewSession();
        session.delete(bean);
        session.flush();
        session.clear();
        session.close();
    }

    /**
     * 根据ID删除
     * @param c 类
     * @param id ID
     */
    public void delete(Class<?> c, String id) {
        Session session = getNewSession();
        Object obj = session.get(c, id);
        session.delete(obj);
        flush();
        clear();
    }

    @Override
    public void delete(Class<?> c, int id) {
        Session session = getNewSession();
        Object obj = session.get(c, id);
        session.delete(obj);
        flush();
        clear();
    }

    /**
     * 批量删除
     * @param c 类
     * @param ids ID 集合
     */
    public void delete(Class<?> c, String[] ids) {
        for(String id : ids) {
            Object obj = getSession().get(c, id);
            if (obj != null) {
                getSession().delete(obj);
            }
        }
    }

    @Override
    public void delete(Class<?> c, int[] ids) {
        for(int id : ids) {
            Object obj = getSession().get(c, id);
            if (obj != null) {
                getSession().delete(obj);
            }
        }
    }

    @Override
    public List<?> find(String column, String value, Class<?> c) {
        Session session = getSession();
        Criteria criteria = session.createCriteria(c);
        criteria.add(eq(column, value));
        return criteria.list();
    }
}
