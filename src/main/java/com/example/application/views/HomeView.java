package com.example.application.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.annotation.UIScope;

@Route("")
@AnonymousAllowed
@UIScope
@PageTitle("Package θ - Bracket Racing Tracker")
public class HomeView extends VerticalLayout {

    private final String FEATURES_ID = "features";
    private final String USE_CASES_ID = "use-cases";
    private final String HOW_IT_WORKS_ID = "how-it-works";

    public HomeView() {
        setSpacing(false);
        setPadding(false);
        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("overflow", "auto");
        getStyle().set("width", "100%");
        getStyle().set("max-width", "100%");

        // Create the top banner and navigation
        createTopBanner();

        // Create hero section
        createHeroSection();

        // Create features section
        createFeaturesSection();

        // Create use cases section
        createUseCasesSection();

        // Create how it works section
        createHowItWorksSection();

        // Create footer
        createFooter();
    }

    private void createTopBanner() {
        // Create the main horizontal layout for the banner
        HorizontalLayout bannerHL = new HorizontalLayout();
        bannerHL.setWidthFull();
        bannerHL.setPadding(true);
        bannerHL.setSpacing(true);
        bannerHL.getStyle().set("background-color", "var(--lumo-base-color)");
        bannerHL.getStyle().set("border-bottom", "1px solid var(--lumo-contrast-10pct)");

        // Create left side content container
        HorizontalLayout leftContent = new HorizontalLayout();
        leftContent.setPadding(false);
        leftContent.setSpacing(true);

        // Create logo and text components
//        Icon logo = VaadinIcon.BULLSEYE.create();
//        logo.setColor("var(--lumo-primary-color)");
//        logo.setSize("24px");
        H1 text = new H1("Package θ");
        text.getStyle().set("font-size", "1.5em");
        text.getStyle().set("margin", "0");

        // Add text to the left content
        leftContent.add(text);

        // Create navigation buttons
        HorizontalLayout navButtons = new HorizontalLayout();
        navButtons.setSpacing(true);

        // Create navigation text buttons
        Button featuresButton = new Button("Features");
        featuresButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        featuresButton.addClickListener(event -> scrollToSection(FEATURES_ID));

        Button useCasesButton = new Button("Use Cases");
        useCasesButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        useCasesButton.addClickListener(event -> scrollToSection(USE_CASES_ID));

        Button howItWorksButton = new Button("How It Works");
        howItWorksButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        howItWorksButton.addClickListener(event -> scrollToSection(HOW_IT_WORKS_ID));

        navButtons.add(featuresButton, useCasesButton, howItWorksButton);

        // Create the login button
        Button loginButton = new Button("Try Package θ");
        loginButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        loginButton.addClickListener(event -> {
            UI.getCurrent().navigate("/login");
        });

        // Configure the banner layout with proper alignment
        bannerHL.add(leftContent, navButtons, loginButton);

        // Set left padding for left content
        leftContent.getStyle().set("padding-left", "16px");

        // Set right padding for login button
        loginButton.getStyle().set("margin-right", "16px");

        // Configure the alignment and expansion
        bannerHL.setJustifyContentMode(JustifyContentMode.CENTER);
        bannerHL.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        bannerHL.getStyle().set("flex-wrap", "wrap");

        // Ensure nav buttons are in the middle and responsive
        navButtons.getStyle().set("flex-grow", "1");
        navButtons.setJustifyContentMode(JustifyContentMode.CENTER);

        // Add the banner to the main layout
        add(bannerHL);
    }

    private void scrollToSection(String id) {
        UI.getCurrent().getPage().executeJs("document.getElementById('" + id + "').scrollIntoView({behavior: 'smooth'})");
    }

