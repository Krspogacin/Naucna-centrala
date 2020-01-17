package org.upp.scholar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.upp.scholar.entity.Magazine;
import org.upp.scholar.entity.User;
import org.upp.scholar.model.MagazineDto;
import org.upp.scholar.repository.MagazineRepository;

import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MagazineService {

    @Autowired
    private UserService userService;

    @Autowired
    private MagazineRepository magazineRepository;

    public Magazine findByIssn(String issn){
        Magazine magazine = this.magazineRepository.findByIssn(issn);
        return magazine;
    }

    public Magazine save(Magazine magazine){
        if (magazine == null){
            throw new NotFoundException();
        }
        return this.magazineRepository.save(magazine);
    }

    public List<MagazineDto> findMagazinesByEditor(String editor){
        User baseEditor = this.userService.findByUsername(editor);
        List<Magazine> magazines = this.magazineRepository.findAllByChiefEditor(baseEditor);
        List<MagazineDto> magazineDtos = new ArrayList<>();
        for (Magazine magazine: magazines){
            magazineDtos.add(new MagazineDto(magazine));
        }
        return magazineDtos;
    }
}
