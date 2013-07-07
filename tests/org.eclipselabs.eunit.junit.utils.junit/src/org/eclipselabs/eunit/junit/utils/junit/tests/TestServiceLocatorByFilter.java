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

import org.eclipselabs.eunit.junit.utils.ServiceLocator;
import org.eclipselabs.eunit.junit.utils.junit.service.TestService;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author bhunt
 * 
 */
public class TestServiceLocatorByFilter
{
	@Rule
	public ServiceLocator<TestService> serviceLocator = new ServiceLocator<TestService>(TestService.class, "(alias=junit)");

	@Test
	public void testGetService()
	{
		TestService service = serviceLocator.getService();
		assertThat(service.getProperties(), is(notNullValue()));
	}
}
