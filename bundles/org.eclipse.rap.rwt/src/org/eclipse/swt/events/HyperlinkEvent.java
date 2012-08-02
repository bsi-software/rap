/*******************************************************************************
 * Copyright (c) 2009 EclipseSource and others. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   EclipseSource - initial API and implementation
 *   Ralf Zahn (ARS) - browser history support (Bug 283291)
 ******************************************************************************/
package org.eclipse.swt.events;

import org.eclipse.rwt.Adaptable;
import org.eclipse.swt.internal.widgets.EventUtil;

/**
 * Instances of this class provide information about a hyperlink activation event.
 *
 * @see HyperlinkListener
 * @since 1.4
 * This property is part of the extended RAP Web-2.0 API, but not part of the SWT API
 */
public final class HyperlinkEvent extends TypedEvent {

  public static final int ACTIVATED = 9000;

  private static final long serialVersionUID = 1L;
  private static final Class LISTENER = HyperlinkListener.class;

  public final String url;

  public HyperlinkEvent( Object source, final String url) {
    super( source, ACTIVATED );
    this.url = url;
  }

  protected void dispatchToObserver( Object listener ) {
    switch( getID() ) {
      case ACTIVATED:
        ( ( HyperlinkListener )listener ).activated( this );
      break;
      default:
        throw new IllegalStateException( "Invalid event handler type." );
    }
  }

  protected Class getListenerType() {
    return LISTENER;
  }
  
  protected boolean allowProcessing() {
    return EventUtil.isAccessible( widget );
  }

  public static void addListener( final Adaptable adaptable,
                                  final HyperlinkListener listener )
  {
    addListener( adaptable, LISTENER, listener );
  }

  public static void removeListener( final Adaptable adaptable,
                                     final HyperlinkListener listener )
  {
    removeListener( adaptable, LISTENER, listener );
  }

  public static boolean hasListener( final Adaptable adaptable ) {
    return hasListener( adaptable, LISTENER );
  }

  public static Object[] getListeners( final Adaptable adaptable ) {
    return getListener( adaptable, LISTENER );
  }
}
