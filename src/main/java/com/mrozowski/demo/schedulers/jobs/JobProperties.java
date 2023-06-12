package com.mrozowski.demo.schedulers.jobs;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "scheduler")
record JobProperties(List<SchedulerJobConfiguration> jobs) {

  static record SchedulerJobConfiguration(String cron, int jobPriority, String group, String name) {}
}
