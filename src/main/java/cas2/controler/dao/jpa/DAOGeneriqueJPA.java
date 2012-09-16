package cas2.controler.dao.jpa;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;

import cas2.controler.CExc;
import cas2.controler.ErrorMessage;
import cas2.controler.dao.DAO;

public abstract class DAOGeneriqueJPA<T, ID extends Serializable>
                                                    implements DAO<T, ID> {

    protected EntityManager entityManager;
    
    private Class<T> entityClass;
    private String entityName = "";
    
    @SuppressWarnings("unchecked")
    public DAOGeneriqueJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.entityClass = (Class<T>) ((ParameterizedType) getClass().
                                                getGenericSuperclass()).
                                                getActualTypeArguments()[0];
        
        if (entityName.equals("")) {
            entityName = entityClass.getSimpleName();
        }
    }

    public void delete(T obj) throws CExc {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new CExc(entityName + " deletion",
                           ErrorMessage.getSQLMessage(e));
        }
    }

    public List<T> findAll() {
        return entityManager.createQuery(
                "SELECT t FROM " + entityName + " t", entityClass).
                    getResultList();
    }

    public T getById(ID id) {
        return entityManager.find(entityClass, id);
    }

    public T create(T obj) throws CExc {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(obj);
            entityManager.getTransaction().commit();
            return obj;
        } catch (Exception e) {
            throw new CExc(entityName + " creation",
                           ErrorMessage.getSQLMessage(e));
        }
    }

    public void update(T obj) throws CExc {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new CExc(entityName + " update",
                           ErrorMessage.getSQLMessage(e));
        }
    }

}
