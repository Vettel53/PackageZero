package com.example.application.account.accountcreation.builder;

import com.example.application.account.accountcreation.AccountCreationService;
import com.example.application.account.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class AccountCreationBuilder {

    private final AccountCreationService accountCreationService;
    private final UserService userService;

    Dialog createDialog;
    // TODO: Possible to convert to Password Field
    TextField confirmPassword;
    TextField password;
    TextField accountName;
    Notification errorNotification;

    public AccountCreationBuilder(AccountCreationService accountCreationService, UserService userService) {
        this.accountCreationService = accountCreationService;
        this.userService = userService;
        buildErrorNotification();
    }

    // TODO: Maybe put this in a utility method to be reused throughout application? Static.
    private void buildErrorNotification() {
        errorNotification = new Notification("", 3000, Notification.Position.TOP_CENTER);        errorNotification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        errorNotification.addThemeVariants(NotificationVariant.LUMO_ERROR);
    }

    public Dialog createAccountCreateDialog() {
        // Instantiate Dialog Box
        createDialog = new Dialog();
        createDialog.setHeaderTitle("Create Account");

        // Initialize FormLayout with correct properties
        FormLayout formLayout = new FormLayout();
        formLayout.add(
                createAccountName(),
                createPassword(),
                createConfirmPassword()
        );

        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1), new FormLayout.ResponsiveStep("600px", 2));

        // Initialize VerticalLayout with correct properties
        VerticalLayout dialogLayout = new VerticalLayout(formLayout);
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "24rem").set("max-width", "100%");

        // Add VerticalLayout to dialog
        createDialog.add(dialogLayout);

        // Add save and cancel buttons to dialog footer
        Button create = createAccountButton();
        Button cancel = cancelAccountButton();
        createDialog.getFooter().add(cancel);
        createDialog.getFooter().add(create);

        // Return Dialog
        return createDialog;
    }

    private TextField createConfirmPassword() {
        confirmPassword = new TextField("Confirm Password");
        confirmPassword.setRequired(true);
        confirmPassword.setMinLength(8);
        confirmPassword.setMaxLength(25);
        confirmPassword.setErrorMessage("Password must be between 8 and 25 characters!");
        confirmPassword.setHelperText("e.g: ThisIsMyPassword (P.S. Don't use this)!");
        return confirmPassword;
    }

    private TextField createPassword() {
        password = new TextField("Password");
        password.setRequired(true);
        password.setMinLength(8);
        password.setMaxLength(25);
        password.setErrorMessage("Password must be between 8 and 25 characters!");
        password.setHelperText("e.g: ThisIsMyPassword (P.S. Don't use this)!");
        return password;
    }

    private TextField createAccountName() {
        accountName = new TextField("Account Name");
        accountName.setRequired(true);
        accountName.setMinLength(1);
        accountName.setMaxLength(25);
        accountName.setErrorMessage("Password must be between 1 and 25 characters!");
        accountName.setHelperText("e.g: Vettel");
        return accountName;
    }

    private Button createAccountButton() {
        Button createAccountButton = new Button("Create Account");
        createAccountButton.addClickListener(event -> {

            // Check textfields for null and password mismatching
            if(checkIfAccountFieldsAreNull() || checkPasswordsMismatch()) {
                return;
            }

            // Check if user exists
            if (userService.userExists(accountName.getValue())) {
                errorNotification.setText("Username already in use!");
                errorNotification.open();
                return;
            }

            accountCreationService.createUser(accountName.getValue(), password.getValue());
            Notification.show("Created Account!");
            createDialog.close();
        });

        return createAccountButton;
    }

    private Button cancelAccountButton() {
        Button cancelAccountButton = new Button("Cancel");
        cancelAccountButton.addClickListener(event -> {

            Notification.show("Cancelled Creating Account..");
            createDialog.close();
        });

        return cancelAccountButton;
    }

    private boolean checkIfAccountFieldsAreNull() {

        if (accountName.getValue().isEmpty() || accountName.isInvalid()) {
            errorNotification.setText("Account Name is an invalid value!!");
            errorNotification.open();
            return true;
        }
        if (password.getValue().isEmpty() || password.isInvalid()) {
            errorNotification.setText("Password is an invalid value!!");
            errorNotification.open();
            return true;
        }
        if (confirmPassword.getValue().isEmpty() || confirmPassword.isInvalid()) {
            errorNotification.setText("Confirm Password is an invalid value!!");
            errorNotification.open();
            return true;
        }

        return false;
    }

    private boolean checkPasswordsMismatch() {
        if (!password.getValue().equals(confirmPassword.getValue())) {
            errorNotification.setText("Passwords do not match!");
            errorNotification.open();
            return true;
        }
        return false;
    }

}
