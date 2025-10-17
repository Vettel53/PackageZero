package com.example.application.dashboard.dialogs;

import com.example.application.account.AppUser;
import com.example.application.dashboard.DashboardService;
import com.example.application.run.Run;
import com.example.application.run.FakeRunGenerationService;
import com.example.application.run.RunService;
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
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@UIScope
public class AddDialog {

    @Value("${runs.fake-generation.button}")
    private boolean fakeRunGenerationButton;

    static final String REGEX_PATTERN = "\\d{1,2}\\.\\d{1,4}";
    static final String ALLOWED_CHARACTER_PATTERN = "[0-9.]";
    private final RunService runService;
    DatePicker datePicker;
    TimePicker timePicker;
    TextField car;
    TextField driver;
    ComboBox<String> trackSelection;
    Select<String> lane;
    TextField dial;
    TextField reaction ;
    TextField sixtyFoot;
    TextField halfTrack;
    TextField fullTrack;
    TextField speed;

    Run createdRun;

    private final DashboardService dashboardService;

    public AddDialog(DashboardService dashboardService, RunService runService) {
        this.dashboardService = dashboardService;
        this.runService = runService;
    }

    public void showAddRunDialog(AppUser loggedInAppUser) {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("New Run");

        VerticalLayout dialogLayout = createRunEntryDialogLayout();
        dialog.add(dialogLayout);

        Button addRunButton = new Button("Save");
        Button cancelRunButton = new Button("Cancel");
        dialog.getFooter().add(cancelRunButton);
        dialog.getFooter().add(addRunButton);

        if (fakeRunGenerationButton) {
            Button fakeRunButton = buildFakeGenerationButton(loggedInAppUser);
            dialog.getFooter().add(fakeRunButton);
        }

        dialog.open();

        addRunButton.addClickListener(event -> {
            if (checkIfFieldsAreNull()) {
                return;
            }

            createdRun = new Run(
                    loggedInAppUser,
                    datePicker.getValue(),
                    timePicker.getValue(),
                    car.getValue(),
                    driver.getValue(),
                    trackSelection.getValue(),
                    lane.getValue(),
                    new BigDecimal(dial.getValue()),
                    new BigDecimal(reaction.getValue()),
                    new BigDecimal(sixtyFoot.getValue()),
                    new BigDecimal(halfTrack.getValue()),
                    new BigDecimal(fullTrack.getValue()),
                    new BigDecimal(speed.getValue())
            );

            runService.constructRunEntry(createdRun);

            if (createdRun == null) {
                Notification.show("Weather API down, please try again later...");
                return;
            }

            dialog.close();

            // Refresh grid
            dashboardService.callUpdateGridAfterAdd(createdRun);

            Notification.show("Run saved successfully", 3000, Notification.Position.TOP_CENTER);
        });

        cancelRunButton.addClickListener(event -> {
            dialog.close();
            Notification.show("Cancelled adding run", 3000, Notification.Position.TOP_CENTER);
        });
    }

    private Button buildFakeGenerationButton(AppUser loggedInAppUser) {
        Button fakeRunsButton = new Button("Generate Fake Batch Runs");
        fakeRunsButton.addClickListener(event -> {
            for (int i = 0; i < 5; i++) {
                // Generate the fake run then refresh the grid
                Run fakeRun = FakeRunGenerationService.generateFakeRun(loggedInAppUser);
                runService.constructRunEntry(fakeRun);
                dashboardService.callUpdateGridAfterAdd(fakeRun);
            }
            Notification.show("Created (5) fake runs...", 3000, Notification.Position.TOP_CENTER);
        });

        return fakeRunsButton;
    }

