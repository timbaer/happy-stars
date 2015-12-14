package de.waschnick.happy.stars.controller;

import de.waschnick.happy.stars.api.Stars;
import de.waschnick.happy.stars.api.Universe;
import de.waschnick.happy.stars.api.Universes;
import de.waschnick.happy.stars.business.star.boundary.StarFactory;
import de.waschnick.happy.stars.business.star.boundary.StarSearch;
import de.waschnick.happy.stars.business.universe.boundary.UniverseEdit;
import de.waschnick.happy.stars.business.universe.boundary.UniverseFactory;
import de.waschnick.happy.stars.business.universe.boundary.UniverseSearch;
import de.waschnick.happy.stars.business.universe.entity.UniverseEntity;
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
    private UniverseSearch universeSearch;

    @Autowired
    private UniverseEdit universeEdit;

    @Autowired
    private UniverseFactory universeFactory;

    @Autowired
    private StarSearch starSearch;

    @Autowired
    private StarFactory starFactory;


    @RequestMapping(value = "/universe", method = RequestMethod.GET)
    public Universes getUniverses() {

        List<UniverseEntity> entities = universeSearch.findAll();
        return universeFactory.mappe(entities);
    }

    @RequestMapping(value = "/universe/{id}", method = RequestMethod.GET)
    public Universe getUniverse(@PathVariable long id) {
        return universeFactory.mappe(universeSearch.findUniverse(id));
    }


    @RequestMapping(value = "/universe/{id}/stars", method = RequestMethod.GET)
    public Stars getStarsForUniverse(@PathVariable long id) {
        return starFactory.mappe(starSearch.findAllFromUniverse(id));
    }

    @RequestMapping(value = "/universe", method = RequestMethod.PUT)
    public Universe updateUniverse(@RequestBody Universe universe) {
        return universeFactory.mappe(universeEdit.edit(universe));
    }

    @RequestMapping(value = "/universe", method = RequestMethod.POST)
    public Universe addUniverse(@RequestBody Universe universe) {
        UniverseEntity entity = universeFactory.mappe(universe);
        return universeFactory.mappe(universeEdit.save(entity));
    }

    @RequestMapping(value = "/universe/{id}", method = RequestMethod.DELETE)
    public void deleteUniverse(@PathVariable long id) {
        universeEdit.delete(id);
    }



}
