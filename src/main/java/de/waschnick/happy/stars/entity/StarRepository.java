package de.waschnick.happy.stars.entity;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class StarRepository {

    private static List<StarEntity> starEntities = new ArrayList<StarEntity>();
    private Random random = new Random();

    public StarEntity save(StarEntity entity) {
        entity.setId(random.nextLong());
        starEntities.add(entity);
        return entity;
    }

    public void delete(long id) {
        for (StarEntity starEntity : new ArrayList<StarEntity>(starEntities)) {
            if (starEntity.getId() == id) {
                starEntities.remove(starEntity);
            }
        }
    }

    public List<StarEntity> findAll() {
        return starEntities;
    }
}
