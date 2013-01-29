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

/**
 * Instances of this class provide information about a hyperlink activation event.
 *
 * @see HyperlinkListener
 * @since 2.0
 * This property is part of the extended RAP Web-2.0 API, but not part of the SWT API
 */
public final class HyperlinkEvent extends TypedEvent {
  private static final long serialVersionUID = 1L;

  public static final int ACTIVATED = 9000;

  public final String url;

  public HyperlinkEvent( Object source, final String url) {
    super( source );
    this.url = url;
  }
}
