package de.waschnick.happy.stars.business.star.boundary;

import de.waschnick.happy.stars.api.Star;
import de.waschnick.happy.stars.business.star.entity.StarEntity;
import de.waschnick.happy.stars.business.star.entity.StarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StarEdit {

    @Autowired
    private StarRepository starRepository;

    @Autowired
    private StarFactory starFactory;

    public StarEntity save(StarEntity entity) {
        return starRepository.save(entity);
    }

    public void delete(long id) {
        starRepository.delete(id);
    }

    public StarEntity edit(Star star) {
        // FIXME
        return starFactory.mappe(star);
    }
}
