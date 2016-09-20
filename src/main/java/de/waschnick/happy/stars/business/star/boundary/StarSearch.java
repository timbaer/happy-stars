package de.waschnick.happy.stars.business.star.boundary;

import de.waschnick.happy.stars.business.star.control.StarNotFoundException;
import de.waschnick.happy.stars.business.star.entity.StarEntity;
import de.waschnick.happy.stars.business.star.entity.StarRepository;
import de.waschnick.happy.stars.business.universe.boundary.UniverseSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StarSearch {

    @Autowired
    private StarRepository starRepository;

    @Autowired
    private UniverseSearch universeRepository;


    public List<StarEntity> findAll() {
        return starRepository.findAll();
    }
    
    public List<StarEntity> findAllFromUniverse(Long universeId) {
        return new ArrayList<>(universeRepository.findUniverse(universeId).getStars());
    }

    public StarEntity findStar(long id) {
        StarEntity one = starRepository.getOne(id);
        if (one == null) {
            throw new StarNotFoundException(id);
        }
        return one;
    }
}
