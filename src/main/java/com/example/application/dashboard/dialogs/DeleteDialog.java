package com.example.application.dashboard.dialogs;

import com.example.application.dashboard.DashboardService;
import com.example.application.run.Run;
import com.example.application.run.RunService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class DeleteDialog {

    private final DashboardService dashboardService;
    private final RunService runService;

    public DeleteDialog(DashboardService dashboardService, RunService runService) {
        this.dashboardService = dashboardService;
        this.runService = runService;
    }

    public void loadConfirmDeleteDialog(Run runToDelete) {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Are you sure you want to delete this run?");

        VerticalLayout dialogLayout = createDeleteRunDialogLayout();
        dialog.add(dialogLayout);

        Button yesButton = new Button("Delete Run");
        Button noButton = new Button("Cancel");
        dialog.getFooter().add(noButton);
        dialog.getFooter().add(yesButton);

        dialog.open();

        yesButton.addClickListener(event -> {
            runService.deleteRun(runToDelete);

            // Refresh the grid/data-provider to reflect the changes
            dashboardService.callUpdateGridAfterDelete(runToDelete);

            dialog.close();
            Notification.show("Successfully deleted run!", 3000, Notification.Position.TOP_CENTER);
        });

        noButton.addClickListener(event -> {
            Notification.show("Cancelled deleting run...", 3000, Notification.Position.TOP_CENTER);
            dialog.close();
        });
    }

    private VerticalLayout createDeleteRunDialogLayout() {
        VerticalLayout dialogLayout = new VerticalLayout();
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "24rem").set("max-width", "100%");

        return dialogLayout;
    }

}
