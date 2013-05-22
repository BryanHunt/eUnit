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

package org.eclipselabs.eunit.junit.utils.junit.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Hashtable;

import org.eclipselabs.eunit.junit.utils.ServiceConfigurator;
import org.eclipselabs.eunit.junit.utils.junit.service.ConfiguredTestService;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author bhunt
 * 
 */
public class TestServiceConfigurator
{
	private static Hashtable<String, String> properties = new Hashtable<String, String>();

	static
	{
		properties.put("junit", "junit");
	}

	@Rule
	public ServiceConfigurator<ConfiguredTestService> serviceLocator = new ServiceConfigurator<ConfiguredTestService>(ConfiguredTestService.class,
			"org.eclipselabs.eunit.junit.utils.junit.test.configuredService", properties);

	@Test
	public void testGetService()
	{
		ConfiguredTestService service = serviceLocator.getService();
		assertThat(service.getProperties(), is(notNullValue()));
		assertThat((String) service.getProperties().get("junit"), is("junit"));
	}
}
