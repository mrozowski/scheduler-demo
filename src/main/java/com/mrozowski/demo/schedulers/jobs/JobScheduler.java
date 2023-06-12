package com.mrozowski.demo.schedulers.jobs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class JobScheduler {

  private final Scheduler scheduler;

  public void schedule(JobDetail jobDetail, Trigger trigger) throws SchedulerException {
    var jobKey = trigger.getJobKey();
    if (scheduler.checkExists(jobKey)) {
      log.info("Job with key [{}] already exists", jobKey);
      return;
    }
    log.info("Scheduling a new job with key [{}]", jobKey);
    scheduler.scheduleJob(jobDetail, trigger);
  }
}
