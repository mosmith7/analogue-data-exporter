package com.smithies.analoguedataexporter.controllers;

import com.smithies.analoguedataexporter.entities.Interlocking;
import com.smithies.analoguedataexporter.factory.InterlockingVOFactory;
import com.smithies.analoguedataexporter.repositories.InterlockingRepository;
import com.smithies.analoguedataexporter.valueobjects.InterlockingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("sites")
public class InterlockingController {

    @Autowired
    private InterlockingRepository repo;

    @GetMapping()
    public @ResponseBody List<InterlockingVO> findAll() {
        List<Interlocking> sites = new ArrayList<>();
        repo.findAll().forEach(sites::add);
        return InterlockingVOFactory.generateVO(sites);
    }

    @GetMapping("/id/{id}")
    public @ResponseBody InterlockingVO findById(@PathVariable("id") Short id) {
        return InterlockingVOFactory.generateVO(repo.findOne(id));
    }

    @GetMapping("/search")
    public @ResponseBody List<InterlockingVO> search(@RequestParam("search") String siteName) {
        List<Interlocking> sites = new ArrayList<>();
        repo.findInterlockingByNameStartsWith(siteName).forEach(sites::add);
        return InterlockingVOFactory.generateVO(sites);
    }

    @GetMapping("/names")
    public @ResponseBody List<String> findAllNames() {
        List<Interlocking> sites = new ArrayList<>();
        repo.findAll().forEach(sites::add);
        List<InterlockingVO> vos = InterlockingVOFactory.generateVO(sites);
        return vos.stream().map(vo -> vo.getName()).collect(Collectors.toList());
    }

    @GetMapping("/{name}/id")
    public @ResponseBody Short getIdForName(@PathVariable("name") String siteName) {
        return repo.findInterlockingByNameEquals(siteName).getId();
    }
}
