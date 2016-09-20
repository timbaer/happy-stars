package de.waschnick.happy.stars.business.universe.boundary;

import de.waschnick.happy.stars.api.Universe;
import de.waschnick.happy.stars.business.universe.entity.UniverseEntity;
import de.waschnick.happy.stars.business.universe.entity.UniverseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UniverseEdit {

    @Autowired
    private UniverseRepository universeRepository;

    public UniverseEntity save(UniverseEntity entity) {
        return universeRepository.save(entity);
    }

    public void delete(long id) {
        universeRepository.delete(id);
    }

    public UniverseEntity edit(Universe universe) {
        UniverseEntity currentUniverse = universeRepository.getOne(universe.getId());
        currentUniverse.setMaxSize(universe.getMaxSize());
        currentUniverse.setName(universe.getName());
        return universeRepository.save(currentUniverse);
    }
}
