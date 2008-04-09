/*******************************************************************************
 * Copyright (c) 2002-2006 Innoopract Informationssysteme GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Innoopract Informationssysteme GmbH - initial API and implementation
 ******************************************************************************/
package org.eclipse.swt.internal.widgets.tablekit;

import junit.framework.TestCase;

import org.eclipse.rwt.Fixture;
import org.eclipse.rwt.graphics.Graphics;
import org.eclipse.rwt.internal.lifecycle.*;
import org.eclipse.rwt.internal.service.RequestParams;
import org.eclipse.rwt.lifecycle.*;
import org.eclipse.swt.RWTFixture;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.internal.events.ActivateAdapter;
import org.eclipse.swt.internal.events.ActivateEvent;
import org.eclipse.swt.internal.widgets.*;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.*;

public class TableLCA_Test extends TestCase {

  public void testPreserveValues() {
    Display display = new Display();
    Shell shell = new Shell( display );
    Table table = new Table( shell, SWT.BORDER );
    RWTFixture.markInitialized( display );
    RWTFixture.preserveWidgets();
    IWidgetAdapter adapter = WidgetUtil.getAdapter( table );
    Object headerHeight = adapter.getPreserved( TableLCA.PROP_HEADER_HEIGHT );
    assertEquals( new Integer( 0 ), headerHeight );
    Object headerVisible = adapter.getPreserved( TableLCA.PROP_HEADER_VISIBLE );
    assertEquals( Boolean.FALSE, headerVisible );
    Object linesVisible = adapter.getPreserved( TableLCA.PROP_LINES_VISIBLE );
    assertEquals( Boolean.FALSE, linesVisible );
    Object itemHeight = adapter.getPreserved( TableLCA.PROP_ITEM_HEIGHT );
    assertEquals( new Integer( table.getItemHeight() ), itemHeight );
    assertEquals( new Integer( 0 ),
                  adapter.getPreserved( TableLCA.PROP_ITEM_COUNT ) );
    Object topIndex = adapter.getPreserved( TableLCA.PROP_TOP_INDEX );
    assertEquals( new Integer( table.getTopIndex() ), topIndex );
    Boolean hasListeners
     = ( Boolean )adapter.getPreserved( Props.SELECTION_LISTENERS );
    assertEquals( Boolean.FALSE, hasListeners );
    Object defaultColumnwidth
     = adapter.getPreserved( TableLCA.PROP_DEFAULT_COLUMN_WIDTH );
    int defaultColumnWidth2 = TableLCA.getDefaultColumnWidth( table );
    assertEquals( new Integer( defaultColumnWidth2 ), defaultColumnwidth );
    Object[] itemMetrics
     = ( Object[] )adapter.getPreserved( TableLCAUtil.PROP_ITEM_METRICS );
    Integer imageleft1 = ( TableLCAUtil.getItemMetrics( table )[ 0 ] ).imageLeft;
    assertEquals( imageleft1,
                  ( ( TableLCAUtil.ItemMetrics )itemMetrics[ 0 ] ).imageLeft );
    Integer imagewidth1
     = ( TableLCAUtil.getItemMetrics( table )[ 0 ] ).imageWidth;
    assertEquals( imagewidth1,
                  ( ( TableLCAUtil.ItemMetrics )itemMetrics[ 0 ] ).imageWidth );
    Integer textleft1 = ( TableLCAUtil.getItemMetrics( table )[ 0 ] ).textLeft;
    assertEquals( textleft1,
                  ( ( TableLCAUtil.ItemMetrics )itemMetrics[ 0 ] ).textLeft );
    Integer textwidth1 = ( TableLCAUtil.getItemMetrics( table )[ 0 ] ).textWidth;
    assertEquals( textwidth1,
                  ( ( TableLCAUtil.ItemMetrics )itemMetrics[ 0 ] ).textWidth );
    RWTFixture.clearPreserved();
    TableColumn tc1 = new TableColumn( table, SWT.CENTER );
    tc1.setText( "column1" );
    tc1.setWidth( 50 );
    TableColumn tc2 = new TableColumn( table, SWT.CENTER );
    tc2.setText( "column2" );
    tc2.setWidth( 50 );
    TableItem item1 = new TableItem( table, SWT.NONE );
    item1.setText( 0, "item11" );
    item1.setText( 1, "item12" );
    TableItem item2 = new TableItem( table, SWT.NONE );
    item2.setText( 0, "item21" );
    item2.setText( 1, "item22" );
    table.setHeaderVisible( true );
    table.setLinesVisible( true );
    table.setTopIndex( 1 );
    SelectionListener selectionListener = new SelectionAdapter() {
    };
    table.addSelectionListener( selectionListener );
    RWTFixture.preserveWidgets();
    adapter = WidgetUtil.getAdapter( table );
    headerHeight = adapter.getPreserved( TableLCA.PROP_HEADER_HEIGHT );
    assertEquals( new Integer( table.getHeaderHeight() ), headerHeight );
    headerVisible = adapter.getPreserved( TableLCA.PROP_HEADER_VISIBLE );
    assertEquals( Boolean.TRUE, headerVisible );
    linesVisible = adapter.getPreserved( TableLCA.PROP_LINES_VISIBLE );
    assertEquals( Boolean.TRUE, linesVisible );
    itemHeight = adapter.getPreserved( TableLCA.PROP_ITEM_HEIGHT );
    assertEquals( new Integer( table.getItemHeight() ), itemHeight );
    assertEquals( new Integer( 2 ),
                  adapter.getPreserved( TableLCA.PROP_ITEM_COUNT ) );
    assertEquals( new Integer( 1 ),
                  adapter.getPreserved( TableLCA.PROP_TOP_INDEX ) );
    hasListeners = ( Boolean )adapter.getPreserved( Props.SELECTION_LISTENERS );
    assertEquals( Boolean.TRUE, hasListeners );
    defaultColumnwidth
     = adapter.getPreserved( TableLCA.PROP_DEFAULT_COLUMN_WIDTH );
    defaultColumnWidth2 = TableLCA.getDefaultColumnWidth( table );
    assertEquals( new Integer( defaultColumnWidth2 ), defaultColumnwidth );
    itemMetrics
    = ( Object[] )adapter.getPreserved( TableLCAUtil.PROP_ITEM_METRICS );
    imageleft1 = ( TableLCAUtil.getItemMetrics( table )[ 0 ] ).imageLeft;
    assertEquals( imageleft1,
                  ( ( TableLCAUtil.ItemMetrics )itemMetrics[ 0 ] ).imageLeft );
    imagewidth1 = ( TableLCAUtil.getItemMetrics( table )[ 0 ] ).imageWidth;
    assertEquals( imagewidth1,
                  ( ( TableLCAUtil.ItemMetrics )itemMetrics[ 0 ] ).imageWidth );
    textleft1 = ( TableLCAUtil.getItemMetrics( table )[ 0 ] ).textLeft;
    assertEquals( textleft1,
                  ( ( TableLCAUtil.ItemMetrics )itemMetrics[ 0 ] ).textLeft );
    textwidth1 = ( TableLCAUtil.getItemMetrics( table )[ 0 ] ).textWidth;
    assertEquals( textwidth1,
                  ( ( TableLCAUtil.ItemMetrics )itemMetrics[ 0 ] ).textWidth );
    RWTFixture.clearPreserved();
    //control: enabled
    RWTFixture.preserveWidgets();
    adapter = WidgetUtil.getAdapter( table );
    assertEquals( Boolean.TRUE, adapter.getPreserved( Props.ENABLED ) );
    RWTFixture.clearPreserved();
    table.setEnabled( false );
    RWTFixture.preserveWidgets();
    adapter = WidgetUtil.getAdapter( table );
    assertEquals( Boolean.FALSE, adapter.getPreserved( Props.ENABLED ) );
    RWTFixture.clearPreserved();
    //visible
    RWTFixture.preserveWidgets();
    adapter = WidgetUtil.getAdapter( table );
    assertEquals( Boolean.TRUE, adapter.getPreserved( Props.VISIBLE ) );
    RWTFixture.clearPreserved();
    table.setVisible( false );
    RWTFixture.preserveWidgets();
    adapter = WidgetUtil.getAdapter( table );
    assertEquals( Boolean.FALSE, adapter.getPreserved( Props.VISIBLE ) );
    RWTFixture.clearPreserved();
    //menu
    RWTFixture.preserveWidgets();
    adapter = WidgetUtil.getAdapter( table );
    assertEquals( null, adapter.getPreserved( Props.MENU ) );
    RWTFixture.clearPreserved();
    Menu menu = new Menu( table );
    MenuItem item = new MenuItem( menu, SWT.NONE );
    item.setText( "1 Item" );
    table.setMenu( menu );
    RWTFixture.preserveWidgets();
    adapter = WidgetUtil.getAdapter( table );
    assertEquals( menu, adapter.getPreserved( Props.MENU ) );
    RWTFixture.clearPreserved();
    //bound
    Rectangle rectangle = new Rectangle( 10, 10, 30, 50 );
    table.setBounds( rectangle );
    RWTFixture.preserveWidgets();
    adapter = WidgetUtil.getAdapter( table );
    assertEquals( rectangle, adapter.getPreserved( Props.BOUNDS ) );
    RWTFixture.clearPreserved();
    //control_listeners
    RWTFixture.preserveWidgets();
    adapter = WidgetUtil.getAdapter( table );
    hasListeners = ( Boolean )adapter.getPreserved( Props.CONTROL_LISTENERS );
    assertEquals( Boolean.FALSE, hasListeners );
    RWTFixture.clearPreserved();
    table.addControlListener( new ControlListener() {

      public void controlMoved( final ControlEvent e ) {
      }

      public void controlResized( final ControlEvent e ) {
      }
    } );
    RWTFixture.preserveWidgets();
    adapter = WidgetUtil.getAdapter( table );
    hasListeners = ( Boolean )adapter.getPreserved( Props.CONTROL_LISTENERS );
    assertEquals( Boolean.TRUE, hasListeners );
    RWTFixture.clearPreserved();
    //z-index
    RWTFixture.preserveWidgets();
    adapter = WidgetUtil.getAdapter( table );
    assertTrue( adapter.getPreserved( Props.Z_INDEX ) != null );
    RWTFixture.clearPreserved();
    //foreground background font
    Color background = Graphics.getColor( 122, 33, 203 );
    table.setBackground( background );
    Color foreground = Graphics.getColor( 211, 178, 211 );
    table.setForeground( foreground );
    Font font = Graphics.getFont( "font", 12, SWT.BOLD );
    table.setFont( font );
    RWTFixture.preserveWidgets();
    adapter = WidgetUtil.getAdapter( table );
    assertEquals( background, adapter.getPreserved( Props.BACKGROUND ) );
    assertEquals( foreground, adapter.getPreserved( Props.FOREGROUND ) );
    assertEquals( font, adapter.getPreserved( Props.FONT ) );
    RWTFixture.clearPreserved();
    //tab_index
    RWTFixture.preserveWidgets();
    adapter = WidgetUtil.getAdapter( table );
    assertTrue( adapter.getPreserved( Props.Z_INDEX ) != null );
    RWTFixture.clearPreserved();
    //tooltiptext
    RWTFixture.preserveWidgets();
    adapter = WidgetUtil.getAdapter( table );
    assertEquals( null, table.getToolTipText() );
    RWTFixture.clearPreserved();
    table.setToolTipText( "some text" );
    RWTFixture.preserveWidgets();
    adapter = WidgetUtil.getAdapter( table );
    assertEquals( "some text", table.getToolTipText() );
    RWTFixture.clearPreserved();
    //activate_listeners   Focus_listeners
    RWTFixture.preserveWidgets();
    adapter = WidgetUtil.getAdapter( table );
    hasListeners = ( Boolean )adapter.getPreserved( Props.FOCUS_LISTENER );
    assertEquals( Boolean.FALSE, hasListeners );
    RWTFixture.clearPreserved();
    table.addFocusListener( new FocusListener() {

      public void focusGained( final FocusEvent event ) {
      }

      public void focusLost( final FocusEvent event ) {
      }
    } );
    RWTFixture.preserveWidgets();
    adapter = WidgetUtil.getAdapter( table );
    hasListeners = ( Boolean )adapter.getPreserved( Props.FOCUS_LISTENER );
    assertEquals( Boolean.TRUE, hasListeners );
    RWTFixture.clearPreserved();
    RWTFixture.preserveWidgets();
    adapter = WidgetUtil.getAdapter( table );
    hasListeners = ( Boolean )adapter.getPreserved( Props.ACTIVATE_LISTENER );
    assertEquals( Boolean.FALSE, hasListeners );
    RWTFixture.clearPreserved();
    ActivateEvent.addListener( table, new ActivateAdapter() {
    } );
    RWTFixture.preserveWidgets();
    adapter = WidgetUtil.getAdapter( table );
    hasListeners = ( Boolean )adapter.getPreserved( Props.ACTIVATE_LISTENER );
    assertEquals( Boolean.TRUE, hasListeners );
    RWTFixture.clearPreserved();
    display.dispose();
  }