    private void createHeroSection() {
        VerticalLayout heroSection = new VerticalLayout();
        heroSection.setWidthFull();
        //heroSection.setHeightFull();
        heroSection.setPadding(true);
        heroSection.setSpacing(true);
        heroSection.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        heroSection.getStyle().set("background-image", "linear-gradient(var(--lumo-primary-color-10pct), var(--lumo-primary-color-50pct))");
        heroSection.getStyle().set("text-align", "center");
        heroSection.getStyle().set("padding-top", "4em");
        heroSection.getStyle().set("padding-bottom", "4em");

        H2 heroTitle = new H2("Track Your Bracket Racing Performance Like Never Before");
        heroTitle.getStyle().set("font-size", "2.5em");
        heroTitle.getStyle().set("margin-bottom", "0.5em");

        Paragraph heroSubtitle = new Paragraph("Package θ helps racers analyze their runs, improve consistency, and win more races");
        heroSubtitle.getStyle().set("font-size", "1.2em");
        heroSubtitle.getStyle().set("margin-bottom", "2em");

        Button getStartedButton = new Button("Get Started", VaadinIcon.ARROW_RIGHT.create());
        getStartedButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_LARGE);
        getStartedButton.addClickListener(event -> UI.getCurrent().navigate("/login"));

        Div heroImageContainer = new Div();
        heroImageContainer.setWidth("80%");
        heroImageContainer.setMaxWidth("800px");
        heroImageContainer.getStyle().set("margin-top", "3em");
        heroImageContainer.getStyle().set("background-color", "var(--lumo-base-color)");
        heroImageContainer.getStyle().set("border-radius", "8px");
        heroImageContainer.getStyle().set("box-shadow", "0 4px 20px rgba(0,0,0,0.1)");
        heroImageContainer.getStyle().set("height", "350px");
        heroImageContainer.getStyle().set("display", "flex");
        heroImageContainer.getStyle().set("align-items", "center");
        heroImageContainer.getStyle().set("justify-content", "center");

        Span placeholderText = new Span("App Screenshot Placeholder");
        placeholderText.getStyle().set("color", "var(--lumo-tertiary-text-color)");
        placeholderText.getStyle().set("font-style", "italic");

        Image image = new Image("images/americandragster.jpeg", "American Dragster");
        image.getStyle().set("object-fit", "cover");
        image.getStyle().set("border-radius", "8px");
        image.setWidth("100%");
        image.setHeight("100%");

        heroImageContainer.add(image);

