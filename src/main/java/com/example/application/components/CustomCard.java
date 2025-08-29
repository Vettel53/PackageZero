package com.example.application.components;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;

public class CustomCard {

    public Div createCustomCard(String titleText, String subtitleText, String badgeText, String descriptionText, String width) {
        Div card = new Div();
        card.getStyle().set("border", "1px solid var(--lumo-contrast-10pct)");
        card.getStyle().set("border-radius", "5px");
        card.getStyle().set("padding", "10px");
        card.getStyle().set("width", width);
        card.getStyle().set("background-color", "var(--lumo-base-color)");

        Div title = new Div();
        title.setText(titleText);
        title.getStyle().set("font-size", "1.2em");
        title.getStyle().set("font-weight", "bold");
        card.add(title);

        Div subtitle = new Div();
        subtitle.setText(subtitleText);
        subtitle.getStyle().set("font-size", "0.9em");
        subtitle.getStyle().set("color", "#777");
        card.add(subtitle);

        Span badge = new Span(badgeText);
        badge.getElement().getThemeList().add("badge constrast");
        //badge.getStyle().set("background-color", "lightgreen");
        //badge.getStyle().set("color", "black");
        //badge.getStyle().set("padding", "2px 5px");
        badge.getStyle().set("border-radius", "3px");
        card.add(badge);

        Div description = new Div();
        description.setText(descriptionText);
        card.add(description);

        return card;
    }

}
