package com.mach8.nba.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class Player implements Serializable {


    @Getter @Setter
    @JsonIgnore
    Integer index;

    @Getter @Setter
    String firstName;

    @Getter @Setter
    String lastName;

    @Getter @Setter
    Integer heightInches;

    @Getter @Setter
    Float heightMeters;

    public static Player build(Integer index, String firstName, String lastName, Integer heightInches, Float heightMeters){
        Player player = new Player();
        player.setIndex(index);
        player.setFirstName(firstName);
        player.setLastName(lastName);
        player.setHeightInches(heightInches);
        player.setHeightMeters(heightMeters);
        return player;
    }

    @JsonIgnore
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
