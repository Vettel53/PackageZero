package com.example.application.vehicles;

import com.example.application.account.AppUser;
import com.example.application.components.CustomCard;
import com.example.application.views.MainView;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;

@Route(value = "vehicles", layout = MainView.class)
@PermitAll
@UIScope
public class VehicleView extends VerticalLayout {

    private final VehicleDialog vehicleDialog;

    public VehicleView(VehicleDialog vehicleDialog1) {
        this.vehicleDialog = vehicleDialog1;

        // Remove margins and spacing to prevent content overflow
        setMargin(false);
        setSpacing(false);
        setPadding(false);

        // Wrap content so long cards don't overflow horizontally
        getStyle().set("overflow-x", "hidden");

        add(buildMainHorizontalLayout());
        add(vehiclesLayout());
    }

    public HorizontalLayout buildMainHorizontalLayout() {
        Button addVehicleButton = createAddVehicleButton();
        HorizontalLayout hl = new HorizontalLayout(addVehicleButton);
        hl.addClassNames(LumoUtility.Padding.MEDIUM);
        hl.add(addVehicleButton);

        return hl;
    }

    public FlexLayout vehiclesLayout() {
        //HorizontalLayout vhl = new HorizontalLayout();
        Div card1 = createReactionTimeCard();
        Div card2 = createReactionTimeCard();

        FlexLayout layout = new FlexLayout(card1, card2);
        layout.getStyle().set("overflow-x", "hidden");
        layout.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        layout.setWidthFull();


        // this controls how wide they are
        card1.getStyle().set("flex", "1 1 45%");
        card2.getStyle().set("flex", "1 1 45%");
        card1.getStyle().set("margin", "10px");
        card2.getStyle().set("margin", "10px");
        return layout;
    }

    public Button createAddVehicleButton() {
        Button addVehicleButton = new Button("Add Vehicle");
        addVehicleButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addVehicleButton.addClickShortcut(Key.ENTER);

        addVehicleButton.addClickListener(event -> {
            vehicleDialog.open();
            Notification.show("add vehicle clicked");
        });

        return addVehicleButton;
    }

    private Div createReactionTimeCard() {
        String titleText = "Reaction Time";
        String subtitleText = "Average Reaction Time Of All Runs";
        String badgeText = "testing";
        String descriptionText = "This card displays the average reaction time based on the recorded runs.";
        String width = "50%";

        CustomCard customCard = new CustomCard();
        return customCard.createCustomCard(titleText, subtitleText, badgeText, descriptionText, width);
    }

}
