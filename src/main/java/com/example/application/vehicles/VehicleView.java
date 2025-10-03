package com.example.application.vehicles;

import com.example.application.account.AppUser;
import com.example.application.views.MainView;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.notification.Notification;
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
        add(buildMainHorizontalLayout());
    }

    public HorizontalLayout buildMainHorizontalLayout() {
        Button addVehicleButton = createAddVehicleButton();
        HorizontalLayout hl = new HorizontalLayout(addVehicleButton);
        hl.addClassNames(LumoUtility.Padding.MEDIUM);
        hl.add(addVehicleButton);

        return hl;
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

}