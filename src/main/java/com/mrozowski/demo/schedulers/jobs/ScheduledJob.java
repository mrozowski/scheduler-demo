package com.mrozowski.demo.schedulers.jobs;

import com.mrozowski.demo.schedulers.notification.NotificationCommand;
import com.mrozowski.demo.schedulers.notification.Sender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class ScheduledJob implements Job {

  static final String PRIORITY = "priority";
  static final String NAME = "name";
  private final Sender sender;

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    var jobDataMap = context.getJobDetail().getJobDataMap();

    var name = jobDataMap.getString(NAME);
    int priority = jobDataMap.getInt(PRIORITY);
    log.info("Triggered [{}]", name);

    var command = new NotificationCommand(priority, name);
    sender.sendNotification(command);
  }
}
