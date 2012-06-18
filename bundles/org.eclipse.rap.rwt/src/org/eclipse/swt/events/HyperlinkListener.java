/*******************************************************************************
 * Copyright (c) 2009, 2011 EclipseSource and others. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   EclipseSource - initial API and implementation
 *   Ralf Zahn (ARS) - browser history support (Bug 283291)
 ******************************************************************************/
package org.eclipse.swt.events;

import org.eclipse.swt.internal.SWTEventListener;
import org.eclipse.swt.widgets.Table;

/**
 * An event handler that is invoked whenever the user clicks on a hyperlink on inlined html
 * @see HyperlinkEvent
 * @see for example {@link Table#addHyperlinkListener(HyperlinkListener)}
 * @since 1.4
 * This property is part of the extended RAP Web-2.0 API, but not part of the SWT API
 */
public interface HyperlinkListener extends SWTEventListener {

  /**
   * The event handler method.
   * 
   * @param event the {@link HyperlinkEvent} object
   */
  void activated( HyperlinkEvent event );
}