  public void testSetDataEvent() {
    final StringBuffer log = new StringBuffer();
    Display display = new Display();
    Shell shell = new Shell( display );
    final Table table = new Table( shell, SWT.VIRTUAL );
    table.setItemCount( 10 );
    table.addListener( SWT.SetData, new Listener() {

      public void handleEvent( final Event event ) {
        assertSame( table.getItem( 1 ), event.item );
        assertEquals( 1, event.index );
        log.append( "SetDataEvent" );
      }
    } );
    String displayId = DisplayUtil.getId( display );
    String tableId = WidgetUtil.getId( table );
    RWTFixture.fakeNewRequest();
    Fixture.fakeRequestParam( RequestParams.UIROOT, displayId );
    Fixture.fakeRequestParam( JSConst.EVENT_SET_DATA, tableId );
    Fixture.fakeRequestParam( JSConst.EVENT_SET_DATA_INDEX, "1" );
    RWTFixture.executeLifeCycleFromServerThread( );


    assertEquals( 1, ItemHolder.getItems( table ).length );
    assertEquals( "SetDataEvent", log.toString() );
    String tableItemCtor = "org.eclipse.swt.widgets.TableItem";
    assertTrue( Fixture.getAllMarkup().indexOf( tableItemCtor ) != -1 );
  }

