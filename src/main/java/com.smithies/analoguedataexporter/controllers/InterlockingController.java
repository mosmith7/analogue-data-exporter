package com.smithies.analoguedataexporter.controllers;

import com.smithies.analoguedataexporter.entities.Interlocking;
import com.smithies.analoguedataexporter.factory.InterlockingVOFactory;
import com.smithies.analoguedataexporter.repositories.InterlockingRepository;
import com.smithies.analoguedataexporter.valueobjects.InterlockingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("sites")
public class InterlockingController {

    @Autowired
    private InterlockingRepository repo;

    @GetMapping()
    public List<InterlockingVO> findAll() {
        List<Interlocking> sites = new ArrayList<>();
        repo.findAll().forEach(sites::add);
        return InterlockingVOFactory.generateVO(sites);
    }
}
