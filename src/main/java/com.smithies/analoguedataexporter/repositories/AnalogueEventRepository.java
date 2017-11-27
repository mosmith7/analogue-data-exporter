package com.smithies.analoguedataexporter.repositories;

import com.smithies.analoguedataexporter.entities.AnalogueEvent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnalogueEventRepository extends CrudRepository<AnalogueEvent, Long> {

    List<AnalogueEvent> findByChannel_IdAndDateBetween(Integer id, long dateFrom, long dateTo);

    Long countByChannel_IdAndDateBetween(Integer id, long dateFrom, long dateTo);
}
