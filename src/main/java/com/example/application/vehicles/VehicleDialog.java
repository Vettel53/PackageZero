package com.example.application.vehicles;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class VehicleDialog {

    TextField car;

    private TextField carField;
    private final Binder<Vehicle> binder = new Binder<>(Vehicle.class);

    public void open() {
        Dialog dialog = createDialog();
        dialog.open();
    }

    private Dialog createDialog() {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("New Vehicle");

        VerticalLayout content = createFormLayout();
        dialog.add(content);

        dialog.getFooter().add(createCancelButton(dialog), createSaveButton(dialog));

        return dialog;
    }

    private VerticalLayout createFormLayout() {
        carField = new TextField("Car");
        binder.forField(carField)
                .asRequired("Car is required")
                .withValidator(name -> name.length() >= 2 && name.length() <= 50,
                        "Car name must be between 2 and 50 characters")
                .bind(Vehicle::getName, Vehicle::setName);

        FormLayout formLayout = new FormLayout(carField);
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("600px", 2));

        VerticalLayout layout = new VerticalLayout(formLayout);
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.setAlignItems(FlexComponent.Alignment.STRETCH);
        layout.setWidth("24rem");

        return layout;
    }

    private Button createSaveButton(Dialog dialog) {
        Button save = new Button("Save", e -> {
            if (binder.validate().isOk()) {
                Vehicle vehicle = new Vehicle();
                binder.writeBeanIfValid(vehicle); // save vehicle...
                Notification.show("Vehicle created successfully");
                dialog.close();
            }
        });
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return save;
    }

    private Button createCancelButton(Dialog dialog) {
        Button cancel = new Button("Cancel", e -> {
            dialog.close();
            Notification.show("Cancelled");
        });
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        return cancel;
    }

}
