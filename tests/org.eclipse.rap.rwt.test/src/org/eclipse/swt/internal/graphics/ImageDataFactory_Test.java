/*******************************************************************************
 * Copyright (c) 2002, 2013 Innoopract Informationssysteme GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Innoopract Informationssysteme GmbH - initial API and implementation
 *    EclipseSource - ongoing development
 *    Frank Appel - replaced singletons and static fields (Bug 337787)
 ******************************************************************************/
package org.eclipse.swt.internal.graphics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.graphics.Graphics;
import org.eclipse.rap.rwt.service.ResourceManager;
import org.eclipse.rap.rwt.testfixture.Fixture;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class ImageDataFactory_Test {

  private static final ClassLoader CLASS_LOADER = ImageDataFactory_Test.class.getClassLoader();

  private ImageDataFactory imageDataFactory;

  @Before
  public void setUp() {
    Fixture.createApplicationContext();
    Fixture.createServiceContext();
    Fixture.useDefaultResourceManager();
    imageDataFactory = new ImageDataFactory( RWT.getApplicationContext().getResourceManager() );
    new Display();
  }

  @After
  public void tearDown() {
    Fixture.disposeOfServiceContext();
    Fixture.disposeOfApplicationContext();
  }

  @Test
  public void testFindImageData() {
    Image image = Graphics.getImage( Fixture.IMAGE_50x100, CLASS_LOADER );
    ResourceManager resourceManager = RWT.getResourceManager();
    assertTrue( resourceManager.isRegistered( image.internalImage.getResourceName() ) );
    ImageData imageData = imageDataFactory.findImageData( image.internalImage );
    assertNotNull( imageData );
    assertEquals( 50, imageData.width );
    assertEquals( 100, imageData.height );
  }

  @Test
  public void testFindImageDataUsesCachedImage() {
    Image image = Graphics.getImage( Fixture.IMAGE_50x100, CLASS_LOADER );
    ImageData imageData1 = imageDataFactory.findImageData( image.internalImage );
    ImageData imageData2 = imageDataFactory.findImageData( image.internalImage );
    assertNotSame( imageData1, imageData2 );
    assertEquals( imageData1.data.length, imageData2.data.length );
  }

  @Test
  public void testFindImageDataWithBlankImage() {
    Image blankImage = Graphics.getImage( "resources/images/blank.gif", CLASS_LOADER );
    ImageData blankData = imageDataFactory.findImageData( blankImage.internalImage );
    assertNotNull( blankData );
    assertEquals( 1, blankData.width );
    assertEquals( 1, blankData.height );
  }

  @Test
  public void testFindImageDataWithNull() {
    try {
      imageDataFactory.findImageData( null );
      fail( "Must not allow null-argument" );
    } catch( NullPointerException expected ) {
    }
  }

}
