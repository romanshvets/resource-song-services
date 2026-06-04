package com.romanshvets.song.repository;

import com.romanshvets.song.model.SongDto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<SongDto, Long> {

    @Transactional
    List<SongDto> deleteByIdIn(Collection<Long> ids);

}
