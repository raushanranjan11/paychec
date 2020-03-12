package com.thinkss.paycheck.shedule.job;

import java.util.Properties;

import org.quartz.CronScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
@Configuration
public class QuartzConfig {

   @Value("${org.quartz.scheduler.instanceName}")//org.quartz.scheduler.instanceName
   private String instanceName;

   @Value("${org.quartz.scheduler.instanceId}")
   private String instanceId;

   @Value("${org.quartz.threadPool.threadCount}")
   private String threadCount;

   @Value("${job.startDelay}")
   private Long startDelay;

   @Value("${job.repeatInterval}")
   private Long repeatInterval;

   /*@Value("${job.description}")
   private String description;

   @Value("${job.key}")
   private String key;*/
 

   @Bean
   public SpringJobFactory jobFactory(ApplicationContext applicationContext) {
	   
	   System.out.println("--------------jobFactory--------------------------------");

	   SpringJobFactory sampleJobFactory = new SpringJobFactory();
       sampleJobFactory.setApplicationContext(applicationContext);
       return sampleJobFactory;
   }

   @Bean
   public SchedulerFactoryBean schedulerFactoryBean(ApplicationContext applicationContext) {
	   System.out.println("-------------------schedulerFactoryBean---------------------------");

       SchedulerFactoryBean factory = new SchedulerFactoryBean();

       factory.setOverwriteExistingJobs(true);
       factory.setJobFactory(jobFactory(applicationContext));

       Properties quartzProperties = new Properties();
       quartzProperties.setProperty("org.quartz.scheduler.instanceName",instanceName);
       quartzProperties.setProperty("org.quartz.scheduler.instanceId",instanceId);
       quartzProperties.setProperty("org.quartz.threadPool.threadCount",threadCount);

     //  factory.setDataSource(dataSource);

       factory.setQuartzProperties(quartzProperties);
       factory.setTriggers(emailJobTrigger().getObject());

       return factory;
   }

   @Bean(name = "emailJobTrigger")
//   @Scheduled(cron = "0/20 * * * * ?")
   public SimpleTriggerFactoryBean emailJobTrigger() {
	   System.out.println("--------------------emailJobTrigger--------------------------");

       SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
       factoryBean.setJobDetail(emailJobDetails().getObject());
       factoryBean.setStartDelay(startDelay);
       factoryBean.setRepeatInterval(repeatInterval);
       factoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
       factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);
//       factoryBean.setJobDataMap(jobDataMap);
       
       return factoryBean;
   }

   @Bean(name = "emailJobDetails")
   public JobDetailFactoryBean emailJobDetails() {
	   System.out.println("-----------------------emailJobDetails-----------------------");

       JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
       jobDetailFactoryBean.setJobClass(EmailJob.class);
//       jobDetailFactoryBean.setDescription(description);
       jobDetailFactoryBean.setDurability(true);
//       jobDetailFactoryBean.setName(key);

       return jobDetailFactoryBean;
   }
   
}