package com.smartlights.config;

import com.smartlights.entity.Light;
import com.smartlights.job.LightToggleJob;
import com.smartlights.repository.LightRepository;
import com.smartlights.service.LightService;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.SimpleScheduleBuilder;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class QuartzSchedulerConfig {

    @Bean
    ApplicationRunner scheduleLightJobs(
            Scheduler scheduler,
            LightRepository lightRepository,
            LightService lightService) {
        return args -> {
            List<Light> lights = lightRepository.findAll();

            for (Light light : lights) {
                String lightId = light.getId();

                JobDetail jobDetail = JobBuilder.newJob(LightToggleJob.class)
                        .withIdentity("light-" + lightId)
                        .usingJobData("lightId", lightId)
                        .build();

                Trigger trigger = TriggerBuilder.newTrigger()
                        .withIdentity("trigger-" + lightId)
                        .startNow()
                        .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInSeconds(15)
                                .repeatForever())  
                        .build();

                if (!scheduler.checkExists(jobDetail.getKey())) {
                    scheduler.scheduleJob(jobDetail, trigger);
                    lightService.setInitialInterval(lightId, 15);
                    System.out.println("Scheduled Quartz job for light " + lightId);
                }
            }
        };
    }
}