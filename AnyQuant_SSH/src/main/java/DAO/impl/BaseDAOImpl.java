package DAO.impl;

import DAO.BaseDAO;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.hibernate.criterion.Restrictions.eq;

/**
 * @author Qiang
 * @date 16/5/4
 */
@Repository
@Transactional
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

    @Override
    public int updateByHQL(String hql) {
        Session session = getSession();
        int num= session.createQuery(hql).executeUpdate();
        return num;
    }

    @Override
    public long countByHQL(String hql) {

        Session session = getSession();
        long num= (long)session.createQuery(hql).uniqueResult();
        return num;
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

    @Override
    public Object load(String hql) {
        Session session = getSession();
        return session.createQuery(hql).setMaxResults(1).uniqueResult();


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

    @Override
    public List<?> getAllList(String hql) {
        Session session = getSession();
        return session.createQuery(hql).list();
    }

    @Override
    public List<?> getAllList(String hql, int num) {
        Session session = getSession();
        return session.createQuery(hql).setMaxResults(num).list();
    }

    @Override
    public List<?> batchSingleQuery(String hql, List<String> paras) {
        List result = new ArrayList<>();

        try {
            Session session = getNewSession();
            //开始事务
            Transaction tx = session.beginTransaction();
            Query query= session.createQuery(hql);
            for (int i = 0 ; i < paras.size() ; i++ )
            {
                query.setString(0, paras.get(i));
                Object temp = query.uniqueResult();

                if(temp!=null){
                    result.add(temp);
                }

            }
            //提交事务
            tx.commit();
            //关闭事务
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    @Override
    public List<?> batchSingleQuery_Exact(String hql, List<String> paras) {
        List result = new ArrayList<>();

        try {
            Session session = getNewSession();
            //开始事务
            Transaction tx = session.beginTransaction();
            Query query= session.createQuery(hql);
            for (int i = 0 ; i < paras.size() ; i++ )
            {
                query.setString(0, paras.get(i));
                Object temp = query.uniqueResult();

                result.add(temp);

            }
            //提交事务
            tx.commit();
            //关闭事务
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public List<?> batchListQuery(String hql, List<String> paras) {
        List result = new ArrayList<>();

        try {
            Session session = getNewSession();
            //开始事务
            Transaction tx = session.beginTransaction();
            Query query= session.createQuery(hql);
            for (int i = 0 ; i < paras.size() ; i++ )
            {
                query.setString(0,paras.get(i));
                List tempList = query.list();
                if(tempList!=null){
                    result.addAll(tempList);
                }

            }
            //提交事务
            tx.commit();
            //关闭事务
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
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

    @Override
    public void saveList(List<?> beans) {
        try {
            Session session = getNewSession();
            //开始事务
            Transaction tx = session.beginTransaction();
            for (int i = 0 ; i < beans.size() ; i++ )
            {
                //在Session级别缓存User实例
                session.save(beans.get(i));
                //每当累加器是20的倍数时，将Session中的数据刷入数据库，并清空Session缓存

                if (i % 100 == 0)
                {
                    session.flush();
                    session.clear();
                    tx.commit();
                    tx = session.beginTransaction();
                }
            }
            //提交事务
            tx.commit();
            //关闭事务
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

    @Override
    public byte[] ObjectToBytes(Object ob) {

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream outputStream = new ObjectOutputStream(out);
            outputStream.writeObject(ob);
            byte [] bytes = out.toByteArray();
            outputStream.close();
            return bytes;
        } catch (Exception e) {
            // TODO: handle exception

            System.out.println( " ObjectToBlob " );
            return null ;
        }


    }

    @Override
    public Object BytesToObject(byte[] bytes) {
            try {
                Object obj = null;
                ObjectInputStream in =
                        new ObjectInputStream(new ByteArrayInputStream(bytes));
                obj = in.readObject();
                in.close();
                return obj;
            } catch (Exception e) {
                // TODO: handle exception

                System.out.println( " BlobToObject " );
                e.printStackTrace();

            }
            return null;
    }

    @Override
    public void saveObject(Object object) {

        try {
            Session session = getNewSession();
            Transaction tx =session.beginTransaction();
            session.save(object);
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
