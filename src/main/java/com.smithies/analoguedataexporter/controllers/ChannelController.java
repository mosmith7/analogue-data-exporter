package com.smithies.analoguedataexporter.controllers;

import com.smithies.analoguedataexporter.entities.Channel;
import com.smithies.analoguedataexporter.factory.ChannelVOFactory;
import com.smithies.analoguedataexporter.repositories.ChannelRepository;
import com.smithies.analoguedataexporter.valueobjects.ChannelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("channels")
public class ChannelController {

    @Autowired
    private ChannelRepository repo;

    @GetMapping("site")
    public void getChannelsForSite(Model model) {
        List<Channel> channels = repo.findByInterlocking_Id(Short.valueOf("2"));
        model.addAttribute("channels", channels);
        return;
    }

    @GetMapping("site/{id}")
    public @ResponseBody List<ChannelVO> getChannelsForSite(@PathVariable("id") Short id) {
        List<Channel> channels = repo.findByInterlocking_Id(id);
        // Channels contain sites, which contain a list of channels which contain sites... etc.
        // Hibernate can handle this because it has the two objects in memory (e.g. a related site and channel) and they
        // just point back and forth to one another.
        // However, if we try to construct JSON out of this to pass back to the client, we will create an infinitely long
        // nested list - which will cause a stack overflow error.
        // Therefore create a VO i.e. a client version of the channel object that avoids this problem
        return ChannelVOFactory.generateVO(channels);
    }
}
