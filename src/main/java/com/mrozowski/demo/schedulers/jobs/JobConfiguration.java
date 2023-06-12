package com.mrozowski.demo.schedulers.jobs;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(JobProperties.class)
class JobConfiguration {

  private final JobProperties properties;
  private final JobScheduler scheduler;

  @PostConstruct
  protected void init() {
   createSchedulers();
  }

  private void createSchedulers() {
    properties.jobs().forEach(this::createScheduler);
  }

  private void createScheduler(JobProperties.SchedulerJobConfiguration jobConfig) {
    log.info("Creating scheduler [{}]", jobConfig.name());
    var jobDetails = createJobDetails(jobConfig);
    var cronTrigger = createCronTrigger(jobDetails, jobConfig);
    try {
      scheduler.schedule(jobDetails, cronTrigger);
    } catch (SchedulerException e) {
      log.error("Failed to schedule job [" + cronTrigger.getKey() + "]", e);
    }
  }

  private static JobDetail createJobDetails(JobProperties.SchedulerJobConfiguration jobConfig) {
    return JobBuilder.newJob(ScheduledJob.class)
        .withIdentity(jobConfig.group(), jobConfig.name())
        .setJobData(createJobData(jobConfig))
        .build();
  }

  private static JobDataMap createJobData(JobProperties.SchedulerJobConfiguration jobConfig) {
    return new JobDataMap(
        Map.of(ScheduledJob.NAME, jobConfig.name(), ScheduledJob.PRIORITY, jobConfig.jobPriority()));
  }

  private static Trigger createCronTrigger(JobDetail jobDetail, JobProperties.SchedulerJobConfiguration jobConfig) {
    return TriggerBuilder.newTrigger()
        .forJob(jobDetail.getKey())
        .withPriority(jobConfig.jobPriority())
        .withIdentity(jobConfig.name(), jobConfig.group())
        .withSchedule(CronScheduleBuilder.cronSchedule(jobConfig.cron()))
        .build();
  }
}
