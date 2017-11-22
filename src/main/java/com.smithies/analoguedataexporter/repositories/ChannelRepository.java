package com.smithies.analoguedataexporter.repositories;

import com.smithies.analoguedataexporter.entities.Channel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChannelRepository extends CrudRepository<Channel, Integer> {

    // Spring is clever enough to create this method as long as it is named correctly
    // findBy{field_name}_{id_name}
    List<Channel> findByInterlocking_Id(Short id);
}
