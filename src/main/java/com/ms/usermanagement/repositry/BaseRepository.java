package com.ms.usermanagement.repositry;

import com.ms.usermanagement.model.BaseModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.UUID;

@NoRepositoryBean
public interface BaseRepository<T extends BaseModel> extends JpaRepository<T, UUID> {
    List<T> findAllByDeleted(Boolean deleted);
    Page<T> findAllByDeleted(Boolean deleted, Pageable pageReguest);
    T findByName(String name);
    List<T> findByNameContainingIgnoreCase(String keyword);
}