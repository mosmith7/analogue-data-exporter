package com.smithies.analoguedataexporter.repositories;

import com.smithies.analoguedataexporter.entities.AnalogueReportParameters;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AnalogueReportParametersRepository extends CrudRepository<AnalogueReportParameters, UUID> {

    // TODO: Find all reports saved to user
//    List<AnalogueReportParameters> findByUser_Id(Short id);
}