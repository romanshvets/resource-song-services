package com.romanshvets.resource.repository;

import com.romanshvets.resource.model.ResourceDto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<ResourceDto, Long> {

    @Transactional
    List<ResourceDto> deleteByIdIn(Collection<Long> ids);

}
