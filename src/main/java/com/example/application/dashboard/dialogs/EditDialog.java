package com.example.application.dashboard.dialogs;

import com.example.application.dashboard.DashboardService;
import com.example.application.run.Run;
import com.example.application.run.RunService;
import com.example.application.weather.WeatherService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

import static com.example.application.dashboard.dialogs.AddDialog.ALLOWED_CHARACTER_PATTERN;
import static com.example.application.dashboard.dialogs.AddDialog.REGEX_PATTERN;

import java.math.BigDecimal;

@Component
@UIScope
public class EditDialog {
    static final String SPEED_REGEX_PATTERN = "\\d{1,3}\\.\\d{1,4}";
    private final RunService runService;

    // "Edit Run" member variables
    private DatePicker editDatePicker;
    private TimePicker editTimePicker;
    private TextField editCar;
    private TextField editDriver;
    private ComboBox<String> editTrack;
    private Select<String> editLane;
    private TextField editDial;
    private TextField editReaction;
    private TextField editSixtyFoot;
    private TextField editHalfTrack;
    private TextField editFullTrack;
    private TextField editSpeed;

    private final DashboardService dashboardService;
    private final WeatherService weatherService;
    private final AddDialog addDialog;

    public EditDialog(DashboardService dashboardService, WeatherService weatherService, AddDialog addDialog, RunService runService) {
        this.dashboardService = dashboardService;
        this.weatherService = weatherService;
        this.addDialog = addDialog;
        this.runService = runService;
    }

    public void loadEditRunDialog(Run runToEdit) {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Edit Run");

        VerticalLayout dialogLayout = createEditRunLayout(runToEdit);
        dialog.add(dialogLayout);

        Button saveEditButton = new Button("Save");
        Button cancelEditButton = new Button("Cancel");
        dialog.getFooter().add(cancelEditButton);
        dialog.getFooter().add(saveEditButton);

        dialog.open();

        saveEditButton.addClickListener(event -> {

            if(checkIfEditFieldsAreNull()) {
                return; // An edit TextField field was null
            }

            if (!runToEdit.getTrack().equals(editTrack.getValue()) || !runToEdit.getDate().equals(editDatePicker.getValue()) || !runToEdit.getTime().equals(editTimePicker.getValue())) {
                // Pass new date and time values
                if(weatherService.updateWeather(runToEdit, editTrack.getValue(), editDatePicker.getValue(), editTimePicker.getValue())) {
                    System.out.println("Weather API sucessfully called!");
                } else {
                    Notification.show("Weather API down, please try again later...");
                    return;
                }
            }

            // Save the edited run into database
            runService.saveEditedRun(
                    runToEdit,
                    editDatePicker.getValue(),
                    editTimePicker.getValue(),
                    editCar.getValue(),
                    editDriver.getValue(),
                    editTrack.getValue(),
                    editLane.getValue(),
                    new BigDecimal(editDial.getValue()),
                    new BigDecimal(editReaction.getValue()),
                    new BigDecimal(editSixtyFoot.getValue()),
                    new BigDecimal(editHalfTrack.getValue()),
                    new BigDecimal(editFullTrack.getValue()),
                    new BigDecimal(editSpeed.getValue())
            );

            // Refresh the grid to reflect the changes
            dashboardService.callRefreshGrid();

            Notification.show("Successfully edited run ID: " + runToEdit.getId(), 3000, Notification.Position.TOP_CENTER);
            clearEditTextFields();

            dialog.close();
        });

        cancelEditButton.addClickListener(event -> {
            Notification.show("Cancelled editing run...", 3000, Notification.Position.TOP_CENTER);
            clearEditTextFields();

            dialog.close();
        });
    }

