package de.waschnick.happy.stars.api;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class Universe {

    private Long id;

    @NotNull
    private String name;

    @Min(1)
    @Max(255)
    @NotNull
    private Long maxSize;

}
