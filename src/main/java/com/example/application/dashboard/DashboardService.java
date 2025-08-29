package com.example.application.dashboard;

import com.example.application.run.RunRepo;
import com.example.application.account.AppUser;
import com.example.application.account.UserRepo;
import com.example.application.weather.Weather;
import com.example.application.run.Run;
import com.example.application.security.SecurityService;
import com.example.application.weather.WeatherService;
import com.example.application.weather.WeatherRepo;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@VaadinSessionScope
public class DashboardService {

    // Dependency Injection (constructor)
    private final RunRepo runRepo;
    private final UserRepo userRepo;
    private final WeatherRepo weatherRepo;
    private final SecurityService securityService;
    private final WeatherService weatherService;

    // Setter injection to access a method in dashboardView
    private DashboardView dashboardView;

    public DashboardService(RunRepo runRepo, UserRepo userRepo, WeatherRepo weatherRepo, SecurityService securityService, WeatherService weatherService) {
        this.runRepo = runRepo;
        this.userRepo = userRepo;
        this.weatherRepo = weatherRepo;
        this.securityService = securityService;
        this.weatherService = weatherService;
    }

    public String getAuthenticatedUserName() {
        UserDetails loadUserDetails = securityService.getAuthenticatedUser();
        if (loadUserDetails == null) {
            return null;
        }
        String username = loadUserDetails.getUsername();
        System.out.println("The Authenticated User returned this username: " + username);
        return username;
    }

    public AppUser getAppUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public List<Run> getAllRunsFromUser(AppUser loggedInAppUser) {
        return runRepo.findByAppUser(loggedInAppUser);
    }

    // Used to create fake run entries for ease
    @Transactional
    public Run constructFakeRunEntry(AppUser loggedInAppUser) {
        // Fake values for LocalDate and LocalTime
        LocalDate date = LocalDate.of(2023, 10, 26); // Example date
        LocalTime time = LocalTime.of(14, 30); // Example time

        // Fake values for String fields
        String carText = "1969 Camaro";
        String driverText = "John Doe";
        String trackText = "Edinburg Motorsports Park";
        String laneText = "Left";

        // Fake values for BigDecimal fields
        BigDecimal dialText = new BigDecimal("10.50");
        BigDecimal reactionText = new BigDecimal("0.123");
        BigDecimal sixtyFootText = new BigDecimal("1.85");
        BigDecimal halfTrackText = new BigDecimal("7.503");
        BigDecimal fullTrackText = new BigDecimal("11.90");
        BigDecimal speedText = new BigDecimal("115.75");

        // Create a new Run object with the entered values and save it to the database
        Run newFakeRun = new Run(loggedInAppUser, date, time, carText, driverText, trackText, laneText, dialText, reactionText, sixtyFootText, halfTrackText, fullTrackText, speedText);

        // Get current track weather
        Weather trackWeather = weatherService.getCurrentWeather();
        if (trackWeather == null) {
            return null;
        }

        // Save run to H2 database to create primary key ID
        runRepo.save(newFakeRun);

        // Set trackWeather Run ID to created run
        trackWeather.setRun(newFakeRun);

        // Set the created run's weather
        newFakeRun.setWeather(trackWeather);

        // Save weather to H2 database
        weatherRepo.save(trackWeather);

        return newFakeRun;
    }


    // Used to create a new REAL run entry for the authenticated user
    @Transactional
    public void constructRunEntry(Run runToSave) {
        // Save run to generate Run ID
        runRepo.save(runToSave);

        // Get current track weather
        //Weather trackWeather = weatherService.getCurrentWeather(runToSave.getTrack(), runToSave.getDate(), runToSave.getTime());
        Weather trackWeather = weatherService.getCurrentWeather();
        // Set trackWeather Run ID to created run
        trackWeather.setRun(runToSave);

        // Set the created run's weather
        runToSave.setWeather(trackWeather);

        // Save weather to H2 database
        weatherRepo.save(trackWeather);
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

        // Save the edited run to the database
        runRepo.save(runToEdit);
    }

    public void deleteRun(Run runToDelete) {
        // Delete the run from the database
        System.out.println("Run ID: " + runToDelete.getId());
        System.out.println("Weather: " + runToDelete.getWeather());
        if (runToDelete.getWeather() != null) {
            System.out.println("Weather ID: " + runToDelete.getWeather().getId());
        }
        runRepo.delete(runToDelete);
    }

    public void setDashboardView(DashboardView dashboardView) {
        this.dashboardView = dashboardView;
    }

    // TODO: DOC THESE METHODS HEAVY
    public void callUpdateGridAfterAdd(Run createdRun){
        dashboardView.updateGridAfterAdd(createdRun);
    }

    public void callRefreshGrid() {
        dashboardView.refreshGridData();
    }

    public void callUpdateGridAfterDelete(Run runToDelete) {
        dashboardView.updateGridAfterDelete(runToDelete);
    }

    public BigDecimal truncateToValidDecimal(BigDecimal decimalToTruncate) {
        return decimalToTruncate.setScale(3, RoundingMode.DOWN);
    }

}
