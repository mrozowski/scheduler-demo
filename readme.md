# Schedulers demo

Simple usage of quartz to create scheduled job from configuration.

In 'application.yaml' file can add and configure multiple jobs

```yaml
scheduler:
  jobs:
    - cron: '0/5 * * * * ?' 
      job-priority: 1 
      group: 'groupName'
      name: 'schedulerName#1'
    - cron: '0 1 * * * ?'
      job-priority: 1
      group: 'groupName'
      name: 'schedulerName#2'
```
