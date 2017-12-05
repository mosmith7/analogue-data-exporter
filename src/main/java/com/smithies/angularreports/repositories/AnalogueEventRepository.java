package com.smithies.angularreports.repositories;

import com.smithies.angularreports.entities.AnalogueEvent;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.QueryHint;
import java.util.stream.Stream;

import static org.hibernate.jpa.QueryHints.HINT_FETCH_SIZE;

public interface AnalogueEventRepository extends CrudRepository<AnalogueEvent, Long> {

    @QueryHints(value = @QueryHint(name = HINT_FETCH_SIZE, value = "" + Integer.MIN_VALUE))
    Stream<AnalogueEvent> findByChannel_IdAndDateBetween(Integer id, long dateFrom, long dateTo);

    Long countByChannel_IdAndDateBetween(Integer id, long dateFrom, long dateTo);
}
