package com.mrozowski.demo.schedulers.jobs

import org.quartz.impl.JobDetailImpl
import org.quartz.impl.triggers.CronTriggerImpl
import spock.lang.Specification
import spock.lang.Subject

class JobConfigurationSpec extends Specification {


    def scheduler = Mock(JobScheduler)
    def properties = new JobProperties([new JobProperties.SchedulerJobConfiguration(
            '0/5 * * * * ?', 1, 'groupName', 'schedulerName'
    )])

    @Subject
    def underTest = new JobConfiguration(properties, scheduler)

    def 'should create schedulers for each business unit'() {
        when:
        underTest.init()

        then:
        1 * scheduler.schedule(_, _) >> {args ->
            with(args[0] as JobDetailImpl) {
                with(getJobDataMap()) {
                    get(ScheduledJob.PRIORITY) == 1
                    get(ScheduledJob.NAME) == 'schedulerName'
                }
            }

            with(args[1] as CronTriggerImpl) {
                priority == 1
                cronExpression == '0/5 * * * * ?'
            }
        }
    }
}
