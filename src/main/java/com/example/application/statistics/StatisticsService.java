package com.example.application.statistics;

import com.example.application.run.RunRepo;
import com.example.application.account.AppUser;
import com.example.application.account.UserRepo;
import com.example.application.run.Run;
import com.example.application.security.SecurityService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class StatisticsService {

    private final UserRepo userRepo;
    private final RunRepo runRepo;
    private final SecurityService securityService;

    public StatisticsService(UserRepo userRepo, RunRepo runRepo, SecurityService securityService) {
        this.userRepo = userRepo;
        this.runRepo = runRepo;
        this.securityService = securityService;
    }

    public BigDecimal getBreakoutPercentage() {
        AppUser appUser = getCurrentUser();
        if (appUser == null) {
            return null;
        }

        int breakoutCounter = 0;
        int totalRuns = 0;

        for (Run run : runRepo.findByAppUser(appUser)) {
            BigDecimal dial = run.getDial();
            BigDecimal fullTrack = run.getFullTrack();

            if (dial.compareTo(fullTrack) > 0 ) {
                breakoutCounter++;
            }
            totalRuns++;
        }

        if (totalRuns == 0) {
            return null;
        }

        double breakOutPercentage = ((double) breakoutCounter / totalRuns) * 100;
        System.out.println("Breakout Counter: " + breakoutCounter);
        System.out.println("Total Runs: " + totalRuns);
        System.out.println("Breakout Percentage: " + breakOutPercentage);
        return new BigDecimal(breakOutPercentage).setScale(2, RoundingMode.HALF_UP);
    }

    private AppUser getCurrentUser() {
        UserDetails loadUserDetails = securityService.getAuthenticatedUser();
        if (loadUserDetails == null) {
            return null;
        }
        String username = loadUserDetails.getUsername();

        return userRepo.findByUsername(username);
    }

    public BigDecimal getOverPercentage() {
        AppUser appUser = getCurrentUser();
        if (appUser == null) {
            return null;
        }

        int overCounter = 0;
        int totalRuns = 0;

        for (Run run : runRepo.findByAppUser(appUser)) {
            BigDecimal dial = run.getDial();
            BigDecimal fullTrack = run.getFullTrack();

            if (dial.compareTo(fullTrack) <= 0 ) {
                overCounter++;
            }
            totalRuns++;
        }

        if (totalRuns == 0) {
            return null;
        }

        double overPercentage = ((double) overCounter / totalRuns) * 100;
        return new BigDecimal(overPercentage).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getReactionAverage() {
        AppUser appUser = getCurrentUser();
        if (appUser == null) {
            return null;
        }

        int totalRuns = 0;
        BigDecimal totalReactionTime = BigDecimal.ZERO;

        for (Run run : runRepo.findByAppUser(appUser)) {
            BigDecimal reaction = run.getReaction();
            totalReactionTime = totalReactionTime.add(reaction);
            totalRuns++;
        }

        if (totalRuns == 0) {
            return null;
        }

        return totalReactionTime.divide(BigDecimal.valueOf(totalRuns), 4, RoundingMode.HALF_UP);
    }
}
