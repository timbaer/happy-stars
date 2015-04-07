package de.waschnick.happy.stars.entity;

import de.waschnick.happy.stars.api.StarColor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class StarEntity {

    private long id;

    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    @NotNull
    private StarColor color;

    public StarEntity() {
        // default Constructor
    }

    public StarEntity(long id) {
        this.id = id;
    }

}
