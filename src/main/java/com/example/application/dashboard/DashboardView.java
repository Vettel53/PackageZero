package com.example.application.dashboard;

import com.example.application.account.AppUser;
import com.example.application.run.Run;
import com.example.application.views.MainView;
import com.example.application.dashboard.builder.DashboardBuilder;
import com.example.application.dashboard.components.RunsGrid;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import jakarta.annotation.security.PermitAll;

import java.util.List;


@Route(value = "dashboard", layout = MainView.class)
@PermitAll
@UIScope
public class DashboardView extends VerticalLayout {

    private Grid<Run> grid = new Grid<>(Run.class);
    private final DashboardService dashboardService;
    ListDataProvider<Run> dataProvider;

    private AppUser loggedInAppUser;
    private final DashboardBuilder dashboardBuilder;
    private final RunsGrid runsGrid;

    public DashboardView(DashboardService dashboardService, DashboardBuilder dashboardBuilder, RunsGrid runsGrid) {
        this.dashboardBuilder = dashboardBuilder;
        this.dashboardService = dashboardService;
        this.runsGrid = runsGrid;

        // Setter injection for service to access this classes methods
        dashboardService.setDashboardView(this);

        loadUI();
    }

    private void loadUI() {
        // Get data for grid population
        dataProvider = getDataProvider();
        grid = runsGrid.getGrid(grid, dataProvider);

        add(dashboardBuilder.buildMainHorizontalLayout(loggedInAppUser));
        add(grid);
    }

    // Might put this in another class like service
    private ListDataProvider<Run> getDataProvider() {
        // TODO: Some form of error efficent handling (Make experience smooth for user)
        loggedInAppUser = dashboardService.getAppUserByUsername();

        // Get all runs for the logged-in user
        List<Run> appUserRuns = dashboardService.getAllRunsFromUser(loggedInAppUser);
        dataProvider = new ListDataProvider<>(appUserRuns);
        return dataProvider;
    }

    // TODO: Understand @UIScope @VaadinSessionScope annotations etc
    public void updateGridAfterAdd(Run createdRun) {
        this.getUI().get().access(() -> {
            dataProvider.getItems().add(createdRun);
            dataProvider.refreshAll();
        });
    }

    public void refreshGridData() {
        this.getUI().get().access(() -> {
            dataProvider.refreshAll();
            grid.recalculateColumnWidths();
        });
    }

    public void updateGridAfterDelete(Run runToDelete) {
        this.getUI().get().access(() -> {
            dataProvider.getItems().remove(runToDelete);
            dataProvider.refreshAll();
        });
    }
}