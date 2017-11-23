package com.smithies.analoguedataexporter.repositories;

import com.smithies.analoguedataexporter.entities.AnalogueEvent;
import com.smithies.analoguedataexporter.entities.Channel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnalogueEventRepository extends CrudRepository<AnalogueEvent, Integer> {

    AnalogueEvent findByChannel_IdAndDate(Integer id, long date);
}