    private boolean checkIfFieldsAreNull() {
        Notification errorNotification = new Notification("", 3000, Notification.Position.TOP_CENTER);
        errorNotification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        if (datePicker.getValue() == null) {
            errorNotification.setText("Date is an invalid value!!");
            errorNotification.open();
            return true;
        }
        if (datePicker.getValue() == null) {
            errorNotification.setText("Date is an invalid value!!");
            errorNotification.open();
            return true;
        }
        if (timePicker.getValue() == null) {
            errorNotification.setText("Time is an invalid value!!");
            errorNotification.open();
            return true;
        }
        if (car.getValue().isEmpty() || car.isInvalid()) {
            errorNotification.setText("Car is an invalid value!!");
            errorNotification.open();
            return true;
        }
        if (driver.getValue().isEmpty() || driver.isInvalid()) {
            errorNotification.setText("Driver is an invalid value!!");
            errorNotification.open();
            return true;
        }
        if (trackSelection.getValue() == null) {
            errorNotification.setText("Track is an invalid value!!");
            errorNotification.open();
            return true;
        }
        if (lane.getValue() == null) {
            errorNotification.setText("Lane is an invalid value!!");
            errorNotification.open();
            return true;
        }
        if (dial.getValue().isEmpty() || dial.isInvalid()) {
            errorNotification.setText("Dial is an invalid value!!");
            errorNotification.open();
            return true;
        }
        if (reaction.getValue().isEmpty() || reaction.isInvalid()) {
            errorNotification.setText("Reaction is an invalid value!!");
            errorNotification.open();
            return true;
        }
        if (sixtyFoot.getValue().isEmpty() || sixtyFoot.isInvalid()) {
            errorNotification.setText("60' is an invalid value!!");
            errorNotification.open();
            return true;
        }
        if (halfTrack.getValue().isEmpty() || halfTrack.isInvalid()) {
            errorNotification.setText("330' is an invalid value!!");
            errorNotification.open();
            return true;
        }
        if (fullTrack.getValue().isEmpty() || fullTrack.isInvalid()) {
            errorNotification.setText("660' is an invalid value!!");
            errorNotification.open();
            return true;
        }
        if (speed.getValue().isEmpty() || speed.isInvalid()) {
            errorNotification.setText("Speed is an invalid value!!");
            errorNotification.open();
            return true;
        }
        return false;
    }

