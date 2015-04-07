package de.waschnick.happy.stars.api;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Stars {

    private final List<Star> stars = new ArrayList<Star>();

    public void add(Star star) {
        stars.add(star);
    }
}
