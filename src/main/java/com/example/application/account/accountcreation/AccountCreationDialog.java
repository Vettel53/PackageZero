package com.example.application.account.accountcreation;

import com.example.application.account.accountcreation.builder.AccountCreationBuilder;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class AccountCreationDialog {

    private final AccountCreationBuilder accountCreationBuilder;

    public AccountCreationDialog(AccountCreationBuilder accountCreationBuilder) {
        this.accountCreationBuilder = accountCreationBuilder;
    }

    public void showAccountCreateDialog() {
        // Instantiate dialog box
        Dialog createDialog = accountCreationBuilder.createAccountCreateDialog();

        createDialog.open();
    }


}
