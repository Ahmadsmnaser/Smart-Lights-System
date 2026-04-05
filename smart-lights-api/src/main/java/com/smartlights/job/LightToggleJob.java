package com.smartlights.job;

import com.smartlights.service.LightService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LightToggleJob implements Job {

    @Autowired
    private LightService lightService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String lightId = context.getMergedJobDataMap().getString("lightId");
        lightService.toggleAndLog(lightId);
    }
}
