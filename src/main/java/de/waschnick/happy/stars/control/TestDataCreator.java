package de.waschnick.happy.stars.control;

import de.waschnick.happy.stars.api.StarColor;
import de.waschnick.happy.stars.entity.StarEntity;
import de.waschnick.happy.stars.entity.StarRepository;
import de.waschnick.happy.stars.entity.UniverseEntity;
import de.waschnick.happy.stars.entity.UniverseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Component
@Transactional
public class TestDataCreator {

    @Autowired
    private UniverseRepository universeRepository;

    @Autowired
    private StarRepository starRepository;

    @Transactional
    public void createAndSaveTestDataIfDatabaseEmpty() {
        if (universeRepository.findAll().isEmpty()) {
            log.info("####################################");
            log.info("Creating Testdata...");

            UniverseEntity u1 = universe("Universe 1", 1);
            universeRepository.save(u1);

            addStarToUniverse(u1, "start_1", StarColor.BLUE);

            universeRepository.flush();
            log.info("Finished creating Testdata!");
            log.info("####################################");
        }
    }

    private void addStarToUniverse(UniverseEntity universe, String name, StarColor color) {
        StarEntity star = new StarEntity();
        star.setName(name);
        star.setColor(color);
        star.setUniverse(universe);
        starRepository.save(star);
    }

    private UniverseEntity universe(String name, long maxSize) {
        UniverseEntity universe = new UniverseEntity();
        universe.setName(name);
        universe.setMaxSize(maxSize);
        return universe;
    }

}
