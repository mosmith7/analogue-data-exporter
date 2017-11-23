package com.smithies.analoguedataexporter.repositories;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.smithies.analoguedataexporter.config.UnitTest;
import com.smithies.analoguedataexporter.entities.AnalogueEvent;
import com.smithies.analoguedataexporter.entities.Channel;
import com.smithies.analoguedataexporter.entities.Interlocking;
import org.junit.Test;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.springframework.beans.factory.annotation.Autowired;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@DatabaseSetup(value = {"/interlocking_data.xml",
                        "/channel_data.xml",
                        "/analogue_event_data.xml"
                        }, type = DatabaseOperation.INSERT)
@DatabaseTearDown("/empty_data.xml")
public class AnalogueEventRepositoryTest extends UnitTest {

    @Autowired
    private AnalogueEventRepository repo;

    @Test
    public void testFindEventById() throws Exception {
        AnalogueEvent event = repo.findOne(1);
        assertNotNull(event);
        assertEquals(new Integer(1), event.getId());
    }

    @Test
    public void testFourEventsExist() throws Exception {
        assertEquals(4, repo.count());
    }

    @Test
    public void testDeleteEvent() {
        repo.delete(1);
        assertNull(repo.findOne(1));
    }

    @Test
    public void testEventContainsChannel() {
        AnalogueEvent event = repo.findOne(2);
        Channel channel = event.getChannel();
        assertNotNull(channel);
        assertEquals(new Integer(2), channel.getId());
        assertEquals("channel2", channel.getName());
    }

    @Test
    public void testChannelContainsSite() {
        AnalogueEvent event = repo.findOne(3);
        Interlocking site = event.getChannel().getInterlocking();
        assertNotNull(site);
        assertEquals(new Short("3"), site.getId());
        assertEquals("BIR", site.getCode());
        assertEquals("Birmingham", site.getName());
    }

}
