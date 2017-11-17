package com.smithies.analoguedataexporter.repositories;

import com.smithies.analoguedataexporter.entities.AnalogueEvent;
import org.springframework.data.repository.CrudRepository;

public interface EventAnalogueRepository extends CrudRepository<AnalogueEvent, Integer> {
}
