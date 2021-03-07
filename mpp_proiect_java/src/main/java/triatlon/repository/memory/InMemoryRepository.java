package triatlon.repository.memory;

import triatlon.domain.Entity;
import triatlon.domain.validators.Validator;
import triatlon.repository.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class InMemoryRepository<ID, E extends Entity<ID>> implements Repository<ID,E> {

    private Validator<E> validator;
    Map<ID,E> entities;

    public InMemoryRepository(Validator<E> validator) {
        this.validator = validator;
        entities=new HashMap<ID,E>();
    }


    @Override
    public Optional<E> findOne(ID id) {
        if(id == null)
            throw new IllegalArgumentException("Id nu poate fi null");
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public Iterable<E> findAll() {
        Set<E> allEntities = entities.entrySet().stream().map((entry -> entry.getValue())).collect(Collectors.toSet());
        return allEntities;

    }

    @Override
    public Optional<E> save(E entity)  {
        if(entity == null){
            throw new IllegalArgumentException("Entitatea nu poate fi nula");
        }
        validator.validate(entity);
        return Optional.ofNullable(entities.putIfAbsent(entity.getId(),entity));
    }

    @Override
    public Optional<E> delete(ID id) {
        if(id == null){
            throw new IllegalArgumentException("Id-ul nu poate fi null!");
        }
        return Optional.ofNullable(entities.remove(id));
    }

    @Override
    public Optional<E> update(E entity) {
        if(entity == null){
            throw new IllegalArgumentException("Id-ul nu poate fi null!");
        }
        validator.validate(entity);
        return Optional.ofNullable(entities.computeIfPresent(entity.getId(),(k,v) ->entity));
    }

}