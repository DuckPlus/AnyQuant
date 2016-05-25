package DAO;

import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;

/**
 * The conventional interface of DAO objects
 * @author Qiang
 * @date 16/5/4
 */
public interface BaseDAO {
	/**
	 * gerCurrentSession 会自动关闭session，使用的是当前的session事务
	 * @return
	 */
	public Session getSession();

	/**
	 * openSession 需要手动关闭session 意思是打开一个新的session
	 */
	public Session getNewSession();

	/**
	 *
	 */
	public void flush();

	public void clear();


	public int executeMyHQL(String hql);
	/**
	 * load a object through its ID
	 * @param id   its primary key
	 * @return
	 */
	Object load(Class<?> c, String id);
	Object load(Class<?> c, Serializable bean);
	Object load(String hql);
	/**
	 * get all entities of this table
	 * @param c
	 * @return
     */
	public List<?> getAllList(Class<?> c);
	public List<?> getAllList(String hql);

	/**
	 * count the entites of a table
	 * @param c
	 * @return
     */
	public long getTotalCount(Class<?> c);

	/**
	 * save a object
	 * @param bean
     */
	public void save(Object bean);

	public void saveList(List<?> beans);

	/**
	 * update a object
	 * @param bean
     */
	public void update(Object bean);

	/**
	 * delete a object
	 * @param bean
     */
	public void delete(Object bean);


	/**
	 * delete a object through its id
	 * @param c
	 * @param id
     */
	public void delete(Class<?> c, String id);
	public void delete(Class<?> c, int id);


	/**
	 *
	 * @param column
	 * @param value
	 * @param c
     * @return
     */
	public List<?> find(String column, String value, Class<?> c);
}
