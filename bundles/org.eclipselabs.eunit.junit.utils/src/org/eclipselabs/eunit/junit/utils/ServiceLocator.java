/*******************************************************************************
 * Copyright (c) 2012 Bryan Hunt.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Bryan Hunt - initial API and implementation
 *******************************************************************************/

package org.eclipselabs.eunit.junit.utils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.eclipselabs.eunit.junit.utils.bundle.Activator;
import org.junit.rules.ExternalResource;
import org.osgi.util.tracker.ServiceTracker;

/**
 * This class is intended to be used as a JUnit @Rule. It will create a
 * service tracker for the specified service and wait for the service
 * up to the specified timeout. Usage example:
 * <p>
 * 
 * <pre>
 * &#064;Rule
 * public ServiceLocator&lt;TestService&gt; serviceLocator = new ServiceLocator&lt;TestService&gt;(TestService.class);
 * 
 * &#064;Test
 * public void myTest()
 * {
 * 	TestService service = serviceLocator.getService();
 * }
 * </pre>
 * 
 * @author bhunt
 */
public class ServiceLocator<T> extends ExternalResource
{
	private String filter;
	private long timeout;
	private Class<T> type;
	private ServiceTracker<T, T> serviceTracker;
	private T service;

	/**
	 * Defaults the timeout to 1000 ms.
	 * 
	 * @param type the service class
	 */
	public ServiceLocator(Class<T> type)
	{
		this(type, 1000);
	}

	public ServiceLocator(Class<T> type, String filter)
	{
		this(type, 1000, filter);
	}

	/**
	 * 
	 * @param type the service class
	 * @param timeout the timeout to wait for the service in ms.
	 */
	public ServiceLocator(Class<T> type, long timeout)
	{
		this(type, timeout, null);
	}

	/**
	 * 
	 * @param type the service class
	 * @param timeout the timeout to wait for the service in ms.
	 */
	public ServiceLocator(Class<T> type, long timeout, String filter)
	{
		this.timeout = timeout;
		this.type = type;
		this.filter = filter;
	}

	/**
	 * 
	 * @return the service instance
	 */
	public T getService()
	{
		return service;
	}

	@Override
	protected void before() throws Throwable
	{
		if (filter != null)
			serviceTracker = new ServiceTracker<T, T>(Activator.getBundleContext(), Activator.getBundleContext().createFilter(filter), null);
		else
			serviceTracker = new ServiceTracker<T, T>(Activator.getBundleContext(), type, null);

		serviceTracker.open();

		service = waitForService();
		assertThat("timed out waiting for service: " + type.getName(), service, is(notNullValue()));
		super.before();
	}

	@Override
	protected void after()
	{
		serviceTracker.close();
		super.after();
	}

	/**
	 * @return the timeout to wait for the service
	 */
	protected long getTimeout()
	{
		return timeout;
	}

	/**
	 * 
	 * @return the service or null if there was a timeout waiting for the service
	 * @throws InterruptedException if the wait timer was interrupted
	 */
	protected T waitForService() throws InterruptedException
	{
		return serviceTracker.waitForService(getTimeout());
	}
}
