package de.waschnick.happy.stars.business.star.boundary;

import de.waschnick.happy.stars.api.Star;
import de.waschnick.happy.stars.api.Stars;
import de.waschnick.happy.stars.business.star.entity.StarEntity;
import de.waschnick.happy.stars.business.star.entity.StarRepository;
import de.waschnick.happy.stars.business.universe.boundary.UniverseSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StarFactory {

    @Autowired
    private StarRepository starRepository;

    @Autowired
    private UniverseSearch universeSearch;

    public Stars mappe(List<StarEntity> stars) {
        Stars result = new Stars();
        for (StarEntity star : stars) {
            result.add(mappe(star));
        }
        return result;
    }

    public Star mappe(StarEntity starEntity) {
        Star star = new Star();
        star.setId(starEntity.getId());
        star.setColor(starEntity.getColor());
        star.setName(starEntity.getName());
        star.setUniverseId(starEntity.getUniverse().getId());
        return star;
    }

    public StarEntity mappe(Star star) {
        StarEntity entity = new StarEntity();
        entity.setColor(star.getColor());
        entity.setName(star.getName());
        entity.setUniverse(universeSearch.findUniverse(star.getId()));
        return entity;
    }



}
