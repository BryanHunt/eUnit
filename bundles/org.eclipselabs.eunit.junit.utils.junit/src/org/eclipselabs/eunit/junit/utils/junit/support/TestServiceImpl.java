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

/**
 * @author bhunt
 * 
 */
public class TestServiceImpl implements ConfiguredTestService
{
	private Map<String, Object> properties;

	@Override
	public Map<String, Object> getProperties()
	{
		return properties;
	}

	public void activate(Map<String, Object> properties)
	{
		this.properties = properties;
	}
}
