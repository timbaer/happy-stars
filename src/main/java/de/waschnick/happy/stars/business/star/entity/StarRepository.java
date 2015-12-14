package de.waschnick.happy.stars.business.star.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StarRepository extends JpaRepository<StarEntity, Long> {

//    List<StarEntity> findByUniverse(long id);
}
