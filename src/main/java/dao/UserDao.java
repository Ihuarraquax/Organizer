package dao;

import entities.User;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * DataAccessObject, CRUD do obiektów User w bazie danych
 */


public class UserDao implements Dao<User> {

    private EntityManager entityManager;

    public UserDao(EntityManagerFactory entityManagerFactory) {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    public Optional<User> get(long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public List<User> getAll() {
        Query query = entityManager.createQuery("SELECT u FROM User u");
        return query.getResultList();
    }

    @Override
    public void save(User user) {
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        entityManager.refresh(user);
    }

    @Override
    public void update(User user, String[] params) {
        user.setFirstName(Objects.requireNonNull(params[0], "First name cannot be null"));
        user.setLastName(Objects.requireNonNull(params[1], "Last name cannot be null"));
        user.setPassword(Objects.requireNonNull(params[2], "Password name cannot be null"));

        executeInsideTransaction(entityManager -> entityManager.merge(user));
    }

    @Override
    public void delete(User user) {
        executeInsideTransaction(entityManager -> entityManager.remove(user));
    }

    private void executeInsideTransaction(Consumer<EntityManager> action) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            action.accept(entityManager);
            tx.commit();
        }
        catch (RuntimeException e) {
            tx.rollback();
            throw e;
        }
    }
}
