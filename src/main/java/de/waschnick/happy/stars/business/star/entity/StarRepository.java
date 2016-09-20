package de.waschnick.happy.stars.business.star.entity;

import de.waschnick.happy.stars.business.universe.entity.UniverseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StarRepository extends JpaRepository<StarEntity, Long> {

    List<StarEntity> findByUniverse(UniverseEntity universeEntity);
}
