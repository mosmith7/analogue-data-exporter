package com.smithies.analoguedataexporter.repositories;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.smithies.analoguedataexporter.config.UnitTest;
import com.smithies.analoguedataexporter.entities.AnalogueEvent;
import org.junit.Test;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@DatabaseSetup(value = {"/interlocking_data.xml",
                        "/channel_data.xml",
                        "/analogue_event_data.xml"
                        }, type = DatabaseOperation.INSERT)
@DatabaseTearDown("/empty_data.xml")
public class AnalogueEventRepositoryTest extends UnitTest {

    @Autowired
    private EventAnalogueRepository repo;

    @Test
    public void findEventById() throws Exception {
        AnalogueEvent event = repo.findOne(1);
        assertNotNull(event);
        assertEquals(new Integer(1), event.getId());
    }
}
