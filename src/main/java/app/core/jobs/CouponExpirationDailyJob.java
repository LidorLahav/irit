package app.core.jobs;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.core.services.JobService;


/**
 * The daily job task that goes over the coupons daily
 * to check if any coupons have expired and then deletes them
 */
@Component
public class CouponExpirationDailyJob implements Runnable {
	
	
	private JobService jobService;
	private boolean quit;
	private Thread job;
	
	@Autowired
	public CouponExpirationDailyJob(JobService jobService) {
		this.jobService = jobService;
		this.quit = false;
	}
	
	@Override
	public void run() {
		System.out.println("Job started");
		while(!quit) {
			System.out.println("Job started deleting coupons");
			jobService.deleteExpiredCoupons();
			try {
				Thread.sleep(TimeUnit.DAYS.toMillis(1));
			} catch (InterruptedException e) {
				System.out.println("Thread interrupted");
			}
		}
	}
	
	@PreDestroy
	public void stop() {
		this.quit = true;
		job.interrupt();
		System.out.println("job stopped");
	}
	
	@PostConstruct
	public void start() {
		job = new Thread(this);
		job.start();
	}
	
}
