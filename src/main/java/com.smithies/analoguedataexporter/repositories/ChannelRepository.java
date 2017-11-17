package com.smithies.analoguedataexporter.repositories;

import com.smithies.analoguedataexporter.entities.Channel;
import org.springframework.data.repository.CrudRepository;

public interface ChannelRepository extends CrudRepository<Channel, Integer> {
}