    private boolean checkIfEditFieldsAreNull() {
        Notification errorNotification = new Notification("", 3000, Notification.Position.TOP_CENTER);
        errorNotification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        if (editDatePicker.getValue() == null) {
            errorNotification.setText("Date is an invalid value!!");
            errorNotification.open();
            return true;
        }
        if (editTimePicker.getValue() == null) {
            errorNotification.setText("Time is an invalid value!!");
            errorNotification.open();
            return true;
        }
        if (editCar.getValue().isEmpty() || editCar.isInvalid()) {
            errorNotification.setText("Car is an invalid value!!");
            errorNotification.open();
            return true;
        }
        if (editDriver.getValue().isEmpty() || editDriver.isInvalid()) {
            errorNotification.setText("Driver is an invalid value!!");
            errorNotification.open();
            return true;
        }
        if (editTrack.getValue() == null) {
            errorNotification.setText("Track is an invalid value!!");
            errorNotification.open();
            return true;
        }
        if (editLane.getValue() == null) {
            errorNotification.setText("Lane is an invalid value!!");
            errorNotification.open();
            return true;
        }
        if (editDial.getValue().isEmpty() || editDial.isInvalid()) {
            errorNotification.setText("Dial is an invalid value!!");
            errorNotification.open();
            return true;
        }
        if (editReaction.getValue().isEmpty() || editReaction.isInvalid()) {
            errorNotification.setText("Reaction is an invalid value!!");
            errorNotification.open();
            return true;
        }
        if (editSixtyFoot.getValue().isEmpty() || editSixtyFoot.isInvalid()) {
            errorNotification.setText("60' is an invalid value!!");
            errorNotification.open();
            return true;
        }
        if (editHalfTrack.getValue().isEmpty() || editHalfTrack.isInvalid()) {
            errorNotification.setText("330' is an invalid value!!");
            errorNotification.open();
            return true;
        }
        if (editFullTrack.getValue().isEmpty() || editFullTrack.isInvalid()) {
            errorNotification.setText("660' is an invalid value!!");
            errorNotification.open();
            return true;
        }
        if (editSpeed.getValue().isEmpty() || editSpeed.isInvalid()) {
            errorNotification.setText("Speed is an invalid value!!");
            errorNotification.open();
            return true;
        }
        return false;
    }

