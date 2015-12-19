/*******************************************************************************
 * Copyright (c) 2013 Bryan Hunt.
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

import java.util.Dictionary;

import org.eclipselabs.eunit.junit.utils.bundle.Activator;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.util.tracker.ServiceTracker;

/**
 * This class extends ServiceLocator by supplying a service configuration
 * through the ConfigurationAdmin service. Note that a factory configuration
 * is created using the ConfigurationAdmin service. Like the base ServiceLocator,
 * this class is intended to be used as a JUnit @Rule. Usage example:
 * <p>
 * 
 * <pre>
 * &#064;Rule
 * public ServiceConfigurator&lt;TestService&gt; serviceLocator = new ServiceConfigurator&lt;TestService&gt;(TestService.class, &quot;org.example.test.service&quot;, properties);
 * 
 * &#064;Test
 * public void myTest()
 * {
 * 	TestService service = serviceLocator.getService();
 * }
 * </pre>
 * 
 * @author bhunt
 * 
 */
public class ServiceConfigurator<T> extends ServiceLocator<T>
{
	private ServiceTracker<ConfigurationAdmin, ConfigurationAdmin> configurationAdminServiceTracker;
	private String pid;
	private Dictionary<String, ?> properties;

	/**
	 * 
	 * @param type the service class
	 * @param pid the OSGi service id
	 * @param properties the service configuration properties
	 */
	public ServiceConfigurator(Class<T> type, String pid, Dictionary<String, ?> properties)
	{
		this(type, 1000, null, pid, properties);
	}

	/**
	 * 
	 * @param type the service class
	 * @param pid the OSGi service id
	 * @param properties the service configuration properties
	 */
	public ServiceConfigurator(Class<T> type, String filter, String pid, Dictionary<String, ?> properties)
	{
		this(type, 1000, filter, pid, properties);
	}

	/**
	 * 
	 * @param type the service class
	 * @param timeout the timeout to wait for the service in ms.
	 * @param pid the OSGi service id
	 * @param properties the service configuration properties
	 */
	public ServiceConfigurator(Class<T> type, long timeout, String filter, String pid, Dictionary<String, ?> properties)
	{
		super(type, timeout, filter);
		this.pid = pid;
		this.properties = properties;
	}

	@Override
	protected void before() throws Throwable
	{
		configurationAdminServiceTracker = new ServiceTracker<ConfigurationAdmin, ConfigurationAdmin>(Activator.getBundleContext(), ConfigurationAdmin.class, null);
		configurationAdminServiceTracker.open();
		ConfigurationAdmin configurationAdmin = configurationAdminServiceTracker.waitForService(getTimeout());

		assertThat("timed out waiting for the ConfigurationAdmin service", configurationAdmin, is(notNullValue()));

		Configuration configuration = configurationAdmin.createFactoryConfiguration(pid, null);
		configuration.update(properties);
		super.before();
	}

	@Override
	protected void after()
	{
		super.after();
		configurationAdminServiceTracker.close();
	}
}
