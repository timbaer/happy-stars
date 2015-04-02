package de.waschnick.happy.stars;

import de.waschnick.happy.stars.api.Stars;
import org.springframework.web.bind.annotation.RequestMapping;

public class StarController {

    @RequestMapping("/stars")
    public Stars getStars() {
        return new Stars();
    }

}
