package com.smithies.analoguedataexporter.repositories;

import com.smithies.analoguedataexporter.entities.Interlocking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterlockingRepository extends CrudRepository<Interlocking, Short> {
}
