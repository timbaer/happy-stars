package de.waschnick.happy.stars.controller;

import de.waschnick.happy.stars.api.Universe;
import de.waschnick.happy.stars.api.Universes;
import de.waschnick.happy.stars.control.TestDataCreator;
import de.waschnick.happy.stars.entity.UniverseEntity;
import de.waschnick.happy.stars.entity.UniverseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class UniverseController {

    @Autowired
    private UniverseRepository universeRepository;

    @Autowired
    private TestDataCreator testDataCreator;

    @RequestMapping(value = "/universe", method = RequestMethod.GET)
    public Universes getUniverses() {
        //testDataCreator.createAndSaveTestDataIfDatabaseEmpty();

        List<UniverseEntity> entities = universeRepository.findAll();
        Universes result = new Universes();
        for (UniverseEntity entity : entities) {
            result.add(mappe(entity));
        }
        return result;
    }

    @RequestMapping(value = "/universe/{id}", method = RequestMethod.GET)
    public Universe getUniverse(@PathVariable long id) {
        return mappe(universeRepository.findOne(id));
    }

    @RequestMapping(value = "/universe/{id}", method = RequestMethod.PUT)
    public Universe updateStar(@PathVariable long id) {
        // FIXME todo
        return new Universe();
    }

    @RequestMapping(value = "/universe", method = RequestMethod.POST)
    public Universe addStar(@RequestBody Universe star) {
        UniverseEntity entity = mappe(star);
        return mappe(universeRepository.save(entity));
    }

    @RequestMapping(value = "/universe/{id}", method = RequestMethod.DELETE)
    public void deleteUniverse(@PathVariable long id) {
        universeRepository.delete(id);
    }

    private Universe mappe(UniverseEntity entity) {
        Universe universe = new Universe();
        universe.setId(entity.getId());
        universe.setName(entity.getName());
        universe.setMaxSize(entity.getMaxSize());
        return universe;
    }

    private UniverseEntity mappe(Universe star) {
        UniverseEntity entity = new UniverseEntity();
        entity.setName(star.getName());
        entity.setMaxSize(star.getMaxSize());
        return entity;
    }

}