        heroSection.add(heroTitle, heroSubtitle, getStartedButton, heroImageContainer);
        add(heroSection);
    }

    private void createFeaturesSection() {
        // Create the features section with an id for navigation
        Div anchorDiv = new Div();
        anchorDiv.setId(FEATURES_ID);
        anchorDiv.getStyle().set("padding-top", "80px");
        //anchorDiv.getStyle().set("margin-top", "-80px");

        VerticalLayout featuresSection = new VerticalLayout();
        featuresSection.setWidthFull();
        featuresSection.setPadding(true);
        featuresSection.setSpacing(true);
        featuresSection.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        featuresSection.getStyle().set("text-align", "center");
        featuresSection.getStyle().set("background-color", "var(--lumo-contrast-5pct)");
        featuresSection.getStyle().set("padding-top", "2em");
        featuresSection.getStyle().set("padding-bottom", "4em");

        H2 sectionTitle = new H2("Features");
        sectionTitle.getStyle().set("font-size", "2em");
        sectionTitle.getStyle().set("margin-bottom", "1.5em");

        // Features grid layout
        HorizontalLayout featuresGrid = new HorizontalLayout();
        featuresGrid.setWidthFull();
        featuresGrid.setJustifyContentMode(JustifyContentMode.CENTER);
        featuresGrid.setDefaultVerticalComponentAlignment(Alignment.START);
        featuresGrid.getStyle().set("flex-wrap", "wrap");
        featuresGrid.getStyle().set("gap", "30px");

        // Add feature cards
        featuresGrid.add(
                createFeatureCard(VaadinIcon.TIMER, "Run Analysis",
                        "Track and analyze your reaction times, 60-foot times, and overall performance."),
                createFeatureCard(VaadinIcon.CHART, "Performance Trends",
                        "Visualize your performance over time and identify areas for improvement."),
                createFeatureCard(VaadinIcon.CLOUD, "Cloud Sync",
                        "Access your race data from anywhere, on any device.")
        );

        featuresSection.add(sectionTitle, featuresGrid);
        anchorDiv.add(featuresSection);
        add(anchorDiv);
    }

    private VerticalLayout createFeatureCard(VaadinIcon icon, String title, String description) {
        VerticalLayout card = new VerticalLayout();
        card.setWidth("300px");
        card.setHeight("250px");
        card.setPadding(true);
        card.setSpacing(true);
        card.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        card.getStyle().set("background-color", "var(--lumo-base-color)");
        card.getStyle().set("border-radius", "8px");
        card.getStyle().set("box-shadow", "0 2px 10px rgba(0,0,0,0.05)");
        card.getStyle().set("text-align", "center");
        card.getStyle().set("transition", "transform 0.3s");
        card.getStyle().set("cursor", "pointer");

        // Add hover effect
        card.getElement().addEventListener("mouseover", event ->
                        card.getStyle().set("transform", "translateY(-5px)"));
        card.getElement().addEventListener("mouseout", event ->
                        card.getStyle().set("transform", "translateY(5px)"));
        Icon featureIcon = icon.create();
        featureIcon.setColor("var(--lumo-primary-color)");
        featureIcon.setSize("40px");

        H3 featureTitle = new H3(title);
        featureTitle.getStyle().set("margin-top", "0.5em");
        featureTitle.getStyle().set("margin-bottom", "0.5em");

        Paragraph featureDescription = new Paragraph(description);
        featureDescription.getStyle().set("color", "var(--lumo-secondary-text-color)");

        card.add(featureIcon, featureTitle, featureDescription);
        return card;
    }

    private void createUseCasesSection() {
        // Create the use cases section with an id for navigation
        Div anchorDiv = new Div();
        anchorDiv.setId(USE_CASES_ID);
        anchorDiv.getStyle().set("padding-top", "80px");
        //anchorDiv.getStyle().set("margin-top", "-80px");

        VerticalLayout useCasesSection = new VerticalLayout();
        useCasesSection.setWidthFull();
        useCasesSection.setPadding(true);
        useCasesSection.setSpacing(true);
        useCasesSection.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        useCasesSection.getStyle().set("background-color", "var(--lumo-contrast-5pct)");
        useCasesSection.getStyle().set("padding-top", "2em");
        useCasesSection.getStyle().set("padding-bottom", "4em");

        H2 sectionTitle = new H2("Use Cases");
        sectionTitle.getStyle().set("font-size", "2em");
        sectionTitle.getStyle().set("margin-bottom", "1.5em");
        sectionTitle.getStyle().set("text-align", "center");

        // Create use case items
        VerticalLayout useCasesContainer = new VerticalLayout();
        useCasesContainer.setMaxWidth("900px");
        useCasesContainer.setPadding(false);
        useCasesContainer.setSpacing(true);

        useCasesContainer.add(
                createUseCase("Weekend Racer",
                        "Track your weekend race performance and see improvement over time.",
                        true,
                        "omp logo.png"),
                createUseCase("Professional Bracket Racer",
                        "Analyze detailed metrics to gain a competitive edge in high-stakes races.",
                        false,
                        "sparco black.jpg"),
                createUseCase("Race Team Manager",
                        "Monitor the performance of your entire racing team in one place.",
                        true,
                        "vp racing2.jpg")
        );

        useCasesSection.add(sectionTitle, useCasesContainer);
        anchorDiv.add(useCasesSection);
        add(anchorDiv);
    }

    private HorizontalLayout createUseCase(String title, String description, boolean imageOnRight, String imageName) {
        HorizontalLayout useCaseItem = new HorizontalLayout();
        useCaseItem.setWidthFull();
        useCaseItem.setSpacing(true);
        useCaseItem.setPadding(true);
        useCaseItem.getStyle().set("background-color", "var(--lumo-base-color)");
        useCaseItem.getStyle().set("border-radius", "8px");
        useCaseItem.getStyle().set("margin-bottom", "20px");
        useCaseItem.getStyle().set("box-shadow", "0 2px 10px rgba(0,0,0,0.05)");

        // Create image placeholder
        Div imagePlaceholder = new Div();
        imagePlaceholder.setWidth("200px");
        imagePlaceholder.setHeight("150px");
        imagePlaceholder.getStyle().set("background-color", "var(--lumo-contrast-10pct)");
        imagePlaceholder.getStyle().set("border-radius", "8px");
        imagePlaceholder.getStyle().set("display", "flex");
        imagePlaceholder.getStyle().set("align-items", "center");
        imagePlaceholder.getStyle().set("justify-content", "center");
        if (imageName != null) { // if image was specified
            Image image = new Image("images/" + imageName, imageName);
            image.getStyle().set("object-fit", "cover");
            image.getStyle().set("border-radius", "8px");
            image.setWidth("100%");
            image.setHeight("auto");
            imagePlaceholder.getStyle().set("background-color", "transparent");

            imagePlaceholder.add(image);
        } else {
            Span placeholderText = new Span("Image");
            placeholderText.getStyle().set("color", "var(--lumo-tertiary-text-color)");
            placeholderText.getStyle().set("font-style", "italic");
            imagePlaceholder.add(placeholderText);
        }

        // Create text content
        VerticalLayout textContent = new VerticalLayout();
        textContent.setPadding(false);
        textContent.setSpacing(false);
        textContent.setDefaultHorizontalComponentAlignment(Alignment.START);

        H3 useCaseTitle = new H3(title);
        useCaseTitle.getStyle().set("margin-top", "0");
        useCaseTitle.getStyle().set("margin-bottom", "0.5em");

        Paragraph useCaseDescription = new Paragraph(description);
        useCaseDescription.getStyle().set("color", "var(--lumo-secondary-text-color)");
        useCaseDescription.getStyle().set("margin", "0");

        textContent.add(useCaseTitle, useCaseDescription);
        textContent.setFlexGrow(1, textContent);

        // Add components in the correct order based on imageOnRight flag
        if (imageOnRight) {
            useCaseItem.add(textContent, imagePlaceholder);
        } else {
            useCaseItem.add(imagePlaceholder, textContent);
        }

        return useCaseItem;
    }

    private void createHowItWorksSection() {
        // Create the how it works section with an id for navigation
        Div anchorDiv = new Div();
        anchorDiv.setId(HOW_IT_WORKS_ID);
        anchorDiv.getStyle().set("padding-top", "80px");
        //anchorDiv.getStyle().set("margin-top", "-80px");

        VerticalLayout howItWorksSection = new VerticalLayout();
        howItWorksSection.setWidthFull();
        howItWorksSection.setPadding(true);
        howItWorksSection.setSpacing(true);
        howItWorksSection.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        howItWorksSection.getStyle().set("background-color", "var(--lumo-contrast-5pct)");
        howItWorksSection.getStyle().set("padding-top", "2em");
        howItWorksSection.getStyle().set("padding-bottom", "4em");

        H2 sectionTitle = new H2("How It Works");
        sectionTitle.getStyle().set("font-size", "2em");
        sectionTitle.getStyle().set("margin-bottom", "1.5em");
        sectionTitle.getStyle().set("text-align", "center");

        // Steps container
        VerticalLayout stepsContainer = new VerticalLayout();
        stepsContainer.setMaxWidth("800px");
        stepsContainer.setPadding(false);
        stepsContainer.setSpacing(true);

        // Add steps
        stepsContainer.add(
                createStep(1, "Sign Up", "Create an account to get started with Package θ."),
                createStep(2, "Enter Your Race Data", "Record your reaction times, 60-foot times, and other metrics after each run."),
                createStep(3, "Analyze Performance", "View detailed analytics to understand your strengths and areas for improvement."),
                createStep(4, "Win More Races", "Apply insights from your data to improve your racing strategy.")
        );

        howItWorksSection.add(sectionTitle, stepsContainer);
        anchorDiv.add(howItWorksSection);
        add(anchorDiv);
    }

    private HorizontalLayout createStep(int stepNumber, String title, String description) {
        HorizontalLayout step = new HorizontalLayout();
        step.setWidthFull();
        step.setSpacing(true);
        step.setPadding(true);
        step.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        step.getStyle().set("background-color", "var(--lumo-base-color)");
        step.getStyle().set("border-radius", "8px");
        step.getStyle().set("margin-bottom", "15px");
        step.getStyle().set("box-shadow", "0 2px 5px rgba(0,0,0,0.05)");

        // Step number circle
        Div numberCircle = new Div();
        numberCircle.setText(String.valueOf(stepNumber));
        numberCircle.getStyle().set("background-color", "var(--lumo-primary-color)");
        numberCircle.getStyle().set("color", "var(--lumo-primary-contrast-color)");
        numberCircle.getStyle().set("border-radius", "50%");
        numberCircle.getStyle().set("width", "40px");
        numberCircle.getStyle().set("height", "40px");
        numberCircle.getStyle().set("display", "flex");
        numberCircle.getStyle().set("align-items", "center");
        numberCircle.getStyle().set("justify-content", "center");
        numberCircle.getStyle().set("font-weight", "bold");
        numberCircle.getStyle().set("flex-shrink", "0");

        // Step content
        VerticalLayout stepContent = new VerticalLayout();
        stepContent.setPadding(false);
        stepContent.setSpacing(false);

        H3 stepTitle = new H3(title);
        stepTitle.getStyle().set("margin-top", "0");
        stepTitle.getStyle().set("margin-bottom", "0.25em");

        Paragraph stepDescription = new Paragraph(description);
        stepDescription.getStyle().set("color", "var(--lumo-secondary-text-color)");
        stepDescription.getStyle().set("margin", "0");

        stepContent.add(stepTitle, stepDescription);

        step.add(numberCircle, stepContent);
        return step;
    }

    private void createFooter() {
        HorizontalLayout footer = new HorizontalLayout();
        footer.setWidthFull();
        footer.setPadding(true);
        footer.setSpacing(true);
        footer.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        footer.getStyle().set("background-color", "var(--lumo-contrast-10pct)");
        footer.getStyle().set("padding-top", "2em");
        footer.getStyle().set("padding-bottom", "2em");
        footer.getStyle().set("margin-top", "auto");

        // Copyright text
        Span copyright = new Span("© " + java.time.Year.now().getValue() + " Package θ. All rights reserved.");
        copyright.getStyle().set("color", "var(--lumo-secondary-text-color)");

        // Footer links
        HorizontalLayout links = new HorizontalLayout();
        links.setSpacing(true);

        Anchor termsLink = new Anchor("#", "Terms of Service");
        termsLink.getStyle().set("color", "var(--lumo-primary-color)");
        termsLink.getStyle().set("text-decoration", "none");

        Anchor privacyLink = new Anchor("#", "Privacy Policy");
        privacyLink.getStyle().set("color", "var(--lumo-primary-color)");
        privacyLink.getStyle().set("text-decoration", "none");

        Anchor contactLink = new Anchor("#", "Contact Us");
        contactLink.getStyle().set("color", "var(--lumo-primary-color)");
        contactLink.getStyle().set("text-decoration", "none");

        links.add(termsLink, privacyLink, contactLink);

        footer.add(copyright);
        footer.add(links);
        footer.setJustifyContentMode(JustifyContentMode.BETWEEN);

        add(footer);
    }
}