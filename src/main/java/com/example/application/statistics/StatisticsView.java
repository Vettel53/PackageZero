package com.example.application.statistics;

import java.math.BigDecimal;

import com.example.application.components.CustomCard;
import com.example.application.views.MainView;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import jakarta.annotation.security.PermitAll;

@Route(value = "statistics", layout = MainView.class)
@PermitAll
@UIScope
public class StatisticsView extends HorizontalLayout {

    private final StatisticsService statisticsService;
    
    public StatisticsView(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
        //add(createBreakOutChart());
        //add(createReactionTimeChart());
        setWrap(true);
        setMargin(true);
        setSpacing(true);
        add(createReactionTimeCard());
        add(createBreakOutCard());
        add(createOverPercentageCard());
    }

    private Div createReactionTimeCard() {
        String titleText = "Reaction Time";
        String subtitleText = "Average Reaction Time Of All Runs";
        String badgeText = statisticsService.getReactionAverage() == null ? "N/A" : statisticsService.getReactionAverage() + " sec";
        String descriptionText = "This card displays the average reaction time based on the recorded runs.";
        String width = "100%";

        CustomCard customCard = new CustomCard();
        return customCard.createCustomCard(titleText, subtitleText, badgeText, descriptionText, width);
    }

    private Div createBreakOutCard() {
        String titleText = "Break-Out Percentage";
        String subtitleText = "Percentage of Break-Outs in Runs";
        String badgeText = statisticsService.getBreakoutPercentage() == null ? "N/A" : statisticsService.getBreakoutPercentage() + "%";
        String descriptionText = "This card displays the percentage of runs that resulted in a break-out.";
        String width = "100%";

        CustomCard customCard = new CustomCard();
        return customCard.createCustomCard(titleText, subtitleText, badgeText, descriptionText, width);
    }

    private Div createOverPercentageCard() {
        String titleText = "Over Percentage";
        String subtitleText = "Percentage of Runs Over Dial-In";
        String badgeText = statisticsService.getOverPercentage() == null ? "N/A" : statisticsService.getOverPercentage() + "%";
        String descriptionText = "This card displays the percentage of runs that went over the dial-in";
        String width = "100%";

        CustomCard customCard = new CustomCard();
        return customCard.createCustomCard(titleText, subtitleText, badgeText, descriptionText, width);
    }

    // private Div createStatsLayout() {
    //     Div statsLayout = new Div();
    //     statsLayout.setWidth("100%");
    //     statsLayout.setHeight("100%");
    //     statsLayout.setText("test");
    //     statsLayout.getStyle().set("border", "1px solid #FFFFFF");
    //     statsLayout.getStyle().set("border-radius", "10px");

    //     return statsLayout;
    // }
}
