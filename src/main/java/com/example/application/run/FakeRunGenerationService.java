package com.example.application.run;

import com.example.application.account.AppUser;
import com.vaadin.flow.component.notification.Notification;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

public class FakeRunGenerationService {
    public static Run generateFakeRun(AppUser loggedInAppUser) {
        Random random = new Random();

        String car = "Burple Dragster";
        String driver = "Max";
        String trackSelection = "Texas Motorplex";
        String lane = random.nextBoolean() ? "Left" : "Right";

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        // These BigDecimals are random within "realistic" ranges (Microsoft Copilot Code)
        BigDecimal dial = BigDecimal.valueOf(4.44 + (random.nextDouble() * (4.57 - 4.44)))
                .setScale(4, RoundingMode.HALF_UP);
        BigDecimal reaction = BigDecimal.valueOf(random.nextDouble() * 0.30)
                .setScale(4, RoundingMode.HALF_UP);
        BigDecimal sixtyFoot = BigDecimal.valueOf(0.995 + (random.nextDouble() * (0.999 - 0.995)))
                .setScale(4, RoundingMode.HALF_UP);
        BigDecimal halfTrack = BigDecimal.valueOf(2.870 + (random.nextDouble() * (2.874 - 2.870)))
                .setScale(4, RoundingMode.HALF_UP);
        BigDecimal fullTrack = dial.add(BigDecimal.valueOf((random.nextDouble() * 0.016) * (random.nextBoolean() ? 1 : -1))
                .setScale(4, RoundingMode.HALF_UP));
        BigDecimal speed = BigDecimal.valueOf(153 + random.nextDouble(3) * (2.874 - 2.870))
                .setScale(2, RoundingMode.HALF_UP);

        Run createdRun = new Run(
                loggedInAppUser,
                date,
                time,
                car,
                driver,
                trackSelection,
                lane,
                dial,
                reaction,
                sixtyFoot,
                halfTrack,
                fullTrack,
                speed
        );

        System.out.println("Created Fake Run " + createdRun.getId() + " with Dial " + createdRun.getDial());
        return createdRun;
    }
}

