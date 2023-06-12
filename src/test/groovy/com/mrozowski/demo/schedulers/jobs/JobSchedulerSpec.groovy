package com.mrozowski.demo.schedulers.jobs

import org.quartz.JobDetail
import org.quartz.JobKey
import org.quartz.Scheduler
import org.quartz.impl.triggers.CronTriggerImpl
import spock.lang.Specification
import spock.lang.Subject

class JobSchedulerSpec extends Specification {

    def scheduler = Mock(Scheduler)
    def jobDetail = Mock(JobDetail)
    def trigger = Mock(CronTriggerImpl)

    @Subject
    def underTest = new JobScheduler(scheduler)

    def 'should schedule new job'() {
        given:
        def jobKey = new JobKey('test')
        trigger.getJobKey() >> jobKey

        when:
        underTest.schedule(jobDetail, trigger)

        then:
        0 * scheduler.getTrigger(_)
        1 * scheduler.checkExists(jobKey) >> false
        1 * scheduler.scheduleJob(jobDetail, trigger)
    }
}
