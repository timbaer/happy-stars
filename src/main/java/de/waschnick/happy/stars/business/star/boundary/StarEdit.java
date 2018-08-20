package de.waschnick.happy.stars.business.star.boundary;

import de.waschnick.happy.stars.api.Star;
import de.waschnick.happy.stars.business.star.entity.StarEntity;
import de.waschnick.happy.stars.business.star.entity.StarRepository;
import de.waschnick.happy.stars.business.universe.control.ToManyStarsForUniverseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StarEdit {

    @Autowired
    private StarRepository starRepository;

    @CacheEvict(value = "stars", allEntries = true)
    public StarEntity save(StarEntity entity) {
        checkMaxSizeOfUniverse(entity);
        return starRepository.save(entity);
    }

    @CacheEvict(value = "stars", allEntries = true)
    public void delete(long id) {
        starRepository.deleteById(id);
    }

    @CacheEvict(value = "stars", allEntries = true)
    public StarEntity edit(Star star) {
        StarEntity currentStar = starRepository.getOne(star.getId());
        currentStar.setColor(star.getColor());
        currentStar.setName(star.getName());
        return starRepository.save(currentStar);
    }

    private void checkMaxSizeOfUniverse(StarEntity entity) {
        long maxSizeOfUnivsere = entity.getUniverse().getMaxSize();
        List<StarEntity> allStarsForUnivese = starRepository.findByUniverse(entity.getUniverse());
        if (allStarsForUnivese.size() >= maxSizeOfUnivsere) {
            throw new ToManyStarsForUniverseException(entity.getUniverse());
        }
    }
}
