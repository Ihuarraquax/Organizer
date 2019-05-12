package dao;

import entities.Event;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public class EventDao implements Dao<Event> {

    private EntityManager entityManager;

    public EventDao(EntityManagerFactory entityManagerFactory) {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    public Optional<Event> get(long id) {
        return Optional.ofNullable(entityManager.find(Event.class, id));
    }

    @Override
    public List<Event> getAll() {
        entityManager.clear();
        Query query = entityManager.createQuery("SELECT e FROM Event e");
        return query.getResultList();
    }

    @Override
    public void save(Event event) {
        executeInsideTransaction(entityManager -> entityManager.persist(event));
    }

    @Override
    public void update(Event event, String[] params) {
        event.setName(Objects.requireNonNull(params[0], "Name cannot be null"));
        event.setStartDate(Objects.requireNonNull(LocalDateTime.parse(params[1]), "Start date cannot be null"));
        event.setEndDate(Objects.requireNonNull(LocalDateTime.parse(params[2]), "End date cannot be null"));
        executeInsideTransaction(entityManager -> entityManager.merge(event));
    }

    @Override
    public void delete(Event event) {
        executeInsideTransaction(entityManager -> entityManager.remove(event));
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
