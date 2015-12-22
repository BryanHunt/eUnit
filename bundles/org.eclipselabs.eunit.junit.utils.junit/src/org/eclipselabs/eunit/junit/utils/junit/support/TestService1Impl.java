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

package org.eclipselabs.eunit.junit.utils.junit.support;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author bhunt
 * 
 */
@Component(service = TestService.class, property = {"instance=1"})
public class TestService1Impl implements TestService
{
	private Map<String, Object> properties;

	@Override
	public Map<String, Object> getProperties()
	{
		return properties;
	}

	@Activate
	public void activate(Map<String, Object> properties)
	{
		this.properties = properties;
	}
}
