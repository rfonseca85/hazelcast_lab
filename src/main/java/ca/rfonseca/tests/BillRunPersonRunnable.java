package ca.rfonseca.tests;

import java.io.Serializable;
import java.util.concurrent.CountDownLatch;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IQueue;

import ca.rfonseca.model.Person;

public class BillRunPersonRunnable implements Runnable, Serializable {

	private static final long serialVersionUID = 1L;

	private String distributedQueueName;

	public BillRunPersonRunnable(String distributedQueueName) {
		this.distributedQueueName = distributedQueueName;
	}

	@Override
	public void run() {
		final CountDownLatch countDownLatch = new CountDownLatch(10);
		Thread[] threads = new Thread[(int) countDownLatch.getCount()];
		for (int i = 0; i < countDownLatch.getCount(); i++) {
			threads[i] = new Thread() {
				@Override
				public void run() {
					consumeQueue();
					countDownLatch.countDown();
				}
			};
			threads[i].setName("Person Executor Consumer " + i);
			threads[i].setDaemon(true);
			threads[i].start();
		}
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
		}
	}

	protected void consumeQueue() {
		try {
			Config config = new Config();
			HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(config);
			IQueue<Person> queue = hazelcastInstance.getQueue(this.distributedQueueName);

			
			for (Person person : queue) {
				System.out.println(person.getName());
			}
			
			
			
		} catch (Throwable e) {
			System.out.println(e);
		}
	}

}
