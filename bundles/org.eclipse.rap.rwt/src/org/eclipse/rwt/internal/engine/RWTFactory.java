/*******************************************************************************
 * Copyright (c) 2011 EclipseSource and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    EclipseSource - initial API and implementation
 ******************************************************************************/
package org.eclipse.rwt.internal.engine;

import org.eclipse.rwt.internal.branding.BrandingManager;
import org.eclipse.rwt.internal.lifecycle.EntryPointManager;
import org.eclipse.rwt.internal.lifecycle.LifeCycleFactory;
import org.eclipse.rwt.internal.service.*;
import org.eclipse.rwt.internal.service.StartupPage.IStartupPageConfigurer;


public class RWTFactory {
  
  public static LifeCycleFactory getLifeCycleFactory() {
    Object singleton = ApplicationContext.getSingleton( LifeCycleFactory.class );
    return ( LifeCycleFactory )singleton;
  }
  
  public static BrandingManager getBrandingManager() {
    Object singleton = ApplicationContext.getSingleton( BrandingManager.class );
    return ( BrandingManager )singleton;
  }
  
  public static EntryPointManager getEntryPointManager() {
    Object singleton = ApplicationContext.getSingleton( EntryPointManager.class );
    return ( EntryPointManager )singleton;
  }

  public static ServiceManager getServiceManager() {
    Object singleton = ApplicationContext.getSingleton( ServiceManager.class );
    return ( ServiceManager )singleton;
  }

  public static StartupPage getStartupPage() {
    Object singleton = ApplicationContext.getSingleton( StartupPage.class );
    return ( StartupPage )singleton;
  }
  
  public static IStartupPageConfigurer getStartupPageConfigurer() {
    Object singleton = ApplicationContext.getSingleton( StartupPageConfigurer.class );
    return ( IStartupPageConfigurer )singleton;
  }


  private RWTFactory() {
    // prevent instantiation
  }
}
