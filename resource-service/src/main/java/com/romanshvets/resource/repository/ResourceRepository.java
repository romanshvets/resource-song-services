package com.romanshvets.resource.repository;

import com.romanshvets.resource.model.ResourceDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends JpaRepository<ResourceDto, Long> {

//    @Transactional
//    List<SongDto> deleteByIdIn(Collection<Long> ids);

}
