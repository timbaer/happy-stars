package de.waschnick.happy.stars.controller;

import de.waschnick.happy.stars.api.StarColor;
import de.waschnick.happy.stars.business.star.control.StarImages;
import de.waschnick.happy.stars.business.star.entity.StarRepository;
import de.waschnick.happy.stars.business.universe.entity.UniverseRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URL;

@CrossOrigin
@RestController
@RequestMapping("/api/")
public class InfoController {

    @Autowired
    private StarRepository starRepository;

    @Autowired
    private UniverseRepository universeRepository;

    @Autowired
    private StarImages starImages;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class VersionInfo {
        String version;
    }


    @Cacheable("stars")
    @RequestMapping(value = "count/star", method = RequestMethod.GET)
    public Long coutnStars() {
        return starRepository.count();
    }

    @Cacheable("universes")
    @RequestMapping(value = "count/universe", method = RequestMethod.GET)
    public Long coutnUniverse() {
        return universeRepository.count();
    }

    @RequestMapping(value = "colors/values", method = RequestMethod.GET)
    public StarColor[] possibleColorsOfTheStars() {
        return StarColor.values();
    }

    @RequestMapping(value = "image/star/{name}", method = RequestMethod.GET)
    public String imageUrl(HttpServletRequest request, @NotBlank @PathVariable String name) throws Exception {
        URL url = new URI(request.getRequestURL().toString()).toURL();
        String imagePath = starImages.getImageUrlForName(name);
        return url.getProtocol() + "://" + url.getHost() + ":" + url.getPort() + "/" + imagePath;
    }


    @RequestMapping(value = "version", method = RequestMethod.GET)
    public VersionInfo getVersion() {
        return new VersionInfo("alive!");
    }
}
