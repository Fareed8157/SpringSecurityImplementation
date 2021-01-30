package com.ms.usermanagement.service.impl;

import com.ms.usermanagement.model.BaseModel;

import java.util.List;
import java.util.UUID;

public interface BaseService<T extends BaseModel> {
    public T insert(T item);
    public List<T> searchByName(String keyword);
    public List<T> saveAll(Iterable<T> list);
    public List<T> getAll();
    public T findById(UUID id);
    public T delete(UUID id);
    public T deleteSoft(UUID id);
    public T update(T item);
    public T findByName(String name);
    boolean exists(String name);
    List<T> findByNameContainingIgnoreCase(String keyword);
    List<T> findAllByDeleted(Boolean deleted);
}
