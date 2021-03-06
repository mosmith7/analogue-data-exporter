package com.smithies.angularreports.repositories;

import com.smithies.angularreports.entities.Interlocking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterlockingRepository extends CrudRepository<Interlocking, Short> {

    Interlocking findInterlockingByNameEquals(String name);

    List<Interlocking> findInterlockingByNameStartsWith(String name);
}
