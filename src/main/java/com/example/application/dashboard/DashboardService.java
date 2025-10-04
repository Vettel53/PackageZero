package com.example.application.dashboard;

import com.example.application.account.UserService;
import com.example.application.account.AppUser;
import com.example.application.run.RunService;
import com.example.application.run.Run;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@VaadinSessionScope
public class DashboardService {

    // Dependency Injection (constructor)
    private final UserService userService;
    private final RunService runService;

    // Setter injection to access a method in dashboardView
    private DashboardView dashboardView;

    public DashboardService(UserService userService, RunService runService) {
        this.userService = userService;
        this.runService = runService;
    }

    public AppUser getAppUserByUsername() {
        String username = userService.getUsername();
        System.out.println("The Logged in User has this username: " + username);

        return userService.getCurrentAppUser(username);
    }

    public List<Run> getAllRunsFromUser(AppUser loggedInAppUser) {
        return runService.getUserRuns(loggedInAppUser);
    }

    public void setDashboardView(DashboardView dashboardView) {
        this.dashboardView = dashboardView;
    }

    // TODO: DOC THESE METHODS HEAVY
    public void callUpdateGridAfterAdd(Run createdRun){
        dashboardView.updateGridAfterAdd(createdRun);
    }

    public void callRefreshGrid() {
        dashboardView.refreshGridData();
    }

    public void callUpdateGridAfterDelete(Run runToDelete) {
        dashboardView.updateGridAfterDelete(runToDelete);
    }

    public BigDecimal truncateToValidDecimal(BigDecimal decimalToTruncate) {
        return decimalToTruncate.setScale(3, RoundingMode.DOWN);
    }

}
