package de.waschnick.happy.stars.entity;

import de.waschnick.happy.stars.api.StarColor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "star")
public class StarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    @NotNull
    @Size(min = 1, max = 255)
    private StarColor color;

    public StarEntity() {
        // default Constructor
    }

    public StarEntity(long id) {
        this.id = id;
    }

}
