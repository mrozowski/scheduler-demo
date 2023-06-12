package com.mrozowski.demo.schedulers.jobs

import com.mrozowski.demo.schedulers.notification.NotificationCommand
import com.mrozowski.demo.schedulers.notification.Sender
import org.quartz.JobDetail
import org.quartz.JobExecutionContext
import spock.lang.Specification
import spock.lang.Subject

class ScheduledJobSpec extends Specification {

    def sender = Mock(Sender)

    @Subject
    def underTest = new ScheduledJob(sender)

    def 'should call sender'() {
        given:
        def priority = 1
        def name = 'schedulerName'
        def command = new NotificationCommand(priority, name)

        and:
        def jobDetail = Mock(JobDetail)
        jobDetail.getJobDataMap() >> [
                'priority': priority,
                'name'    : name
        ]
        def context = Mock(JobExecutionContext)
        context.getJobDetail() >> jobDetail

        when:
        underTest.execute(context)

        then:
        1 * sender.sendNotification(command)
    }
}
