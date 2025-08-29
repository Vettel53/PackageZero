package com.example.application.run;

import com.example.application.account.AppUser;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RunService {

    private RunRepo runRepo;

    public RunService(RunRepo runRepo) {
        this.runRepo = runRepo;
    }

    public List<Run> getUserRuns(AppUser appUser) {
        return runRepo.findByAppUser(appUser);
    }

}
