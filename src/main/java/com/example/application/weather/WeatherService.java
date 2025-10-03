package com.example.application.weather;

import com.example.application.run.Run;
import com.nimbusds.jose.shaded.gson.JsonArray;
import com.nimbusds.jose.shaded.gson.JsonObject;
import com.nimbusds.jose.shaded.gson.JsonParser;
import com.vaadin.flow.component.notification.Notification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class WeatherService {

    private final WebClient.Builder builder;
    private final WeatherRepo weatherRepo;

    @Value("${weatherapi.local}")
    private boolean localWeatherAPI;

    public WeatherService(WeatherRepo weatherRepo) {
        builder = WebClient.builder();
        this.weatherRepo = weatherRepo;
    }

    // Used for the fake run generation (Use realistic data in future)
    public Weather getFakeWeather() {
        Weather fakeWeather = new Weather();

        fakeWeather.setTemperature("1010");
        fakeWeather.setRelativeHumidity("100");
        fakeWeather.setUncorrectedBarometer("12345");
        fakeWeather.setCorrectedBarometer("54312");
        fakeWeather.setWindSpeed("52354");
        fakeWeather.setWindDirection("523432");
        fakeWeather.setDewPoint("13245");
        fakeWeather.setSaturationPressure("523");
        fakeWeather.setVaporPressure("432");
        fakeWeather.setGrains("5423");
        fakeWeather.setAirDensityNoWaterVapor("3245");
        fakeWeather.setAirDensityWithWaterVapor("5234");
        fakeWeather.setDensityAltitude("523432");
        return fakeWeather;
    }

    // Used to generate real runs (User Selects)
    public Weather getCurrentWeather(String raceTrack, LocalDate date, LocalTime time) {
        try {
            URI weatherURI = UriComponentsBuilder
                    .fromUri(getAPIURL())
                    .queryParam("trackName", raceTrack)
                    .queryParam("date", date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .queryParam("time", time.format(DateTimeFormatter.ofPattern("HH:mm")))
                    .build()
                    .toUri();
            System.out.println(weatherURI);

            String jsonResponse = builder.build()
                    .get()
                    .uri(weatherURI)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return parseWeatherAPIResponse(jsonResponse);
        } catch (Exception e) {
            System.out.println("Weather API Most likely down!" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateWeather(Run runToEdit, String newTrack, LocalDate editDate, LocalTime editTime) {
        Weather currentWeather = runToEdit.getWeather();

        // Get new weather from new track
        Weather newWeather = getCurrentWeather(newTrack, editDate, editTime);
        if (newWeather == null) {
            Notification.show("Weather API down, please try again later...");
            return false; // Weather was not updated (API Error)
        }

        // Set currentWeather attributes to newWeather attributes
        currentWeather.setTemperature(newWeather.getTemperature());
        currentWeather.setRelativeHumidity(newWeather.getRelativeHumidity());
        currentWeather.setUncorrectedBarometer(newWeather.getUncorrectedBarometer());
        currentWeather.setCorrectedBarometer(newWeather.getCorrectedBarometer());
        currentWeather.setWindSpeed(newWeather.getWindSpeed());
        currentWeather.setWindDirection(newWeather.getWindDirection());
        currentWeather.setDewPoint(newWeather.getDewPoint());
        currentWeather.setSaturationPressure(newWeather.getSaturationPressure());
        currentWeather.setVaporPressure(newWeather.getVaporPressure());
        currentWeather.setGrains(newWeather.getGrains());
        currentWeather.setAirDensityNoWaterVapor(newWeather.getAirDensityNoWaterVapor());
        currentWeather.setAirDensityWithWaterVapor(newWeather.getAirDensityWithWaterVapor());
        currentWeather.setDensityAltitude(newWeather.getDensityAltitude());

        // Set the created run's weather
        runToEdit.setWeather(currentWeather);

        // Save weather to H2 database
        weatherRepo.save(currentWeather);

        return true;
    }

    private URI getAPIURL() throws URISyntaxException {
        if(localWeatherAPI) { // Using a locally hosted API
            return new URI("http://localhost:8081/weather/by-datetime");
        } else { // Using externally hosted API
            // TODO: CHANGE
            return new URI("https://trackweatherapi-production.up.railway.app/weather");
        }
    }

    private Weather parseWeatherAPIResponse(String jsonResponse) {
        Weather parsedWeather = new Weather();
        // Parse the root JSON object
        JsonObject root = JsonParser.parseString(jsonResponse).getAsJsonObject();

        // Get the "data" array
        JsonArray dataArray = root.getAsJsonArray("data");

        // Get the first element from the "data" array
        JsonObject dataRow = dataArray.get(0).getAsJsonObject();

        parsedWeather.setTemperature(dataRow.get("temp").getAsString());
        parsedWeather.setRelativeHumidity(dataRow.get("humidity").getAsString());
        parsedWeather.setUncorrectedBarometer("null");
        parsedWeather.setCorrectedBarometer("null");
        parsedWeather.setWindSpeed(dataRow.get("wind_speed").getAsString());
        parsedWeather.setWindDirection("null");
        parsedWeather.setDewPoint(dataRow.get("dew_point").getAsString());
        parsedWeather.setSaturationPressure("null");
        parsedWeather.setVaporPressure("null");
        parsedWeather.setGrains("null");
        parsedWeather.setAirDensityNoWaterVapor("null");
        parsedWeather.setAirDensityWithWaterVapor("null");
        parsedWeather.setDensityAltitude("null");
        return parsedWeather;
    }
}
