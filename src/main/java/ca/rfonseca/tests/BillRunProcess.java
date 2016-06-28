package ca.rfonseca.tests;

import java.util.Map;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IExecutorService;
import com.hazelcast.core.IQueue;
import com.hazelcast.core.Member;
import com.hazelcast.core.MultiExecutionCallback;

import ca.rfonseca.model.Person;
import ca.rfonseca.utils.MOCKUtils;

public class BillRunProcess {

	public static void executeBillRun() throws Exception {

		//starting hazelcast configuration and getting an instance
		Config config = new Config();
		HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(config);
		
		
		//creating a queue of person
		IQueue<Person> distributedPersonQueue = hazelcastInstance.getQueue("PersonQueue");
		distributedPersonQueue.clear();
		for(Person person : MOCKUtils.getPersonList()) {
			distributedPersonQueue.add(person);
		}

		//if there is persons to process
		if(!distributedPersonQueue.isEmpty()) {
		
			IExecutorService executorService = hazelcastInstance.getExecutorService("PROCESSPERSONS");
			BillRunPersonRunnable billRunPersonExecutor = new BillRunPersonRunnable("PersonQueue");

			
			final Object semaphore = new Object();
			synchronized (semaphore) {
				executorService.submitToAllMembers(billRunPersonExecutor, new MultiExecutionCallback() {
					@Override
					public void onResponse(Member arg0, Object arg1) {
						
					}
					@Override
					public void onComplete(Map<Member, Object> arg0) {
						//All members executed
						synchronized (semaphore) {
							semaphore.notify();
						}
					}
				});
				//Waiting for Person to be Processed by the Distributed Processes");
				semaphore.wait();
			}
			//Person Processed");
		} else {
			//No Account Bill Cycle to Process
		}


		
		
		

	}

}
