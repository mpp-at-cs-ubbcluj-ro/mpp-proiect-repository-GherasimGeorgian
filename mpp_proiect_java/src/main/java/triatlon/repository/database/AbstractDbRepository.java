package triatlon.repository.database;

import triatlon.domain.Entity;
import triatlon.domain.validators.Validator;
import triatlon.repository.memory.InMemoryRepository;

import java.sql.*;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractDbRepository <ID, E extends Entity<ID>> extends InMemoryRepository<ID,E> {

    String url;
    String username;
    String password;
    public AbstractDbRepository(String url, String username, String password, Validator<E> validator) {
        super(validator);
        this.url=url;
        this.username=username;
        this.password=password;
        loadData();
    }
    public abstract E extractEntity(ResultSet rs);

    public abstract String abstractSelect();

    public abstract String abstractInsert();

    public abstract String abstractDelete(ID id);

    public abstract void abstractInsertParameters(PreparedStatement stmt, E entity);

    public abstract String abstractUpdate();

    public abstract void abstractUpdateParameters(PreparedStatement stmt,E entity);

    private void loadData() {

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(abstractSelect());
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                E entity = extractEntity(resultSet);
                super.save(entity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Iterable<E> findAll() {
        loadData();
        return super.findAll();
    }

    @Override
    public Optional<E> save(E entity){
        Optional<E> e=super.save(entity);
        if (e.isEmpty())
        {
            try (Connection connection = DriverManager.getConnection(url, username, password);
                 PreparedStatement stmt = connection
                         .prepareStatement(abstractInsert())) {
                abstractInsertParameters(stmt,entity);

                stmt.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return e;

    }




    @Override
    public Optional<E> delete(ID id){
        Optional<E> optional = super.delete(id);
        if(optional.isPresent()) {
            try (Connection connection = DriverManager.getConnection(url, username, password);
                 PreparedStatement stmt = connection
                         .prepareStatement(abstractDelete(id))) {
                int deleted = stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return optional;
        }
        else {
            return Optional.empty();
        }
    }



    @Override
    public Optional<E> update(E entity) {
        Optional<E> optional = super.update(entity);
        if(optional.isPresent()) {
            try (Connection connection = DriverManager.getConnection(url, username, password);
                 PreparedStatement stmt = connection
                         .prepareStatement(abstractUpdate())) {

                abstractUpdateParameters(stmt,entity);
                int update = stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return optional;
        }
        else {
            return Optional.empty();
        }
    }
}
