package org.accolite.entity;

import lombok.*;

import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PlayerStatistics {
    private String playerName;
    private String countryName;
    private Date birthDate;
    private long scores;
    private int matchesPlayed;
    private int wicketTaken;
    private int highestScore;
    private double average;
    private double strikeRate;
    public  double getAverage(){
        return this.wicketTaken/this.getMatchesPlayed();
    }


}
