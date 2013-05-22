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

package org.eclipselabs.eunit.junit.utils.junit.service;

import java.util.Dictionary;

import org.osgi.service.component.ComponentContext;

/**
 * @author bhunt
 * 
 */
public class TestServiceImpl implements ConfiguredTestService
{
	private Dictionary<Object, Object> properties;

	@Override
	public Dictionary<Object, Object> getProperties()
	{
		return properties;
	}

	@SuppressWarnings("unchecked")
	public void activate(ComponentContext context)
	{
		properties = context.getProperties();
	}
}
