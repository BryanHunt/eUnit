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
 * @author bhunt
 * 
 */
public class ServiceFactoryConfigurator<T> extends ServiceLocator<T>
{
	private ServiceTracker<ConfigurationAdmin, ConfigurationAdmin> configurationAdminServiceTracker;
	private String pid;
	private Dictionary<String, ?> properties;

	public ServiceFactoryConfigurator(Class<T> type, String pid, Dictionary<String, ?> properties)
	{
		this(type, 1000, pid, properties);
	}

	public ServiceFactoryConfigurator(Class<T> type, long timeout, String pid, Dictionary<String, ?> properties)
	{
		super(type, timeout);
		this.pid = pid;
		this.properties = properties;
	}

	@Override
	protected void before() throws Throwable
	{
		configurationAdminServiceTracker = new ServiceTracker<ConfigurationAdmin, ConfigurationAdmin>(Activator.getBundleContext(), ConfigurationAdmin.class, null);
		configurationAdminServiceTracker.open();
		ConfigurationAdmin configurationAdmin = configurationAdminServiceTracker.waitForService(getTimeout());
		assertThat("Timed out waiting for the ConfigurationAdmin service", configurationAdmin, is(notNullValue()));
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
