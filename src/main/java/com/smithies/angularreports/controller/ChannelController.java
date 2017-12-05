package com.smithies.angularreports.controller;

import com.smithies.angularreports.entities.Channel;
import com.smithies.angularreports.factory.ChannelVOFactory;
import com.smithies.angularreports.repositories.ChannelRepository;
import com.smithies.angularreports.valueobjects.ChannelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("channels")
public class ChannelController {

    @Autowired
    private ChannelRepository repo;

    @GetMapping("/id/{id}")
    public @ResponseBody
    ChannelVO getChannelsForSite(@PathVariable("id") Integer id) {
        return ChannelVOFactory.generateVO(repo.findOne(id));
    }

    @GetMapping("site")
    public void getChannelsForSite(Model model) {
        List<Channel> channels = repo.findByInterlocking_Id(Short.valueOf("2"));
        model.addAttribute("channels", channels);
        return;
    }

    @GetMapping("site/{id}")
    public @ResponseBody
    List<ChannelVO> getChannelsForSite(@PathVariable("id") Short id) {
        List<Channel> channels = repo.findByInterlocking_Id(id);
        // Channels contain sites, which contain a list of channels which contain sites... etc.
        // Hibernate can handle this because it has the two objects in memory (e.g. a related site and channel) and they
        // just point back and forth to one another.
        // However, if we try to construct JSON out of this to pass back to the client, we will create an infinitely long
        // nested list - which will cause a stack overflow error.
        // Therefore create a VO i.e. a client version of the channel object that avoids this problem
        return ChannelVOFactory.generateVO(channels);
    }

    @GetMapping
    public @ResponseBody
    List<ChannelVO> getChannelsForSite(@RequestParam("siteId") Short siteId,
                                       @RequestParam("channel") String channelName) {
        List<Channel> channels = repo.findByInterlocking_IdAndNameContains(siteId, channelName);
        return ChannelVOFactory.generateVO(channels);
    }
}
