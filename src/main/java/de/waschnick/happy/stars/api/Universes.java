package de.waschnick.happy.stars.api;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Universes {

    private final List<Universe> universes = new ArrayList<Universe>();

    public void add(Universe star) {
        universes.add(star);
    }
}
