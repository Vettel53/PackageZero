package com.example.application.account;

import com.example.application.account.accountcreation.AccountCreationDialog;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.annotation.UIScope;

@Route("login")
@AnonymousAllowed
@UIScope
public class LoginView extends VerticalLayout {

    private final AccountCreationDialog accountCreationDialog;

    public LoginView(AccountCreationDialog accountCreationDialog) {
        this.accountCreationDialog = accountCreationDialog;

        // Set view properties
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        // Create login form
        LoginForm login = new LoginForm();
        login.setAction("login");
        //login.addLoginListener(l -> UI.getCurrent().navigate("/dashboard"));

        // Create Account Button with listener
        Button createAccount = new Button("Create Account");
        createAccount.addClickListener(event -> {
            accountCreationDialog.showAccountCreateDialog();
        });

        // Add to vertical layout
        add(new H1("Bracket Racing App"), login, createAccount);

    }
}
