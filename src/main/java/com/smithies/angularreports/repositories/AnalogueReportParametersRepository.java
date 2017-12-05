package com.smithies.angularreports.repositories;

import com.smithies.angularreports.entities.AnalogueReportParameters;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnalogueReportParametersRepository extends CrudRepository<AnalogueReportParameters, Integer> {

    Optional<AnalogueReportParameters> findById(Integer id);

    // TODO: Find all reports saved to user
//    List<AnalogueReportParameters> findByUser_Id(Short id);

    List<AnalogueReportParameters> findByDateCreatedBefore(long date);
}
