package com.smithies.analoguedataexporter.repositories;

import com.smithies.analoguedataexporter.entities.Channel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepository extends CrudRepository<Channel, Integer> {
}