    private VerticalLayout createRunEntryDialogLayout() {
        FormLayout formLayout = new FormLayout();

        formLayout.add(
                createDatePicker(),
                createTimePicker(),
                createCarField(),
                createDriverField(),
                createTrackSelectionBox(),
                createLaneSelect(),
                createDialField(),
                createReactionField(),
                createSixtyFootField(),
                createHalfTrackField(),
                createFullTrackField(),
                createSpeedField()
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

    // Bunch of Field Builder methods
    private DatePicker createDatePicker() {
        datePicker = new DatePicker("Date");
        datePicker.setRequired(true);
        return datePicker;
    }

    private TimePicker createTimePicker() {
        timePicker = new TimePicker("Time");
        timePicker.setRequired(true);
        // Add constraints if needed
        return timePicker;
    }

    private TextField createCarField() {
        car = new TextField("Car");
        car.setRequired(true);
        car.setMinLength(2);
        car.setMaxLength(50);
        car.setErrorMessage("Car name must be between 2 and 50 characters");
        car.setHelperText("e.g: 21' Miller Dragster");
        return car;
    }

    private TextField createDriverField() {
        driver = new TextField("Driver");
        driver.setRequired(true);
        driver.setMinLength(2);
        driver.setMaxLength(50);
        driver.setErrorMessage("Driver name must be between 2 and 50 characters");
        driver.setHelperText("e.g: Verstappen");
        return driver;
    }

    private Select<String> createLaneSelect() {
        lane = new Select<>();
        lane.setLabel("Select Lane");
        lane.setItems("Left", "Right");
        lane.setEmptySelectionAllowed(false);
        lane.setErrorMessage("Lane must be chosen");
        return lane;
    }

    private TextField createDialField() {
        dial = new TextField("Dial");
        dial.setRequired(true);
        // Must be in the 12.4567 format, allowing 00.0000 to 99.9999
        dial.setPattern(REGEX_PATTERN);
        dial.setAllowedCharPattern(ALLOWED_CHARACTER_PATTERN);
        dial.setErrorMessage("Dial must be between 00.00 to 99.9999 seconds");
        dial.setHelperText("e.g: 4.49");
        return dial;
    }

    private TextField createReactionField() {
        reaction = new TextField("Reaction");
        reaction.setRequired(true);
        // Must be in the 12.4567 format, allowing 00.0000 to 99.9999
        reaction.setPattern(REGEX_PATTERN);
        reaction.setAllowedCharPattern(ALLOWED_CHARACTER_PATTERN);
        reaction.setErrorMessage("Reaction must be between 0.0000 to 9.9999 seconds");
        reaction.setHelperText("e.g: 0.0022");
        return reaction;
    }

    private TextField createSixtyFootField() {
        sixtyFoot = new TextField("60' Foot");
        sixtyFoot.setRequired(true);
        // Must be in the 12.4567 format, allowing 00.0000 to 99.9999
        sixtyFoot.setPattern(REGEX_PATTERN);
        sixtyFoot.setAllowedCharPattern(ALLOWED_CHARACTER_PATTERN);
        sixtyFoot.setErrorMessage("60' must be between 00.0000 to 99.9999 seconds");
        sixtyFoot.setHelperText("e.g: 1.0374");
        return sixtyFoot;
    }

    private TextField createHalfTrackField() {
        halfTrack = new TextField("330' Track");
        halfTrack.setRequired(true);
        // Must be in the 12.4567 format, allowing 00.0000 to 99.9999
        halfTrack.setPattern(REGEX_PATTERN);
        halfTrack.setAllowedCharPattern(ALLOWED_CHARACTER_PATTERN);
        halfTrack.setErrorMessage("330' time must be between 00.0000 to 99.9999 seconds");
        halfTrack.setHelperText("e.g: 2.9055");
        return halfTrack;
    }

    private TextField createFullTrackField() {
        fullTrack = new TextField("660' Track");
        fullTrack.setRequired(true);
        // Must be in the 12.4567 format, allowing 00.0000 to 99.9999
        fullTrack.setPattern(REGEX_PATTERN);
        fullTrack.setAllowedCharPattern(ALLOWED_CHARACTER_PATTERN);
        fullTrack.setErrorMessage("660' time must be between 00.0000 to 99.9999 seconds");
        fullTrack.setHelperText("e.g: 4.4997");
        return fullTrack;
    }

    private TextField createSpeedField() {
        speed = new TextField("Speed MPH");
        speed.setRequired(true);
        // Must be in the 123.456 format, allowing 000.000 to 999.999
        speed.setPattern("\\d{1,3}\\.\\d{1,4}");
        speed.setAllowedCharPattern(ALLOWED_CHARACTER_PATTERN);
        speed.setErrorMessage("Speed must be between 0.000 and 999.999 MPH");
        speed.setHelperText("e.g: 153.11");
        return speed;
    }

    // Maybe a better way to do this?
    ComboBox<String> createTrackSelectionBox() {
        // Initialize ComboBox with tracks
        trackSelection = new ComboBox<>("Tracks");
        trackSelection.setRequired(true);
        trackSelection.setErrorMessage("Track must be selected");
        trackSelection.setItems(
                "Alabama International Dragway",
                "Atmore Dragway",
                "Baileyton Dragstrip",
                "Cottonwood Dragway",
                "Holiday Raceway",
                "Huntsville Dragway",
                "I22 Motorsports Park",
                "Jakes Dragstrip",
                "Lassiter Mountain Dragway",
                "Mobile Dragway",
                "Montgomery Raceway Park",
                "Phenix Motorsports Park",
                "US-90 Dragway",
                "Alaska Raceway Park",
                "Dome Valley Raceway",
                "Firebird Motorsports Park",
                "Gila Bend Municipal Airport",
                "Tucson Dragway",
                "Centerville Dragway",
                "ECTA Arkansas Challenge",
                "George Rays Wildcat Dragstrip",
                "Prescott Raceway",
                "Auto Club Raceway at Pomona",
                "Avenal Sand Drags",
                "Banning Street Drags",
                "Barona Eighth Mile Drags",
                "Chuckwalla Valley Raceway",
                "Dumont Dunes",
                "El Mirage Dry Lake",
                "Famoso Raceway",
                "Fontana Drags",
                "Irwindale Speedway",
                "Kingdon Drags",
                "Lake Ming",
                "Qualcom Stadium",
                "Redding Dragstrip",
                "Sacramento Raceway",
                "Samoa Drag Strip",
                "Soboba Casino",
                "Sonoma Raceway",
                "Bandimere Speedway",
                "Front Range Airport",
                "Julesburg Dragstrip",
                "Pikes Peak International Raceway",
                "Pueblo Motorsports Park",
                "Western Colorado Dragway",
                "US 13 Dragway",
                "Bradenton Motorsports Park",
                "Emerald Coast Dragway",
                "Gainesville Raceway",
                "Immokalee Regional Raceway",
                "Orlando Speed World",
                "Palm Beach International Raceway",
                "Powerhouse Motorsports Park",
                "ShowTime Dragstrip",
                "ShowTime Speedway",
                "Atlanta Dragway",
                "Brainerd Motorsports Park",
                "Head Hunter Dragway",
                "LaGrange-Troup County Dragway",
                "Middle Georgia Motorsports Park",
                "Paradise Dragstrip",
                "RM Motorsports park",
                "Savannah River Dragway",
                "Screven Speedway",
                "Silver Dollar Raceway",
                "South Georgia Motorsports Park",
                "US 19 Dragway",
                "Hilo Dragstrip",
                "Kauai Raceway Park",
                "Maui Raceway Park",
                "Firebird Raceway",
                "Sage Raceway",
                "Accelaquarter Raceway",
                "Byron Dragway",
                "Central Illinois Dragway",
                "Coles County Dragway",
                "Cordova International Raceway",
                "I57 Dragstrip",
                "Route 66 Raceway",
                "World Wide Technology Raceway",
                "Brown County Dragway",
                "Bunker Hill Dragstrip",
                "Greater Evansville Dragway",
                "Lucas Oil Raceway",
                "Muncie Dragway",
                "Osceola Dragway",
                "US 41 Dragway",
                "Wabash Valley Dragway",
                "Wagler Motorsports Park",
                "Cedar Falls Motorsports Park",
                "Eddyville Raceway Park",
                "I29 Dragway",
                "North Iowa Dragway",
                "Onawa Dragway",
                "Tri-State Raceway",
                "Kansas International Dragway",
                "Mid America Dragway",
                "Midwest Raceway",
                "S. R. C. A. Dragstrip",
                "Beacon Dragway",
                "Beechbend Raceway Park",
                "Decker Boys Raceway",
                "I-64 Motorplex",
                "Kentucky Dragway",
                "London Dragway",
                "Ohio Valley Dragway",
                "Thornhill Dragstrip",
                "US 60 Dragway",
                "Windy Hollow Dragway",
                "No Problem Raceway Park",
                "Platinum Raceway",
                "State Capitol Raceway",
                "Twin City Raceway",
                "New Oxford Dragway",
                "Winterport Dragway",
                "75-80 Dragway",
                "Capitol Raceway",
                "Cecil County Dragway",
                "Maryland International Raceway",
                "Mason-Dixon Dragway",
                "Lapeer International Dragway",
                "Mid Michigan Motorplex",
                "Milan Dragway",
                "Northern Michigan Dragway",
                "Onondaga Dragway",
                "The Ubly Dragway",
                "US 131 Motorsports Park",
                "Brainerd International Raceway",
                "Grove Creek Raceway",
                "Top End Dragways",
                "Battlefield Dragstrip",
                "Byhalia Raceway",
                "Finishline Dragstrip",
                "Gulfport Dragway",
                "Holly Springs Motorsports",
                "Jackson Dragway Park",
                "Street Racing Haven",
                "Benton Speedway",
                "Bonne Terre Drag Strip",
                "Flying H Dragstrip",
                "Jeffers Motorsports Park",
                "Mo-Kan Dragway",
                "Ozark Raceway Park",
                "Thunder Valley Raceway",
                "Thunder Valley Sand Drags",
                "US 36 Raceway",
                "Lewistown Raceway",
                "Lost Creek Raceway",
                "Phillips County Motorsports",
                "Yellowstone Drag Strip",
                "Kearney Raceway Park",
                "Las Vegas Motor Speedway",
                "Northern Nevada Racing Association",
                "Top Gun Raceway",
                "New England Dragway",
                "Island Dragway",
                "Old Bridge Township Raceway Park",
                "Albuquerque Dragway",
                "Alien City Dragway",
                "Arroyo Seco Raceway",
                "Hobbs Motorsports Park",
                "Calverton Executive Airpark",
                "ESTA Safety Park Dragstrip",
                "Empire Dragway",
                "Lancaster Motorplex",
                "Lebanon Valley Speedway",
                "Skyview Drags",
                "710 Dragway",
                "Brewers Speedway",
                "Charlotte Motor Speedway",
                "Coastal Plains Dragway",
                "Farmington Dragway",
                "Fayetteville Dragway",
                "Galot Motorsports Park",
                "Harrells Raceway",
                "Kinston Dragstrip",
                "Mooresville Dragway",
                "Nahunta Dragway",
                "New Thunder Valley Raceway",
                "Northeast Dragway",
                "Outer Banks Speedway",
                "Piedmont Dragway",
                "Rico Dragstrip",
                "Rockingham Dragway",
                "Roxboro Motorsports Dragway",
                "Shadyside Dragway",
                "Wilkesboro Dragway",
                "zMax Dragway",
                "Magic City International Dragway",
                "Dragway 42",
                "Dragway of Magnolia",
                "Edgewater Sports Park",
                "Free Bird Dragway",
                "KD Dragway",
                "Kil-Kare Raceway",
                "Marion County Raceway",
                "National Trail Raceway",
                "Pacemakers Dragway Park",
                "Quaker City Motorsports Park",
                "Summit Motorsports Park",
                "Thompson Raceway Park",
                "Wilmington Airport",
                "Ardmore Dragway",
                "Grand Lake",
                "Hinton Street Races",
                "Lake El Reno",
                "Outlaw Dragway",
                "Sayre Street Races",
                "Thunder Valley Raceway Park",
                "Tulsa Raceway Park",
                "Coos Bay Speedway",
                "Madras Dragstrip",
                "Medford Dragstrip",
                "Portland Raceway",
                "Woodburn Dragstrip",
                "Beaver Springs Dragway",
                "Keystone Raceway Park",
                "Lucky Drag City",
                "Maple Grove Raceway",
                "Numidia Dragway",
                "South Mountain Raceway",
                "Salinas Speedway",
                "Bowman Dragway",
                "Carolina Dragway",
                "Darlington Dragway",
                "Greer Dragway",
                "Pageland Dragway",
                "South Carolina Motorplex",
                "Union County Dragway",
                "Ware Shoals Dragway",
                "Oahe Speedway",
                "Sturgis Dragway",
                "Thunder Valley Dragways",
                "Bristol Dragway",
                "Bristol Motor Speedway",
                "Cherokee Race Park",
                "Clarksville Speedway",
                "Crossville Dragway",
                "Jackson Dragway",
                "Knoxville Dragstrip",
                "Memphis International Raceway",
                "Middle Tennessee Dragway",
                "Music City Raceway",
                "US 43 Dragway",
                "Alamo City Motorplex",
                "Amarillo Dragway",
                "Ben Bruce Memorial Airpark",
                "Big Country Race Way",
                "Caprock Motorplex",
                "Cedar Creek Dragway",
                "Concho Valley Dragway",
                "Desert Thunder Raceway-Texas",
                "Edinburg Motorsports Park",
                "El Paso Motorplex",
                "Houston Motorsports Park",
                "I-30 Dragway",
                "Indian Valley Raceway",
                "Little River Dragway",
                "Lonestar Motorsports Park",
                "Lubbock Dragway",
                "North Star Dragway",
                "Paris Dragstrip",
                "Penwell Knights Raceway",
                "Pine Valley Raceway Park",
                "San Antonio Raceway",
                "Texas Motorplex",
                "The Texas Mile",
                "Wichita Raceway Park",
                "Xtreme Raceway Park",
                "Yellow Belly Dragstrip",
                "Bonneville Speedway",
                "Diamond Mountain Dragway",
                "Colonial Beach Dragway",
                "Eastside Speedway",
                "Elk Creek Dragway",
                "London Dragway",
                "Motor Mile Dragway",
                "Natural Bridge Dragstrip",
                "Richmond Dragway",
                "Sumerduck Dragway",
                "Virginia Motorsports Park",
                "Bremerton Raceway",
                "Pacific Raceways",
                "Renegade Raceway",
                "Spokane County Raceway",
                "Walla Walla Drag Strip",
                "Potomac Airpark",
                "Tri-River Dragway",
                "Great Lakes Dragaway",
                "Rock Falls Raceway",
                "Wisconsin International Raceway",
                "Central Wyoming Motorsports Park",
                "Hypoxia Dragway",
                "Dezzi Raceway",
                "Killarney International Raceway",
                "Midvaal Raceway",
                "Tarlton International Raceway",
                "The Rock Raceway",
                "Amisfield Dragstrip",
                "Bruce McLaren Motorsports Park",
                "Masterton Motorplex",
                "Meremere Dragway",
                "Motueka Airport",
                "Pegasus Bay Drag Racing Club",
                "Finnish Hot Rod Association",
                "Kauhava Airport",
                "Motopark Raceway",
                "Circuit de Clastres",
                "Hockenheimring",
                "Kiskunlachaza Drag",
                "Kvartmilubrautin",
                "Hal Far Raceway",
                "Gardermoen Raceway",
                "Hudik Raceway",
                "Malmo Raceway",
                "Mantorp Park",
                "Sundsvall Raceway",
                "Tierp Arena",
                "Melbourne Raceway",
                "Santa Pod Raceway",
                "Amazonas Dragway",
                "Arena Race Multieventos",
                "Autodromo Internacional de Curitiba",
                "Autodromo de Taruma",
                "Mega Space",
                "Race Park Maringa",
                "Race Valley Motorsports Park",
                "Sao Paulo International Dragway",
                "Speed Park Franca",
                "Toledo Dragway",
                "Velopark",
                "Autodromo Sunix",
                "Curacao International Raceway",
                "Palo Marga International Raceway",
                "Time Line Events",
                "Parkers Raceway",
                "Autodromo Motorsports Park",
                "Autodromo Guadalajara",
                "Autodromo Monclava",
                "Autodromo Monterrey",
                "Autodromo de Hermosillo",
                "Samalayuca Dragway",
                "Sonora Autodromo Cerro Colorado",
                "Autodromo Internacional de Turagua Pancho Pepe Croquer",
                "The Valley Dragway",
                "Area 53 Raceway",
                "Central Alberta Raceways",
                "Medicine Hat Dragway",
                "Rad Torque Raceway",
                "Mission Raceway Park",
                "Nitro Motorsports Park",
                "Northern Lights Raceway",
                "Gimli Motorsports Park",
                "Miramichi Dragway Park",
                "Eastbound Park",
                "Cape Breton Dragway",
                "Grand Bend Motorplex",
                "Shannonville Motorsports Park",
                "St Thomas Raceway Park",
                "Toronto Motorsports Park",
                "Autodrome Montmagny",
                "Drag BSL",
                "Luskville Dragway",
                "Napierville Dragway",
                "Saskatchewan Intl Raceway",
                "Swift Current Airport",
                "Dubbo City Car Club",
                "Sydney Dragway",
                "Alice Springs Inland Dragway",
                "Hidden Valley Dragway",
                "Benaraby Raceway",
                "Ironbark Raceway",
                "Palmyra Dragway",
                "Springmount Raceway",
                "Warwick Dragway",
                "Wide Bay Motor Complex",
                "Willowbank Raceway",
                "Adelaide International Raceway",
                "Steel City Drag Club",
                "The Bend Motorsports Park",
                "TAS Dragway",
                "Calder Park Raceway",
                "Heathcote Park Raceway",
                "South Coast Raceway",
                "Sunset Strip",
                "Swan Hill Dragway",
                "Collie Motorplex",
                "Perth Motorplex",
                "Kuwait Motor Town",
                "Qatar Racing Club",
                "Yas Marina Circuit"
        );

        return trackSelection;
    }

}
