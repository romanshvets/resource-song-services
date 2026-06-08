package com.romanshvets.song.repository;

import com.romanshvets.song.repository.model.SongEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<SongEntity, Long> {

    @Transactional
    List<SongEntity> deleteByIdIn(Collection<Long> ids);

}
