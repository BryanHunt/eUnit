/*******************************************************************************
 * Copyright (c) 2011 Bryan Hunt.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Bryan Hunt - initial API and implementation
 *******************************************************************************/

package org.eclipselabs.eunit.junit.utils.bundle;

import org.eclipselabs.emf.ext.ResourceSetFactory;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author bhunt
 * 
 */
public class Activator implements BundleActivator
{
	public static BundleContext getBundleContext()
	{
		return bundleContext;
	}

	public static ResourceSetFactory getResourceSetFactory()
	{
		return resourceSetFactoryTracker.getService();
	}

	@Override
	public void start(BundleContext context) throws Exception
	{
		bundleContext = context;
		resourceSetFactoryTracker = new ServiceTracker<ResourceSetFactory, ResourceSetFactory>(context, ResourceSetFactory.class, null);
		resourceSetFactoryTracker.open();
	}

	@Override
	public void stop(BundleContext context) throws Exception
	{
		if (resourceSetFactoryTracker != null)
			resourceSetFactoryTracker.close();

		bundleContext = null;
	}

	private static BundleContext bundleContext;
	private static ServiceTracker<ResourceSetFactory, ResourceSetFactory> resourceSetFactoryTracker;
}