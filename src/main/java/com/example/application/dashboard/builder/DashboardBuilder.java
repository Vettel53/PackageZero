package com.example.application.dashboard.builder;

import com.example.application.account.AppUser;
import com.example.application.dashboard.dialogs.AddDialog;
import com.example.application.dashboard.dialogs.DeleteDialog;
import com.example.application.dashboard.dialogs.EditDialog;
import com.example.application.weather.Weather;
import com.example.application.run.Run;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
@UIScope
public class DashboardBuilder {
    private final AddDialog addDialog;
    private final EditDialog editDialog;
    private final DeleteDialog deleteDialog;
    // TODO: Probably change methods to default visibility

    public DashboardBuilder(AddDialog addDialog, EditDialog editDialog, DeleteDialog deleteDialog) {
        this.addDialog = addDialog;
        this.editDialog = editDialog;
        this.deleteDialog = deleteDialog;
    }

    public HorizontalLayout buildMainHorizontalLayout(AppUser loggedInAppUser) {
        // Create a new horizontal layout to hold the add run button
        HorizontalLayout hl = new HorizontalLayout();

        // Create add run button
        Button addRunButton = new Button("Add New Run");
        addRunButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addRunButton.addClickShortcut(Key.ENTER);

        // Add event listener to add click event
        addRunButton.addClickListener(event -> {
            // Load the entry run dialog when addRunButton is clicked
            addDialog.showAddRunDialog(loggedInAppUser);
            Notification.show("ADD BUTTON CLICKED");
        });

        // Add run button to horizontalLayout
        hl.add(addRunButton);
        hl.addClassNames(LumoUtility.Padding.MEDIUM);

        return hl;
    }

    public Grid<Run> buildGridLayout(Grid<Run> grid, ListDataProvider<Run> dataProvider) {
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        grid.setDataProvider(dataProvider);
        grid.setHeight("500px");

        // Remove all auto-generated columns by Vaadin
        grid.removeAllColumns();

        // Add each column individually and what attributes/features they will need
        grid.addColumn(Run::getId).setHeader("ID").setFrozen(true).setAutoWidth(true).setSortable(true);
        grid.addColumn(Run::getDate).setHeader("Date").setAutoWidth(true).setSortable(true);
        grid.addColumn(Run::getTime).setHeader("Time").setAutoWidth(true).setSortable(true);
        grid.addColumn(Run::getCar).setHeader("Car").setAutoWidth(true).setSortable(true);
        grid.addColumn(Run::getDriver).setHeader("Driver").setAutoWidth(true).setSortable(true);
        grid.addColumn(Run::getTrack).setHeader("Track").setAutoWidth(true).setSortable(true);
        grid.addColumn(Run::getLane).setHeader("Lane").setAutoWidth(true).setSortable(true);
        grid.addColumn(Run::getDial).setHeader("Dial").setAutoWidth(true).setSortable(true);
        grid.addColumn(Run::getReaction).setHeader("Reaction").setAutoWidth(true).setSortable(true);
        grid.addColumn(Run::getSixtyFoot).setHeader("60'").setAutoWidth(true).setSortable(true);
        grid.addColumn(Run::getHalfTrack).setHeader("330'").setAutoWidth(true).setSortable(true);
        grid.addColumn(Run::getFullTrack).setHeader("660'").setAutoWidth(true).setSortable(true);
        grid.addColumn(Run::getSpeed).setHeader("Speed").setAutoWidth(true).setSortable(true);

        // TODO: Understand where it's referncing this run in the lamda expression
        grid.addComponentColumn(run -> {

            // Edit button
            Button editButton = new Button(VaadinIcon.EDIT.create());
            editButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            editButton.addClickListener(event -> {
                Run runToEdit = run;

                // Open the edit dialog with the existing run data
                editDialog.loadEditRunDialog(runToEdit);
                Notification.show("Edit");
            });

            // Delete Button
            Button deleteButton = new Button(VaadinIcon.TRASH.create());
            deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
            // Click listener
            deleteButton.addClickListener(event -> {
                Run runToDelete = run;

                // Open delete dialog and ask user to delete or not delete
                deleteDialog.loadConfirmDeleteDialog(runToDelete);
                Notification.show("Delete");
            });

            // Return the delete and edit buttons as a HBox layout inside each run entry
            return new HorizontalLayout(editButton, deleteButton);
        }).setHeader("Manage").setAutoWidth(true).setFrozenToEnd(true);
        
        grid.setItemDetailsRenderer(new ComponentRenderer<>(run -> {
            FormLayout detailsLayout = new FormLayout();

            TextField temperature = new TextField("Temperature");
            TextField relativeHumidity = new TextField("Relative Humidity");
            TextField uncorrectedBarometer = new TextField("Uncorrected Barometer");
            TextField correctedBarometer = new TextField("Corrected Barometer");
            TextField windSpeed = new TextField("Wind Speed");
            TextField windDirection = new TextField("Wind Direction");
            TextField dewPoint = new TextField("Dew Point");
            TextField saturationPressure = new TextField("Saturation Pressure");
            TextField vaporPressure = new TextField("Vapor Pressure");
            TextField grains = new TextField("Grains");
            TextField airDensityNoWaterVapor = new TextField("Air Density W/O Water Vapor");
            TextField airDensityWithWaterVapor = new TextField("Air Density With Water Vapor");
            TextField densityAltitude = new TextField("Density Altitude");

            // Get Weather for run
            Weather weather = run.getWeather();

            // Set Values For Fields
            temperature.setValue(weather.getTemperature());
            relativeHumidity.setValue(weather.getRelativeHumidity());
            uncorrectedBarometer.setValue(weather.getUncorrectedBarometer());
            correctedBarometer.setValue(weather.getCorrectedBarometer());
            windSpeed.setValue(weather.getWindSpeed());
            windDirection.setValue(weather.getWindDirection());
            dewPoint.setValue(weather.getDewPoint());
            saturationPressure.setValue(weather.getSaturationPressure());
            vaporPressure.setValue(weather.getVaporPressure());
            grains.setValue(weather.getGrains());
            airDensityNoWaterVapor.setValue(weather.getAirDensityNoWaterVapor());
            airDensityWithWaterVapor.setValue(weather.getAirDensityWithWaterVapor());
            densityAltitude.setValue(weather.getDensityAltitude());

            // Add Weather fields to the details layout
            Stream.of(temperature, relativeHumidity, uncorrectedBarometer, correctedBarometer,
                            windSpeed, windDirection, dewPoint, saturationPressure, vaporPressure,
                            grains, airDensityNoWaterVapor, airDensityWithWaterVapor, densityAltitude)
                    .forEach(field -> {
                        field.setReadOnly(true);
                        detailsLayout.add(field);
                    });

            return detailsLayout;
        }));

        grid.setDetailsVisibleOnClick(true);

        return grid;
    }

}