  public void testGetMeasureItemWithoutColumnsVirtual() {
    RWTFixture.fakePhase( PhaseId.PROCESS_ACTION );
    final String[] data = new String[ 1000 ];
    for( int i = 0; i < data.length; i++ ) {
      data[ i ] = "";
    }
    Listener setDataListener = new Listener() {

      public void handleEvent( final Event event ) {
        TableItem item = ( TableItem )event.item;
        int index = item.getParent().indexOf( item );
        item.setText( data[ index ] );
      }
    };
    Display display = new Display();
    Shell shell = new Shell( display );
    shell.setSize( 100, 100 );
    Table table = new Table( shell, SWT.VIRTUAL );
    table.addListener( SWT.SetData, setDataListener );
    table.setItemCount( data.length );
    table.setSize( 90, 90 );
    shell.open();
    int resolvedItemCount;
    TableItem measureItem;
    // Test with items that all have the same width
    resolvedItemCount = countResolvedItems( table );
    measureItem = TableLCAUtil.getMeasureItem( table );
    assertNotNull( measureItem );
    assertEquals( resolvedItemCount, countResolvedItems( table ) );
    // Test with items that have ascending length
    data[ 0 ] = "a";
    for( int i = 1; i < data.length; i++ ) {
      data[ i ] = data[ i - 1 ] + "a";
    }
    table.getItem( 100 ).getText(); // resolves item
    resolvedItemCount = countResolvedItems( table );
    measureItem = TableLCAUtil.getMeasureItem( table );
    int measureItemIndex = measureItem.getParent().indexOf( measureItem );
    assertEquals( 100, measureItemIndex );
    assertEquals( resolvedItemCount, countResolvedItems( table ) );
  }

