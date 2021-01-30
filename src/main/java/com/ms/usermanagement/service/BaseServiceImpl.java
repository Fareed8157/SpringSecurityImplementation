package com.ms.usermanagement.service;

import com.ms.usermanagement.model.BaseModel;
import com.ms.usermanagement.repositry.BaseRepository;
import com.ms.usermanagement.service.impl.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BaseServiceImpl<T extends BaseModel> implements BaseService<T> {

    @Autowired
    BaseRepository<T> repository;

    @Override
    public T insert(T item) {
        try {
            return repository.saveAndFlush(item);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            String p =e.getMessage();

        }

        return repository.saveAndFlush(item);
    }

    @Override
    public List<T> searchByName(String keyword) {
        return repository.findByNameContainingIgnoreCase(keyword);
    }

    @Override
    public List<T> getAll() {
        return repository.findAllByDeleted(false);
    }

    @Override
    public T findById(UUID id) {
        Optional<T> itemOpt = repository.findById(id);
        if (itemOpt.isPresent()) {
            return itemOpt.get();
        } else {
            return null;
        }

    }

    @Override
    public T delete(UUID id) {
        Optional<T> item = repository.findById(id);
        if (item.isPresent()) {
            deleteSoft(item.get().getId());
            return item.get();
        }
        return null;
    }
    @Override
    public List<T> saveAll(Iterable<T> list) {
        return repository.saveAll(list);
    }

    @Override
    public T deleteSoft(UUID id) {
        Optional<T> item = repository.findById(id);
        if (item.isPresent() && item.get().isDeleted() == false) {
            item.get().setDeleted(true);
            repository.save(item.get());
            return item.get();
        }
        return null;
    }

    @Override
    public T update(T item) {
        return repository.saveAndFlush(item);
    }

    @Override
    public T findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public boolean exists(String name) {
        if (repository.findByName(name) != null) {
            return true;
        }
        return false;
    }

    @Override
    public List<T> findByNameContainingIgnoreCase(String keyword){
        return  repository.findByNameContainingIgnoreCase(keyword);
    }

    @Override
    public List<T> findAllByDeleted(Boolean deleted){
        return repository.findAllByDeleted(false);
    }

}
