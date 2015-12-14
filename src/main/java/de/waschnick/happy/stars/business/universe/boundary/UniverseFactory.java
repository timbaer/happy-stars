package de.waschnick.happy.stars.business.universe.boundary;

import de.waschnick.happy.stars.api.Universe;
import de.waschnick.happy.stars.api.Universes;
import de.waschnick.happy.stars.business.universe.entity.UniverseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UniverseFactory {

    public Universe mappe(UniverseEntity entity) {
        Universe universe = new Universe();
        universe.setId(entity.getId());
        universe.setName(entity.getName());
        universe.setMaxSize(entity.getMaxSize());
        return universe;
    }

    public UniverseEntity mappe(Universe star) {
        UniverseEntity entity = new UniverseEntity();
        entity.setName(star.getName());
        entity.setMaxSize(star.getMaxSize());
        return entity;
    }

    public Universes mappe(List<UniverseEntity> entities) {
        Universes result = new Universes();
        for (UniverseEntity entity : entities) {
            result.add(mappe(entity));
        }
        return result;
    }
}
