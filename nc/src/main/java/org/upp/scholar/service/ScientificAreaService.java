package org.upp.scholar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.upp.scholar.entity.ScientificArea;
import org.upp.scholar.repository.ScientificAreaRepository;

import javax.ws.rs.NotFoundException;
import java.util.List;

@Service
public class ScientificAreaService {

    @Autowired
    private ScientificAreaRepository scientificAreaRepository;

    public ScientificArea findOne(Long id){
        ScientificArea scientificArea = this.scientificAreaRepository.getOne(id);
        if (scientificArea == null){
            throw new NotFoundException();
        }
        return scientificArea;
    }

    public List<ScientificArea> findAll(){
        return this.scientificAreaRepository.findAll();
    }

    public ScientificArea save(ScientificArea scientificArea){
        return this.scientificAreaRepository.save(scientificArea);
    }
}
