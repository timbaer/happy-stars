package de.waschnick.happy.stars.api;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Star {

    private long id;

    @NotNull
    private String name;

    @NotNull
    private StarColor color;

    @NotNull
    private Long universeId;
}