  public void testRedraw() {
    final Table[] table = { null };
    Display display = new Display();
    final Shell shell = new Shell( display );
    shell.setSize( 100, 100 );
    Button button = new Button( shell, SWT.PUSH );
    button.addSelectionListener( new SelectionAdapter() {

      public void widgetSelected( final SelectionEvent event ) {
        table[ 0 ] = new Table( shell, SWT.VIRTUAL );
        table[ 0 ].setItemCount( 500 );
        table[ 0 ].setSize( 90, 90 );
        assertFalse( isItemVirtual( table[ 0 ], 0 ) );
        table[ 0 ].clearAll();
        table[ 0 ].redraw();
      }
    } );
    shell.open();
    String displayId = DisplayUtil.getId( display );
    Fixture.fakeResponseWriter();
    Fixture.fakeRequestParam( RequestParams.UIROOT, displayId );
    String buttonId = WidgetUtil.getId( button );
    Fixture.fakeRequestParam( JSConst.EVENT_WIDGET_SELECTED, buttonId  );
    RWTFixture.executeLifeCycleFromServerThread( );

    assertFalse( isItemVirtual( table[ 0 ], 0  ) );
  }

  public void testNoUnwantedResolveItems() {
    Display display = new Display();
    Shell shell = new Shell( display );
    shell.setSize( 100, 100 );
    final Table table = new Table( shell, SWT.VIRTUAL );
    table.setSize( 90, 90 );
    table.setItemCount( 1000 );
    shell.open();
    String displayId = DisplayUtil.getId( display );
    String tableId = WidgetUtil.getId( table );
    RWTFixture.fakeNewRequest();
    Fixture.fakeRequestParam( RequestParams.UIROOT, displayId );
    Fixture.fakeRequestParam( JSConst.EVENT_SET_DATA, tableId );
    Fixture.fakeRequestParam( JSConst.EVENT_SET_DATA_INDEX, "500,501,502,503" );
    Fixture.fakeRequestParam( tableId + ".topIndex", "500" );
    RWTLifeCycle lifeCycle = ( RWTLifeCycle )LifeCycleFactory.getLifeCycle();
    lifeCycle.addPhaseListener( new PhaseListener() {

      private static final long serialVersionUID = 1L;

      public void beforePhase( final PhaseEvent event ) {
        table.redraw();
      }

      public void afterPhase( final PhaseEvent event ) {
      }

      public PhaseId getPhaseId() {
        return PhaseId.PROCESS_ACTION;
      }
    } );
    RWTFixture.executeLifeCycleFromServerThread();

    assertTrue( isItemVirtual( table, 499 ) );
    assertTrue( isItemVirtual( table, 800 ) );
    assertTrue( isItemVirtual( table, 999 ) );
  }

