package de.waschnick.happy.stars.controller;

import de.waschnick.happy.stars.api.Star;
import de.waschnick.happy.stars.api.Stars;
import de.waschnick.happy.stars.business.star.boundary.StarEdit;
import de.waschnick.happy.stars.business.star.boundary.StarFactory;
import de.waschnick.happy.stars.business.star.boundary.StarSearch;
import de.waschnick.happy.stars.business.star.entity.StarEntity;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/")
public class StarController {

    @Autowired
    private StarFactory starFactory;

    @Autowired
    private StarEdit starEdit;

    @Autowired
    private StarSearch starSearch;


    @ApiOperation(value = "Get all available stars", notes = "The Stars are not sorted")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
    @RequestMapping(value = "/star", method = RequestMethod.GET)
    public Stars getStars() {
        return starFactory.mappe(starSearch.findAll());
    }


    @ApiOperation(value = "Get a single star")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 406, message = "Star not found")

    })
    @RequestMapping(value = "/star/{id}", method = RequestMethod.GET)
    public Star getStar(@PathVariable long id) {
        return starFactory.mappe(starSearch.findStar(id));
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
    @RequestMapping(value = "/star", method = RequestMethod.PUT)
    public Star updateStar(@RequestBody Star star) {
        return starFactory.mappe(starEdit.edit(star));
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 406, message = "Universe not found")

    })
    @RequestMapping(value = "/star", method = RequestMethod.POST)
    public Star addStar(@RequestBody Star star) {
        StarEntity entity = starFactory.mappe(star);
        return starFactory.mappe(starEdit.save(entity));
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
    @RequestMapping(value = "/star/{id}", method = RequestMethod.DELETE)
    public void deleteStar(@PathVariable long id) {
        starEdit.delete(id);
    }


}
