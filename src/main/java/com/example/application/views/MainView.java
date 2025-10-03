package com.example.application.views;

import com.example.application.security.SecurityService;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.ChartOptions;
import com.vaadin.flow.component.charts.themes.LumoDarkTheme;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;

import jakarta.annotation.security.PermitAll;


//@Route("welcome")
@PermitAll
@UIScope
public class MainView extends AppLayout {

    private final SecurityService securityService;

    public MainView(SecurityService securityService) {
        this.securityService = securityService;
        // Set chart themes globally to dark mode
        ChartOptions.get().setTheme(new LumoDarkTheme());

        // Maybe rewrite this to make it cleaner
        DrawerToggle toggle = new DrawerToggle();
        H1 title = new H1("Package θ");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        SideNav nav = getSideNav();

        Scroller scroller = new Scroller(nav);
        scroller.setClassName(LumoUtility.Padding.SMALL);

        Button logoutButton = new Button("Logout", click -> securityService.logout());
        logoutButton.getStyle().setMarginBottom("15px");

        addToDrawer(scroller, logoutButton);
        addToNavbar(toggle, title);

        setContent(new H1("Bracket Racing App WIP"));
    }

    private SideNav getSideNav() {
        SideNav sideNav = new SideNav();
        sideNav.addItem(
//                new SideNavItem("Home", "/home",
//                        VaadinIcon.HOME.create()),
                new SideNavItem("Dashboard", "/dashboard",
                        VaadinIcon.DASHBOARD.create()),
                new SideNavItem("Vehicles", "/vehicles",
                        VaadinIcon.CAR.create()),
                new SideNavItem("Statistics", "/statistics",
                        VaadinIcon.CHART_TIMELINE.create()),
                new SideNavItem("nil", "/documents",
                        VaadinIcon.RECORDS.create()),
                new SideNavItem("nil", "/tasks", VaadinIcon.LIST.create()),
                new SideNavItem("nil", "/analytics",
                        VaadinIcon.CHART.create())
        );
        return sideNav;
    }

}

