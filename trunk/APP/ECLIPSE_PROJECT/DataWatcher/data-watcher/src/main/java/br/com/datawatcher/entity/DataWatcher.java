/**
 * 
 */
package br.com.datawatcher.entity;

import java.util.ArrayList;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerKey;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;

import br.com.datawatcher.common.DataWatcherConstants;
import br.com.datawatcher.exception.DataWatcherException;
import br.com.datawatcher.exception.DataWatcherRuntimeException;

/**
 * @author carrefour
 *
 */
public class DataWatcher implements Job {

	private List<DataMapping> mappings = new ArrayList<DataMapping>();
	
	public DataWatcher() { }

	public DataWatcher addMapping(DataMapping dataMapping) {
		if (dataMapping != null) {
			this.mappings.add(dataMapping);
			return this;
		} else throw new IllegalArgumentException("parameter can not be null");
	}
	
	public void start() throws DataWatcherException {
		try {
			SchedulerFactory sf = new StdSchedulerFactory();
			Scheduler scheduler = sf.getScheduler();
		
			for (DataMapping dataMapping : this.mappings) {
				JobDataMap jobDataMap = new JobDataMap();
				jobDataMap.put(dataMapping.getIdentifier(), dataMapping);
				
				CronTriggerImpl cron = new CronTriggerImpl();
				cron.setName(dataMapping.getIdentifier());
				cron.setKey(new TriggerKey(dataMapping.getIdentifier(), DataWatcherConstants.GROUP_TRIGGER));
				cron.setCronExpression(dataMapping.getCheckChange().getCronExpression());
				cron.setJobDataMap(jobDataMap);

				JobDetailImpl job = new JobDetailImpl();
				job.setName(dataMapping.getIdentifier());
				job.setJobClass(this.getClass());
				
				scheduler.scheduleJob(job, cron);
				
				dataMapping.startup();
			}
			
			scheduler.start();
		} catch (Exception e) {
			throw new DataWatcherException(e);
		}
	}
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			String triggerKeyName = context.getTrigger().getKey().getName();
			DataMapping dataMapping = (DataMapping) context.getTrigger().getJobDataMap().get(triggerKeyName);
			dataMapping.checkChange();
		} catch (DataWatcherException e) {
			throw new DataWatcherRuntimeException(e);
		}
	}
}
