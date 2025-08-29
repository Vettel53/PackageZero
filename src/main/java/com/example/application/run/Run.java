package com.example.application.run;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import com.example.application.account.AppUser;
import com.example.application.weather.Weather;

@Entity
public class Run {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO: Understand this relationship deeper
    /**
     * The @ManyToOne annotation means that many Run entries can be linked to one AppUser.
     * @ JoinColumn(name = "user_id") specifies the foreign key column in the Run table that links back to AppUser.
     * This means in the database, the Run table will have a column user_id, storing the primary key (id) of the associated AppUser.
     * */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser appUser;

    private LocalDate date;
    private LocalTime time;
    private String car;
    private String driver;
    private String track;
    private String lane;
    @Digits(integer = 2, fraction = 4)
    private BigDecimal dial;
    @Digits(integer = 2, fraction = 4)
    private BigDecimal reaction;
    @Digits(integer = 2, fraction = 4)
    private BigDecimal sixtyFoot;
    @Digits(integer = 2, fraction = 4)
    private BigDecimal halfTrack;
    @Digits(integer = 2, fraction = 4)
    private BigDecimal fullTrack;
    @Digits(integer = 3, fraction = 4)
    private BigDecimal speed;

    // Cascade.remove = when a run is deleted, the corresponding weather entry is deleted too
    @OneToOne(mappedBy = "run", cascade = CascadeType.REMOVE)
    private Weather weather;

    public Run(AppUser appUser,
               LocalDate date,
               LocalTime time,
               String car,
               String driver,
               String track,
               String lane,
               BigDecimal dial,
               BigDecimal reaction,
               BigDecimal sixtyFoot,
               BigDecimal halfTrack,
               BigDecimal fullTrack,
               BigDecimal speed) {
        this.appUser = appUser;
        this.date = date;
        this.time = time;
        this.car = car;
        this.driver = driver;
        this.track = track;
        this.lane = lane;
        this.dial = dial;
        this.reaction = reaction;
        this.sixtyFoot = sixtyFoot;
        this.halfTrack = halfTrack;
        this.fullTrack = fullTrack;
        this.speed = speed;
    }

    public Run() {

    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getReaction() {
        return reaction;
    }

    public void setReaction(BigDecimal reaction) {
        this.reaction = reaction;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public String getLane() {
        return lane;
    }

    public void setLane(String lane) {
        this.lane = lane;
    }

    public BigDecimal getDial() {
        return dial;
    }

    public void setDial(BigDecimal dial) {
        this.dial = dial;
    }

    public BigDecimal getSixtyFoot() {
        return sixtyFoot;
    }

    public void setSixtyFoot(BigDecimal sixtyFoot) {
        this.sixtyFoot = sixtyFoot;
    }

    public BigDecimal getHalfTrack() {
        return halfTrack;
    }

    public void setHalfTrack(BigDecimal halfTrack) {
        this.halfTrack = halfTrack;
    }

    public BigDecimal getFullTrack() {
        return fullTrack;
    }

    public void setFullTrack(BigDecimal fullTrack) {
        this.fullTrack = fullTrack;
    }

    public BigDecimal getSpeed() {
        return speed;
    }

    public void setSpeed(BigDecimal speed) {
        this.speed = speed;
    }

    public AppUser getUser() {
        return appUser;
    }

    public void setUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }
}
