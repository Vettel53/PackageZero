package com.example.application.weather;

import com.example.application.run.Run;

import jakarta.persistence.*;

@Entity
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "run_id")
    private Run run;

    private String temperature;
    private String relativeHumidity;
    private String uncorrectedBarometer;
    private String correctedBarometer;
    private String windSpeed;
    private String windDirection;
    private String dewPoint;
    private String saturationPressure;
    private String vaporPressure;
    private String grains;
    private String airDensityNoWaterVapor;
    private String airDensityWithWaterVapor;
    private String densityAltitude;

    public Weather() {
    }

    public Weather(String temperature, String relativeHumidity, String uncorrectedBarometer, String correctedBarometer, String windSpeed, String windDirection, String dewPoint, String saturationPressure, String vaporPressure, String grains, String airDensityNoWaterVapor, String airDensityWithWaterVapor, String densityAltitude) {
        this.temperature = temperature;
        this.relativeHumidity = relativeHumidity;
        this.uncorrectedBarometer = uncorrectedBarometer;
        this.correctedBarometer = correctedBarometer;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.dewPoint = dewPoint;
        this.saturationPressure = saturationPressure;
        this.vaporPressure = vaporPressure;
        this.grains = grains;
        this.airDensityNoWaterVapor = airDensityNoWaterVapor;
        this.airDensityWithWaterVapor = airDensityWithWaterVapor;
        this.densityAltitude = densityAltitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Run getRun() {
        return run;
    }

    public void setRun(Run run) {
        this.run = run;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getRelativeHumidity() {
        return relativeHumidity;
    }

    public void setRelativeHumidity(String relativeHumidity) {
        this.relativeHumidity = relativeHumidity;
    }

    public String getUncorrectedBarometer() {
        return uncorrectedBarometer;
    }

    public void setUncorrectedBarometer(String uncorrectedBarometer) {
        this.uncorrectedBarometer = uncorrectedBarometer;
    }

    public String getCorrectedBarometer() {
        return correctedBarometer;
    }

    public void setCorrectedBarometer(String correctedBarometer) {
        this.correctedBarometer = correctedBarometer;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(String dewPoint) {
        this.dewPoint = dewPoint;
    }

    public String getSaturationPressure() {
        return saturationPressure;
    }

    public void setSaturationPressure(String saturationPressure) {
        this.saturationPressure = saturationPressure;
    }

    public String getVaporPressure() {
        return vaporPressure;
    }

    public void setVaporPressure(String vaporPressure) {
        this.vaporPressure = vaporPressure;
    }

    public String getGrains() {
        return grains;
    }

    public void setGrains(String grains) {
        this.grains = grains;
    }

    public String getAirDensityNoWaterVapor() {
        return airDensityNoWaterVapor;
    }

    public void setAirDensityNoWaterVapor(String airDensityNoWaterVapor) {
        this.airDensityNoWaterVapor = airDensityNoWaterVapor;
    }

    public String getAirDensityWithWaterVapor() {
        return airDensityWithWaterVapor;
    }

    public void setAirDensityWithWaterVapor(String airDensityWithWaterVapor) {
        this.airDensityWithWaterVapor = airDensityWithWaterVapor;
    }

    public String getDensityAltitude() {
        return densityAltitude;
    }

    public void setDensityAltitude(String densityAltitude) {
        this.densityAltitude = densityAltitude;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "id=" + id +
                ", run=" + run +
                ", temperature='" + temperature + '\'' +
                ", humidity='" + relativeHumidity + '\'' +
                ", uncorrectedBarometer='" + uncorrectedBarometer + '\'' +
                ", correctedBarometer='" + correctedBarometer + '\'' +
                ", windSpeed='" + windSpeed + '\'' +
                ", windDirection='" + windDirection + '\'' +
                ", dewPoint='" + dewPoint + '\'' +
                ", saturationPressure='" + saturationPressure + '\'' +
                ", vaporPressure='" + vaporPressure + '\'' +
                ", grains='" + grains + '\'' +
                ", airDensityNoVapor='" + airDensityNoWaterVapor + '\'' +
                ", airDensityWithVapor='" + airDensityWithWaterVapor + '\'' +
                '}';
    }

}