  public void testClearVirtual() {
    RWTFixture.fakePhase( PhaseId.PROCESS_ACTION );
    RWTLifeCycle lifeCycle = ( RWTLifeCycle )LifeCycleFactory.getLifeCycle();
    lifeCycle.addPhaseListener( new PreserveWidgetsPhaseListener() );
    Display display = new Display();
    Shell shell = new Shell( display );
    shell.setSize( 100, 100 );
    shell.setLayout( new FillLayout() );
    final Table table = new Table( shell, SWT.VIRTUAL );
    table.setItemCount( 100 );
    shell.layout();
    shell.open();
    ITableAdapter adapter
      = ( ITableAdapter )table.getAdapter( ITableAdapter.class );
    // precondition: all items are resolved (TableItem#cached == true)
    // resolve all items and ensure
    for( int i = 0; i < table.getItemCount(); i++ ) {
      table.getItem( i ).getText();
    }
    assertFalse( adapter.isItemVirtual( table.getItemCount() - 1 ) );
    //
    String displayId = DisplayUtil.getId( display );
    final int lastItemIndex = table.getItemCount() - 1;
    String lastItemId = WidgetUtil.getId( table.getItem( lastItemIndex ) );
    // fake one request that would initialize the UI
    RWTFixture.fakeNewRequest();
    Fixture.fakeRequestParam( RequestParams.UIROOT, displayId );
    RWTFixture.executeLifeCycleFromServerThread();
    // run actual request
    RWTFixture.fakeNewRequest();
    Fixture.fakeRequestParam( RequestParams.UIROOT, displayId );
    lifeCycle.addPhaseListener( new PhaseListener() {
      private static final long serialVersionUID = 1L;
      public void beforePhase( final PhaseEvent event ) {
        table.clear( lastItemIndex );
      }
      public void afterPhase( final PhaseEvent event ) {
      }
      public PhaseId getPhaseId() {
        return PhaseId.PROCESS_ACTION;
      }
    } );
    RWTFixture.executeLifeCycleFromServerThread();
    String markup = Fixture.getAllMarkup();
    String expected
      = "var w = wm.findWidgetById( \""
      + lastItemId
      + "\" );w.clear()";
    assertTrue( markup.indexOf( expected ) != -1 );
  }

  private static int countResolvedItems( final Table table ) {
    Object adapter = table.getAdapter( ITableAdapter.class );
    ITableAdapter tableAdapter = ( ITableAdapter )adapter;
    int result = 0;
    TableItem[] createdItems = tableAdapter.getCreatedItems();
    for( int i = 0; i < createdItems.length; i++ ) {
      int index = table.indexOf( createdItems[ i ] );
      if( !tableAdapter.isItemVirtual( index ) ) {
        result++;
      }
    }
    return result;
  }

  private static boolean isItemVirtual( final Table table, final int index ) {
    Object adapter = table.getAdapter( ITableAdapter.class );
    ITableAdapter tableAdapter = ( ITableAdapter )adapter;
    return tableAdapter.isItemVirtual( index );
  }

  protected void setUp() throws Exception {
    RWTFixture.setUp();
  }

  protected void tearDown() throws Exception {
    RWTFixture.tearDown();
  }
}
