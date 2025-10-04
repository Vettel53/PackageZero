package com.example.application.run;

import com.example.application.account.AppUser;

import com.example.application.weather.Weather;
import com.example.application.weather.WeatherService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class RunService {

    private final WeatherService weatherService;
    private final RunRepo runRepo;

    @Value("${weatherapi.fake-weather}")
    private boolean fakeWeatherGeneration;

    public RunService(RunRepo runRepo, WeatherService weatherService) {
        this.runRepo = runRepo;
        this.weatherService = weatherService;
    }

    public List<Run> getUserRuns(AppUser appUser) {
        return runRepo.findByAppUser(appUser);
    }

    @Transactional
    public void constructRunEntry(Run runToSave) {
        // Save run to generate Run ID
        runRepo.save(runToSave);

        Weather trackWeather;
        if (fakeWeatherGeneration) {
            trackWeather = weatherService.getFakeWeather();
        } else {
            trackWeather = weatherService.getCurrentWeather(runToSave.getTrack(), runToSave.getDate(), runToSave.getTime());
        }

        // Set trackWeather Run ID to created run
        trackWeather.setRun(runToSave);

        // Set the created run's weather
        runToSave.setWeather(trackWeather);

        // Save weather to H2 database
        weatherService.saveWeather(trackWeather);
    }

    public void saveEditedRun(Run runToEdit, LocalDate editedDate, LocalTime editedTime, String editedCar, String editedDriver, String editedTrack, String editedLane, BigDecimal editedDial, BigDecimal editedReaction, BigDecimal editedSixtyFoot, BigDecimal editedHalfTrack, BigDecimal editedFullTrack, BigDecimal editedSpeed) {
        // Set new values to the run to be edited
        runToEdit.setDate(editedDate);
        runToEdit.setTime(editedTime);
        runToEdit.setCar(editedCar);
        runToEdit.setDriver(editedDriver);
        runToEdit.setTrack(editedTrack);
        runToEdit.setLane(editedLane);
        // Truncate values before setting to database (10.1234 -> 10.123)
        runToEdit.setDial(editedDial);
        runToEdit.setReaction(editedReaction);
        runToEdit.setSixtyFoot(editedSixtyFoot);
        runToEdit.setHalfTrack(editedHalfTrack);
        runToEdit.setFullTrack(editedFullTrack);
        runToEdit.setSpeed(editedSpeed);

        runRepo.save(runToEdit);
    }

    public void deleteRun(Run runToDelete) {
        System.out.println("Run ID: " + runToDelete.getId());
        System.out.println("Weather: " + runToDelete.getWeather());
        if (runToDelete.getWeather() != null) {
            System.out.println("Weather ID: " + runToDelete.getWeather().getId());
        }
        runRepo.delete(runToDelete);
    }

}
