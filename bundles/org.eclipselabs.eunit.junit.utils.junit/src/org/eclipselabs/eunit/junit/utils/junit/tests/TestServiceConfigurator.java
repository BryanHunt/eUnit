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

import java.util.Dictionary;
import java.util.Hashtable;

import org.eclipselabs.eunit.junit.utils.ServiceConfigurator;
import org.eclipselabs.eunit.junit.utils.junit.support.TestService;
import org.eclipselabs.eunit.junit.utils.junit.support.TestService3Impl;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author bhunt
 * 
 */
public class TestServiceConfigurator
{
	private static Dictionary<String, Object> properties = new Hashtable<>();

	static
	{
		properties.put("junit", "junit");
	}

	@Rule
	public ServiceConfigurator<TestService> serviceLocator = new ServiceConfigurator<TestService>(TestService.class, TestService3Impl.PID, properties);

	@Test
	public void testGetService()
	{
		TestService service = serviceLocator.getService();
		assertThat(service.getProperties(), is(notNullValue()));
		assertThat(service.getProperties().get("instance"), is(3));
		assertThat((String) service.getProperties().get("junit"), is("junit"));
	}
}
