package com.romanshvets.resource.repository;

import com.romanshvets.resource.repository.model.ResourceEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<ResourceEntity, Long> {

    @Transactional
    List<ResourceEntity> deleteByIdIn(Collection<Long> ids);

}