    private VerticalLayout createEditRunLayout(Run runToEdit) {
        // Initialize instance variables and set their values to the runToEdit values
        // Initialize FormLayout with correct properties
        FormLayout formLayout = new FormLayout();

        formLayout.add(
                createEditDatePicker(runToEdit),
                createEditTimePicker(runToEdit),
                createEditCarField(runToEdit),
                createEditDriverField(runToEdit),
                createTrackSelectionField(runToEdit),
                createEditLaneSelect(runToEdit),
                createEditDialField(runToEdit),
                createEditReactionField(runToEdit),
                createEditSixtyFootField(runToEdit),
                createEditHalfTrackField(runToEdit),
                createEditFullTrackField(runToEdit),
                createEditSpeedField(runToEdit)
        );

        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1), new FormLayout.ResponsiveStep("600px", 2));

        // Initialize VerticalLayout with correct properties (FormLayout atm)
        VerticalLayout dialogLayout = new VerticalLayout(formLayout);
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "24rem").set("max-width", "100%");

        // Return VerticalLayout
        return dialogLayout;
    }

    private ComboBox<String> createTrackSelectionField(Run runToEdit) {
        editTrack = addDialog.createTrackSelectionBox();
        editTrack.setValue(runToEdit.getTrack());
        return editTrack;
    }

    private DatePicker createEditDatePicker(Run runToEdit) {
        editDatePicker = new DatePicker("Date");
        editDatePicker.setRequired(true);
        editDatePicker.setValue(runToEdit.getDate());
        return editDatePicker;
    }

    private TimePicker createEditTimePicker(Run runToEdit) {
        editTimePicker = new TimePicker("Time");
        editTimePicker.setRequired(true);
        editTimePicker.setValue(runToEdit.getTime());
        return editTimePicker;
    }

    private TextField createEditCarField(Run runToEdit) {
        editCar = new TextField("Car");
        editCar.setRequired(true);
        editCar.setMinLength(2);
        editCar.setMaxLength(50);
        editCar.setErrorMessage("Car name must be between 2 and 50 characters");
        editCar.setHelperText("e.g: 21' Miller Dragster");
        editCar.setValue(runToEdit.getCar());
        return editCar;
    }

    private TextField createEditDriverField(Run runToEdit) {
        editDriver = new TextField("Driver");
        editDriver.setRequired(true);
        editDriver.setMinLength(2);
        editDriver.setMaxLength(50);
        editDriver.setErrorMessage("Driver name must be between 2 and 50 characters");
        editDriver.setHelperText("e.g: Verstappen");
        editDriver.setValue(runToEdit.getDriver());
        return editDriver;
    }

    private Select<String> createEditLaneSelect(Run runToEdit) {
        editLane = new Select<>();
        editLane.setLabel("Select Lane");
        editLane.setItems("Left", "Right");
        editLane.setEmptySelectionAllowed(false);
        editLane.setErrorMessage("Lane must be chosen");
        editLane.setValue(runToEdit.getLane());
        return editLane;
    }

    private TextField createEditDialField(Run runToEdit) {
        editDial = new TextField("Dial");
        editDial.setRequired(true);
        // Must be in the 12.4567 format, allowing 00.0000 to 99.9999
        editDial.setPattern(REGEX_PATTERN);
        editDial.setAllowedCharPattern(ALLOWED_CHARACTER_PATTERN);
        editDial.setErrorMessage("Dial must be between 00.00 to 99.9999 seconds");
        editDial.setHelperText("e.g: 4.49");
        editDial.setValue(runToEdit.getDial() != null ? runToEdit.getDial().toString() : "");
        return editDial;
    }

    private TextField createEditReactionField(Run runToEdit) {
        editReaction = new TextField("Reaction");
        editReaction.setRequired(true);
        // Must be in the 12.4567 format, allowing 00.0000 to 9.9999
        editReaction.setPattern(REGEX_PATTERN);
        editReaction.setAllowedCharPattern(ALLOWED_CHARACTER_PATTERN);
        editReaction.setErrorMessage("Reaction must be between 0.0000 to 9.9999 seconds");
        editReaction.setHelperText("e.g: 0.0022");
        editReaction.setValue(runToEdit.getReaction() != null ? runToEdit.getReaction().toString() : "");
        return editReaction;
    }

    private TextField createEditSixtyFootField(Run runToEdit) {
        editSixtyFoot = new TextField("60' Foot");
        editSixtyFoot.setRequired(true);
        // Must be in the 12.4567 format, allowing 00.0000 to 99.9999
        editSixtyFoot.setPattern(REGEX_PATTERN);
        editSixtyFoot.setAllowedCharPattern(ALLOWED_CHARACTER_PATTERN);
        editSixtyFoot.setErrorMessage("60' must be between 00.0000 to 99.9999 seconds");
        editSixtyFoot.setHelperText("e.g: 1.0374");
        editSixtyFoot.setValue(runToEdit.getSixtyFoot() != null ? runToEdit.getSixtyFoot().toString() : "");
        return editSixtyFoot;
    }

    private TextField createEditHalfTrackField(Run runToEdit) {
        editHalfTrack = new TextField("330' Track");
        editHalfTrack.setRequired(true);
        // Must be in the 12.4567 format, allowing 00.0000 to 99.9999
        editHalfTrack.setPattern(REGEX_PATTERN);
        editHalfTrack.setAllowedCharPattern(ALLOWED_CHARACTER_PATTERN);
        editHalfTrack.setErrorMessage("330' time must be between 00.0000 to 99.9999 seconds");
        editHalfTrack.setHelperText("e.g: 2.9055");
        editHalfTrack.setValue(runToEdit.getHalfTrack() != null ? runToEdit.getHalfTrack().toString() : "");
        return editHalfTrack;
    }

    private TextField createEditFullTrackField(Run runToEdit) {
        editFullTrack = new TextField("660' Track");
        editFullTrack.setRequired(true);
        // Must be in the 12.4567 format, allowing 00.0000 to 99.9999
        editFullTrack.setPattern(REGEX_PATTERN);
        editFullTrack.setAllowedCharPattern(ALLOWED_CHARACTER_PATTERN);
        editFullTrack.setErrorMessage("660' time must be between 00.0000 to 99.9999 seconds");
        editFullTrack.setHelperText("e.g: 4.4997");
        editFullTrack.setValue(runToEdit.getFullTrack() != null ? runToEdit.getFullTrack().toString() : "");
        return editFullTrack;
    }

    private TextField createEditSpeedField(Run runToEdit) {
        editSpeed = new TextField("Speed MPH");
        editSpeed.setRequired(true);
        // Must be in the 123.4567 format, allowing 000.0000 to 999.9999
        editSpeed.setPattern(SPEED_REGEX_PATTERN);
        editSpeed.setAllowedCharPattern(ALLOWED_CHARACTER_PATTERN);
        editSpeed.setErrorMessage("Speed must be between 0.0000 and 999.9999 MPH");
        editSpeed.setHelperText("e.g: 153.1112");
        editSpeed.setValue(runToEdit.getSpeed() != null ? runToEdit.getSpeed().toString() : "");
        return editSpeed;
    }

    private void clearEditTextFields() {
        // Clear all text fields
        editDatePicker.clear();
        editTimePicker.clear();
        editCar.clear();
        editDriver.clear();
        editTrack.clear();
        editLane.clear();
        editDial.clear();
        editReaction.clear();
        editSixtyFoot.clear();
        editHalfTrack.clear();
        editFullTrack.clear();
        editSpeed.clear();
    }
}
