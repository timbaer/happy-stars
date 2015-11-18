package de.waschnick.happy.stars.entity;

import de.waschnick.happy.stars.api.StarColor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(of = {"id"})
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
    @Enumerated(EnumType.STRING)
    private StarColor color;

    @NotNull
    @JoinColumn(name = "universe")
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private UniverseEntity universe;

    public StarEntity() {
        // default Constructor
    }

    public StarEntity(long id) {
        this.id = id;
    }

}
