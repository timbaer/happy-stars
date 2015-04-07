package de.waschnick.happy.stars.api;

import lombok.Data;

@Data
public class Star {

    private long id;

    private String name;

    private StarColor color;

}
