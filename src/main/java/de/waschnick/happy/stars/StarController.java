package de.waschnick.happy.stars;

import de.waschnick.happy.stars.api.Star;
import de.waschnick.happy.stars.api.Stars;
import de.waschnick.happy.stars.entity.StarEntity;
import de.waschnick.happy.stars.entity.StarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class StarController {

    @Autowired
    private StarRepository starRepository;

    @RequestMapping(value = "/stars", method = RequestMethod.GET)
    public Stars getStars() {
        List<StarEntity> stars = starRepository.findAll();
        Stars result = new Stars();
        for (StarEntity star : stars) {
            result.add(mappe(star));
        }
        return result;
    }

    @RequestMapping(value = "/stars/{id}", method = RequestMethod.GET)
    public Star getStar(@PathVariable long id) {
        return new Star();
    }

    @RequestMapping(value = "/stars/{id}", method = RequestMethod.PUT)
    public Star updateStar(@PathVariable long id) {
        return new Star();
    }

    @RequestMapping(value = "/stars", method = RequestMethod.POST)
    public Star addStar(@RequestBody Star star) {
        StarEntity entity = mappe(star);
        return mappe(starRepository.save(entity));
    }

    @RequestMapping(value = "/stars/{id}", method = RequestMethod.DELETE)
    public void deleteStar(@PathVariable long id) {
        starRepository.delete(id);
    }

    private Star mappe(StarEntity starEntity) {
        Star star = new Star();
        star.setId(starEntity.getId());
        star.setColor(starEntity.getColor());
        star.setName(starEntity.getName());
        return star;
    }

    private StarEntity mappe(Star star) {
        StarEntity entity = new StarEntity();
        entity.setColor(star.getColor());
        entity.setName(star.getName());
        return entity;
    }

}
