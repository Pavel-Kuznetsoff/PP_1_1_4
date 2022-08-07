package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory factory = Util.getSessionFactory();
    private Transaction transaction = null;

    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        String createTableQuery = """
                CREATE TABLE IF NOT EXISTS user (
                id BIGINT NOT NULL AUTO_INCREMENT, 
                name VARCHAR(50) NOT NULL,
                last_name VARCHAR(50) NOT NULL , 
                age INT NOT NULL,
                PRIMARY KEY (id))
                """;
        Session session = factory.getCurrentSession();
        transaction = session.beginTransaction();
        session.createSQLQuery(createTableQuery)
                .executeUpdate();
        transaction.commit();
    }

    @Override
    public void dropUsersTable() {
        String dropTableQuery = """
                 DROP TABLE IF EXISTS user
                """;
        Session session = factory.getCurrentSession();
        transaction = session.beginTransaction();
        session.createSQLQuery(dropTableQuery)
                .executeUpdate();
        transaction.commit();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try (Session session = factory.getCurrentSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = factory.getCurrentSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        String getUsersQuery = """
                SELECT * FROM user
                """;
        List<User> users = new ArrayList<>();

        try (Session session = factory.getCurrentSession()) {
            transaction = session.beginTransaction();
            List<?> rawList = session.createSQLQuery(getUsersQuery)
                    .addEntity(User.class).getResultList();
            rawList.forEach(obj -> users.add((User) obj));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        String clearTableQuery = """
                TRUNCATE user
                """;
        try (Session session = factory.getCurrentSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(clearTableQuery)
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
