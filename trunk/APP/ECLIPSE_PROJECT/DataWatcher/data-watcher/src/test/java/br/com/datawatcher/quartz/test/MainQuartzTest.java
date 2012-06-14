package br.com.datawatcher.quartz.test;

import java.text.ParseException;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;

public class MainQuartzTest {
	
	public static void main(String[] args) {
		try {
			SchedulerFactory sf = new StdSchedulerFactory();
			Scheduler scheduler = sf.getScheduler();
			scheduler.start();
						
			CronTriggerImpl cron = new CronTriggerImpl();
			cron.setName("trigger");
			cron.setCronExpression("0/5 * * ? * *");
			
			JobDetailImpl job = new JobDetailImpl();
			job.setName("job");
			job.setJobClass(JobTest.class);
			
			
			scheduler.scheduleJob(job, cron);
		} catch (SchedulerException e1) {
			e1.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
