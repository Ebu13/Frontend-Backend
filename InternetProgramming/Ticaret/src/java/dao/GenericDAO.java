package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Transactional
public abstract class GenericDAO<T> {

    private static final Logger LOGGER = Logger.getLogger(GenericDAO.class.getName());

    @PersistenceContext
    protected EntityManager entityManager;

    private Class<T> entityClass;

    @SuppressWarnings("unchecked")
    public GenericDAO() {
        this.entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public T getById(int id) {
        return entityManager.find(entityClass, id);
    }

    public List<T> getAll() {
        return entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass)
                .getResultList();
    }

    public void save(T entity) {
        try {
            entityManager.persist(entity);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while saving entity", ex);
        }
    }

    public void update(T entity) {
        try {
            entityManager.merge(entity);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error occurred while updating entity", ex);
        }
    }

    public void delete(int id) {
        T entity = getById(id);
        if (entity != null) {
            try {
                entityManager.remove(entity);
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, "Error occurred while deleting entity", ex);
            }
        }
    }

}
