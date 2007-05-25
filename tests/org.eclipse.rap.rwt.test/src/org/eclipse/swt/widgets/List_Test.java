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

package org.eclipse.swt.widgets;

import java.util.Arrays;
import junit.framework.TestCase;
import org.eclipse.swt.RWTFixture;
import org.eclipse.swt.SWT;

public class List_Test extends TestCase {

  public void testGetItemsAndGetItemCount() {
    Display display = new Display();
    Composite shell = new Shell( display , SWT.NONE );
    List list = new List( shell, SWT.NONE );
    assertEquals( 0, list.getItemCount() );
    assertEquals( 0, list.getItems().length );
    // add(String)
    list.add( "test" );
    list.add( "test2" );
    assertEquals( 2, list.getItemCount() );
    assertEquals( 2, list.getItems().length );
    assertSame( "test", list.getItem( 0 ) );
    // add(String,int)
    list.add( "test3", 0 );
    assertEquals( 3, list.getItemCount() );
    assertSame( "test3", list.getItem( 0 ) );
    assertSame( "test2", list.getItem( 2 ) );
    // setItem
    list.setItem( 0, "test" );
    list.setItem( 1, "test2" );
    list.setItem( 2, "test3" );
    assertEquals( 3, list.getItemCount() );
    assertSame( "test", list.getItem( 0 ) );
    // getItems
    assertSame( "test", list.getItems()[ 0 ] );
    assertSame( "test2", list.getItems()[ 1 ] );
    assertSame( "test3", list.getItems()[ 2 ] );
    // remove(int)
    list.remove( 1 );
    assertSame( "test3", list.getItem( 1 ) );
    assertEquals( 2, list.getItemCount() );
    // setItems
    list.setItems( new String[] { "test", "test2", "test3" } );
    assertEquals( 3, list.getItemCount() );
    assertEquals( "test2", list.getItem( 1 ) );
    // remove(int,int)
    list.remove( 0, 1 );
    assertEquals( 1, list.getItemCount() );
    assertEquals( "test3", list.getItem( 0 ) );
    // remove(String)
    list.setItems( new String[]{ "test", "test2", "test3", "test" } );
    list.remove( "test" );
    assertEquals( 3, list.getItemCount() );
    assertEquals( "test2", list.getItem( 0 ) );
    // removeAll()
    list.removeAll();
    assertEquals( 0, list.getItemCount() );
  }
  
  public void testAdd() {
    Display display = new Display();
    Composite shell = new Shell( display , SWT.NONE );
    List list = new List( shell, SWT.NONE );
    
    // add item at end of list
    list.add( "test" );
    assertEquals( "test", list.getItem( 0 ) );
    
    // add duplicate items
    list.add( "test" );
    String[] expected = new String[] { "test", "test" };
    assertTrue( Arrays.equals( expected, list.getItems() ) );
    
    // ensure handling of invalid arguments
    int sizeBefore = list.getItemCount(); 
    try {
      list.add( null );
      fail( "add( null ) not allowed." );
    } catch( NullPointerException e ) {
      assertEquals( sizeBefore, list.getItemCount() );
    }
    
    // insert item at invalid position
    list.removeAll();
    list.add( "item1" );
    try {
      list.add( "bad", 50 );
      fail( "Inserting an item at an invalid position must not be allowed" );
    } catch( IllegalArgumentException e ) {
      // expected
    }
    try {
      list.add( "bad", -2 );
      fail( "Inserting an item at an invalid position must not be allowed" );
    } catch( IllegalArgumentException e ) {
      // expected
    }
    
    // insert item2 between item1 and item3
    list.removeAll();
    list.add( "item1" );
    list.add( "item3" );
    list.add( "item2", 1 );
    expected = new String[]{ "item1", "item2", "item3" };
    assertTrue( Arrays.equals( expected, list.getItems() ) );
    
    // insert item in empty list
    list.removeAll();
    list.add( "xyz", 0 );
    assertEquals( "xyz", list.getItem( 0 ) );
  }
  
  public void testRemove() {
    Display display = new Display();
    Composite shell = new Shell( display , SWT.NONE );
    List list = new List( shell, SWT.NONE );
    list.add( "item1" );

    // Test remove at specific position
    list.remove( 0 );
    assertEquals( 0, list.getItemCount() );
    list.add( "item1" );
    list.add( "item2" );
    list.remove( 0 );
    assertEquals( 1, list.getItemCount() );
    assertEquals( "item2", list.getItem( 0 ) );
    
    // Test remove with illegal arguments
    list.removeAll();
    try {
      list.remove( 0 );
      fail( "remove with invalid index must not be allowed" );
    } catch( IllegalArgumentException e ) {
      // expected
    }
    
    // Test remove range
    list.removeAll();
    list.add( "item0" );
    list.add( "item1" );
    list.add( "item2" );
    list.add( "item3" );
    list.remove( 0, 2 );
    assertEquals( 1, list.getItemCount() );
    assertEquals( "item3", list.getItem( 0 ) );
    
    // Test remove range where start > end
    list.removeAll();
    list.add( "item0" );
    list.add( "item1" );
    list.add( "item2" );
    list.add( "item3" );
    list.remove( 1, 0 );
    assertEquals( 4, list.getItemCount() );
    
    // Test remove(int[])
    list.removeAll();
    list.add( "item0" );
    list.add( "item1" );
    list.add( "item2" );
    list.add( "item3" );
    list.remove( new int[] { 0, 2 } );
    assertEquals( 2, list.getItemCount() );
    assertEquals( "item1", list.getItem( 0 ) );
    assertEquals( "item3", list.getItem( 1 ) );
    
    // Test remove(int[]) with an invalid index
    list.removeAll();
    list.add( "item0" );
    try {
      list.remove( new int[] { 0, 2 } );
      fail( "remove(int[]) with an invalid index must throw exception" );
    } catch( IllegalArgumentException e ) {
      assertEquals( 1, list.getItemCount() );
    }

    // Test remove(int[]) with an empty array argument
    list.removeAll();
    list.add( "item0" );
    list.remove( new int[ 0 ] );
    assertEquals( 1, list.getItemCount() );
    
    // Test remove(int[]) with null-argument
    list.removeAll();
    list.add( "item0" );
    try {
      list.remove( ( int[] )null );
      fail( "remove(int[]) with an null-argument must throw exception" );
    } catch( NullPointerException e ) {
      assertEquals( 1, list.getItemCount() );
    }
    
    // Test remove(string)
    list.removeAll();
    list.add( "item0" );
    list.remove( "item0" );
    assertEquals( 0, list.getItemCount() );
    
    // Test remove(string) with null-argument
    list.removeAll();
    list.add( "item0" );
    try {
      list.remove( ( String )null );
      fail( "remove(String) with an null-argument must throw exception" );
    } catch( NullPointerException e ) {
      assertEquals( 1, list.getItemCount() );
    }

    // Test remove(string) with non-existing string
    list.removeAll();
    list.add( "item0" );
    try {
      list.remove( "bad-item" );
      fail(   "remove(String) with an non-existing string as its argument "
            + "must throw exception" );
    } catch( IllegalArgumentException e ) {
      // expected
    }
    
    // Test removeAll
    list.add( "at least one item" );
    list.removeAll();
    assertEquals( 0, list.getItemCount() );
  }
  
  public void testSelectionForSingle() {
    Display display = new Display();
    Composite shell = new Shell( display , SWT.NONE );
    List list = new List( shell, SWT.SINGLE );
    
    // Test initials state of selection
    assertEquals( -1, list.getSelectionIndex() );
    assertEquals( 0, list.getSelectionCount() );
    assertEquals( 0, list.getSelection().length );
    
    // Test selecting single item
    list.add( "test1" );
    list.setSelection( 0 );
    assertEquals( 0, list.getSelectionIndex() );
    assertTrue( Arrays.equals( new int[] { 0 }, list.getSelectionIndices() ) );
    assertEquals( 1, list.getSelection().length );
    assertEquals( "test1", list.getSelection()[ 0 ] );
    
    // Test selecting single item with setSelection(int[])
    list.removeAll();
    list.add( "test1" );
    list.setSelection( new int[] { 0 } );
    assertEquals( 0, list.getSelectionIndex() );
    assertTrue( Arrays.equals( new int[] { 0 }, list.getSelectionIndices() ) );
    
    // Test selecting multiple items: selection will be reset (list is SINGLE)
    list.removeAll();
    list.add( "item0" );
    list.add( "item1" );
    list.add( "item2" );
    list.setSelection( 1 );
    list.setSelection( new int[] { 0, 2 } );
    assertEquals( 0, list.getSelectionCount() );
    assertEquals( 0, list.getSelection().length );
    
    // Test setSelection(int[]) with invaid arguments
    list.removeAll();
    list.add( "test" );
    list.setSelection( 0 );
    try {
      list.setSelection( ( int[] )null );
      fail( "setSelection(int[]) with null-argument is not allowed" );
    } catch( NullPointerException e ) {
      // expected
    }
    list.removeAll();
    list.add( "test" );
    list.setSelection( 0 );
    list.setSelection( new int[]{ 10 } );
    assertEquals( -1, list.getSelectionIndex() );
    list.removeAll();
    list.add( "test1" );
    list.add( "test1" );
    list.setSelection( 0 );
    list.setSelection( new int[]{ 0, 1 } );
    assertEquals( -1, list.getSelectionIndex() );
    
    // Ensure that adding an item does not change selection
    list.removeAll();
    list.add( "test1" );
    list.setSelection( 0 );
    list.add( "test2" );
    assertEquals( 0, list.getSelectionIndex() );
    assertTrue( Arrays.equals( new int[] { 0 }, list.getSelectionIndices() ) );

    // Ensure that inserting an item does not change selection
    list.removeAll();
    list.add( "test1" );
    list.setSelection( 0 );
    list.add( "test2" );
    list.setSelection( 0 );
    list.add( "test1.5", 1 );
    assertEquals( 0, list.getSelectionIndex() );
    assertTrue( Arrays.equals( new int[] { 0 }, list.getSelectionIndices() ) );
    
    // removing selected item must remove the selection also
    list.removeAll();
    list.add( "test" );
    list.setSelection( 0 );
    list.remove( 0 );
    assertEquals( -1, list.getSelectionIndex() );
    
    // Using setItems(String[]) must reset selection
    list.removeAll();
    list.add( "test" );
    list.setSelection( 0 );
    list.setItems( new String[] { "a", "b" } );
    assertEquals( -1, list.getSelectionIndex() );
    
    // Test setSelection(int,int)
    list.removeAll();
    list.add( "test1" );
    list.add( "test2" );
    list.add( "test3" );
    list.setSelection( 0, 0 );
    assertEquals( 0, list.getSelectionIndex() );
    // setSelection(int,int) must specify only one item in the range
    list.setSelection( 0, 1 );
    assertEquals( -1, list.getSelectionIndex() );
    // Test setSelection(int,int) with invalid ranges: must not select anything
    list.setSelection( 0 );
    list.setSelection( 7, 1 );
    assertEquals( -1, list.getSelectionIndex() );
    list.setSelection( 0 );
    list.setSelection( 7, 1 );
    assertEquals( -1, list.getSelectionIndex() );
    list.setSelection( 0 );
    list.setSelection( -2, -2 );
    assertEquals( -1, list.getSelectionIndex() );
    list.setSelection( 0 );
    list.setSelection( -1, 2 );
    assertEquals( -1, list.getSelectionIndex() );
    
    // Test setSelection(String[])
    list.removeAll();
    list.add( "test1" );
    list.add( "test2" );
    list.add( "test3" );
    list.setSelection( -1 );
    list.setSelection( new String[] { "test1" } );
    assertEquals( 0, list.getSelectionIndex() );

    // Test setSelection(String[]) with empty array: deselect all
    list.setSelection( 0 );
    list.setSelection( new String[ 0 ] );
    assertEquals( -1, list.getSelectionIndex() );

    // Test setSelection(String[]) with more than one String: deselect all
    list.setSelection( 0 );
    list.setSelection( new String[] { "test1", "test2" } );
    assertEquals( -1, list.getSelectionIndex() );
    list.setSelection( 0 );
    list.setSelection( new String[] { null, "test2" } );
    assertEquals( -1, list.getSelectionIndex() );

    // Test setSelection(String[]) with one non-existing String: deselect all
    list.setSelection( 0 );
    list.setSelection( new String[] { null } );
    assertEquals( -1, list.getSelectionIndex() );
    list.setSelection( 0 );
    list.setSelection( new String[] { "not here" } );
    assertEquals( -1, list.getSelectionIndex() );
    
    // Test setSelection(String[]) with invalid arguments
    try {
      list.setSelection( ( String[] )null );
      fail( "setSelection(String[]) must not allow null-argument" );
    } catch( NullPointerException e ) {
      // expected
    }
    
    // Select last item and remove it
    list.removeAll();
    list.add( "item0" );
    list.add( "last item" );
    list.setSelection( list.getItemCount() - 1 );
    list.remove( list.getItemCount() - 1 );
    assertEquals( 1, list.getItemCount() );
    assertEquals( -1, list.getSelectionIndex() );

    // Select non-existing item
    list.removeAll();
    list.add( "item0" );
    list.add( "item1" );
    list.add( "item2" );
    list.setSelection( 1 );
    list.setSelection( 12345 );
    assertEquals( -1, list.getSelectionIndex() );
    
    // selectAll must be ignored for single-select Lists
    list.removeAll();
    list.add( "item0" );
    list.add( "item1" );
    list.add( "item2" );
    list.setSelection( 1 );
    list.selectAll();
    assertEquals( 1, list.getSelectionIndex() );
    assertEquals( 1, list.getSelectionCount() );
    
    // select must replace a current selection if called with valid argument
    list.removeAll();
    list.add( "item0" );
    list.add( "item1" );
    list.add( "item2" );
    list.select( 1 );
    assertEquals( 1, list.getSelectionIndex() );
    list.select( -3 );
    assertEquals( 1, list.getSelectionIndex() );
    list.select( list.getItemCount() + 10 );
    assertEquals( 1, list.getSelectionIndex() );
    
    // test select( int[] )
    list.removeAll();
    list.add( "item0" );
    list.add( "item1" );
    list.add( "item2" );
    // proper usage
    list.select( new int[] { 0 } );
    assertEquals( 0, list.getSelectionIndices()[ 0 ] );
    // calling again must not change anything
    list.select( new int[] { 0 } ); 
    assertEquals( 0, list.getSelectionIndices()[ 0 ] );
    // duplicate entries in argumenet are ignored
    list.select( new int[] { 0, 0, 0 } );
    assertEquals( 0, list.getSelectionIndices()[ 0 ] );
    // calls with more than one entries are ignored
    list.select( new int[] { 1, 2 } );
    assertEquals( 0, list.getSelectionIndices()[ 0 ] );
    // calls with empty array are ignored
    list.select( new int[] { } );
    assertEquals( 0, list.getSelectionIndices()[ 0 ] );
    // invalid argument
    try {
      list.select( null );
      fail( "Null argument not allowed" );
    } catch( NullPointerException e ) {
      // expected
    }

    // test select( int, int )
    list.removeAll();
    list.add( "item0" );
    list.add( "item1" );
    list.add( "item2" );
    // proper usage
    list.select( 0, 0 );
    assertEquals( 1, list.getSelectionCount() );
    assertEquals( 0, list.getSelectionIndices()[ 0 ] );
    // invalid usage
    list.select( 1, 2 );
    assertEquals( 0, list.getSelectionIndices()[ 0 ] );
    list.select( 11, 11 );
    assertEquals( 0, list.getSelectionIndices()[ 0 ] );
    list.select( -3, -1 );
    assertEquals( 0, list.getSelectionIndices()[ 0 ] );
    // proper usage
    list.select( 1, 1 );
    assertEquals( 1, list.getSelectionCount() );
    assertEquals( 1, list.getSelectionIndices()[ 0 ] );
  }
  
  public void testSelectionForMulti() {
    Display display = new Display();
    Composite shell = new Shell( display , SWT.NONE );
    List list = new List( shell, SWT.MULTI );
    
    // Test initials state of selection
    assertEquals( -1, list.getSelectionIndex() );
    assertEquals( 0, list.getSelectionCount() );
    assertEquals( 0, list.getSelection().length );
    
    // Test selecting single item
    list.add( "test1" );
    list.setSelection( 0 );
    assertEquals( 0, list.getSelectionIndex() );
    assertTrue( Arrays.equals( new int[] { 0 }, list.getSelectionIndices() ) );
    assertEquals( "test1", list.getSelection()[ 0 ] );
    
    // Test selecting single item with setSelection(int[])
    list.removeAll();
    list.add( "test1" );
    list.setSelection( new int[] { 0 } );
    assertEquals( 0, list.getSelectionIndex() );
    assertTrue( Arrays.equals( new int[] { 0 }, list.getSelectionIndices() ) );
    
    // Test selecting multiple items
    list.removeAll();
    list.add( "item0" );
    list.add( "item1" );
    list.add( "item2" );
    list.setSelection( 1 );
    int[] selection = new int[] { 0, 2 };
    list.setSelection( selection );
    assertEquals( 2, list.getSelectionCount() );
    assertTrue( Arrays.equals( selection, list.getSelectionIndices() ) );
    assertEquals( "item0", list.getSelection()[ 0 ] );
    assertEquals( "item2", list.getSelection()[ 1 ] );
    
    // Test setSelection(int[]) with (partially) invaid arguments
    list.removeAll();
    list.add( "test" );
    list.setSelection( 0 );
    try {
      list.setSelection( ( int[] )null );
      fail( "setSelection(int[]) with null-argument is not allowed" );
    } catch( NullPointerException e ) {
      // expected
    }
    list.removeAll();
    list.add( "test" );
    list.setSelection( 0 );
    list.setSelection( new int[]{ 10, 12 } );
    assertEquals( -1, list.getSelectionIndex() );
    assertEquals( 0, list.getSelection().length );
    list.removeAll();
    list.add( "test1" );
    list.add( "test2" );
    list.setSelection( 0 );
    list.setSelection( new int[]{ 1, 8 } );
    assertEquals( 1, list.getSelectionIndex() );
    assertEquals( "test2", list.getSelection()[ 0 ] );
    
    // Ensure that adding an item does not change selection
    list.removeAll();
    list.add( "test1" );
    list.setSelection( 0 );
    list.add( "test2" );
    assertEquals( 0, list.getSelectionIndex() );
    assertTrue( Arrays.equals( new int[] { 0 }, list.getSelectionIndices() ) );

    // Ensure that inserting an item does not change selection
    list.removeAll();
    list.add( "test1" );
    list.setSelection( 0 );
    list.add( "test2" );
    list.setSelection( 0 );
    list.add( "test1.5", 1 );
    assertEquals( 0, list.getSelectionIndex() );
    assertTrue( Arrays.equals( new int[] { 0 }, list.getSelectionIndices() ) );
    
    // removing selected item must remove the selection also
    list.removeAll();
    list.add( "test" );
    list.setSelection( 0 );
    list.remove( 0 );
    assertEquals( -1, list.getSelectionIndex() );
    
    // Using setItems(String[]) must reset selection
    list.removeAll();
    list.add( "test" );
    list.setSelection( 0 );
    list.setItems( new String[] { "a", "b" } );
    assertEquals( -1, list.getSelectionIndex() );
    
    // Test setSelection(int,int)
    list.removeAll();
    list.add( "test1" );
    list.add( "test2" );
    list.add( "test3" );
    list.setSelection( 0, 0 );
    assertEquals( 0, list.getSelectionIndex() );
    // setSelection(int,int) must specify only one item in the range
    list.setSelection( 0, 1 );
    assertEquals( 0, list.getSelectionIndex() );
    assertEquals( 2, list.getSelectionCount() );
    assertTrue( Arrays.equals( new int[] { 0, 1 }, 
                               list.getSelectionIndices() ) );
    // Test setSelection(int,int) with invalid ranges: must not select anything
    list.setSelection( 0 );
    list.setSelection( 7, 1 );
    assertEquals( -1, list.getSelectionIndex() );
    list.setSelection( 0 );
    list.setSelection( 7, 1 );
    assertEquals( -1, list.getSelectionIndex() );
    list.setSelection( 0 );
    list.setSelection( -2, -2 );
    assertEquals( -1, list.getSelectionIndex() );
    list.setSelection( 0 );
    list.setSelection( -1, 2 );
    assertEquals( 3, list.getSelectionCount() );
    assertEquals( 3, list.getSelectionCount() );
    
    // Test setSelection(String[])
    list.removeAll();
    list.add( "test1" );
    list.add( "test2" );
    list.add( "test3" );
    list.setSelection( -1 );
    list.setSelection( new String[] { "test1" } );
    assertEquals( 0, list.getSelectionIndex() );

    // Test setSelection(String[]) 
    list.setSelection( 0 );
    list.setSelection( new String[] { "test1", "test2" } );
    assertEquals( 0, list.getSelectionIndex() );
    assertEquals( 2, list.getSelectionCount() );
    assertTrue( Arrays.equals( new int[] { 0, 1 }, 
                               list.getSelectionIndices() ) );
    list.setSelection( 0 );
    list.setSelection( new String[] { null, "test2" } );
    assertEquals( 1, list.getSelectionIndex() );

    // Test setSelection(String[]) with empty array: deselect all
    list.setSelection( 0 );
    list.setSelection( new String[ 0 ] );
    assertEquals( -1, list.getSelectionIndex() );
    
    // Test setSelection(String[]) with one non-existing String: deselect all
    list.setSelection( 0 );
    list.setSelection( new String[] { null } );
    assertEquals( -1, list.getSelectionIndex() );
    list.setSelection( 0 );
    list.setSelection( new String[] { "not here" } );
    assertEquals( -1, list.getSelectionIndex() );
    
    // Test setSelection(String[]) with invalid arguments
    try {
      list.setSelection( ( String[] )null );
      fail( "setSelection(String[]) must not allow null-argument" );
    } catch( NullPointerException e ) {
      // expected
    }

    // Select non-existing item
    list.removeAll();
    list.add( "item0" );
    list.add( "item1" );
    list.add( "item2" );
    list.setSelection( 1 );
    list.setSelection( 12345 );
    assertEquals( -1, list.getSelectionIndex() );

    // selectAll must be ignored for single-select Lists
    list.removeAll();
    list.add( "item0" );
    list.add( "item1" );
    list.add( "item2" );
    list.selectAll();
    assertEquals( 3, list.getSelectionCount() );
    assertEquals( 0, list.getSelectionIndices()[ 0 ] );
    assertEquals( 1, list.getSelectionIndices()[ 1 ] );
    assertEquals( 2, list.getSelectionIndices()[ 2 ] );

    // select must add to the current selection if called with valid argument
    list.removeAll();
    list.add( "item0" );
    list.add( "item1" );
    list.add( "item2" );
    list.select( 0 );
    assertEquals( 0, list.getSelectionIndices()[ 0 ] );
    list.select( 1 );
    assertEquals( 0, list.getSelectionIndices()[ 0 ] );
    assertEquals( 1, list.getSelectionIndices()[ 1 ] );
    list.select( -1 );
    assertEquals( 0, list.getSelectionIndices()[ 0 ] );
    assertEquals( 1, list.getSelectionIndices()[ 1 ] );
    list.select( list.getItemCount() + 10 );
    assertEquals( 0, list.getSelectionIndices()[ 0 ] );
    assertEquals( 1, list.getSelectionIndices()[ 1 ] );
    
    // test select( int[] )
    list.removeAll();
    list.add( "item0" );
    list.add( "item1" );
    list.add( "item2" );
    // proper usage
    list.select( new int[] { 0 } );
    assertEquals( 0, list.getSelectionIndices()[ 0 ] );
    // calling again must not change anything
    list.select( new int[] { 0 } ); 
    assertEquals( 0, list.getSelectionIndices()[ 0 ] );
    // duplicate entries in argumenet are ignored
    list.select( new int[] { 0, 0, 0 } );
    assertEquals( 0, list.getSelectionIndices()[ 0 ] );
    // propert usage with more than one entries
    list.select( new int[] { 1, 2 } );
    assertEquals( 0, list.getSelectionIndices()[ 0 ] );
    assertEquals( 1, list.getSelectionIndices()[ 1 ] );
    assertEquals( 2, list.getSelectionIndices()[ 2 ] );
    // calls with empty array are ignored
    list.select( new int[] { } );
    assertEquals( 0, list.getSelectionIndices()[ 0 ] );
    // invalid argument
    try {
      list.select( null );
      fail( "Null argument not allowed" );
    } catch( NullPointerException e ) {
      // expected
    }

    // test select( int, int )
    list.removeAll();
    list.add( "item0" );
    list.add( "item1" );
    list.add( "item2" );
    // proper usage
    list.select( 0, 0 );
    assertEquals( 1, list.getSelectionCount() );
    assertEquals( 0, list.getSelectionIndices()[ 0 ] );
    list.select( 1, 2 );
    assertEquals( 3, list.getSelectionCount() );
    assertEquals( 0, list.getSelectionIndices()[ 0 ] );
    assertEquals( 1, list.getSelectionIndices()[ 1 ] );
    assertEquals( 2, list.getSelectionIndices()[ 2 ] );
    // invalid usage
    list.select( 11, 11 );
    assertEquals( 3, list.getSelectionCount() );
    assertEquals( 0, list.getSelectionIndices()[ 0 ] );
    assertEquals( 1, list.getSelectionIndices()[ 1 ] );
    assertEquals( 2, list.getSelectionIndices()[ 2 ] );
    list.select( -3, -1 );
    assertEquals( 3, list.getSelectionCount() );
    assertEquals( 0, list.getSelectionIndices()[ 0 ] );
    assertEquals( 1, list.getSelectionIndices()[ 1 ] );
    assertEquals( 2, list.getSelectionIndices()[ 2 ] );
    // select an already selected index
    list.select( 1, 1 );
    assertEquals( 3, list.getSelectionCount() );
    assertEquals( 0, list.getSelectionIndices()[ 0 ] );
    assertEquals( 1, list.getSelectionIndices()[ 1 ] );
    assertEquals( 2, list.getSelectionIndices()[ 2 ] );
  }
  
  public void testSetItem() {
    Display display = new Display();
    Composite shell = new Shell( display , SWT.NONE );
    List list = new List( shell, SWT.NONE );
    
    // Test setItem
    list.add( "itemX" );
    list.add( "item1" );
    list.setItem( 0, "item0" );
    assertEquals( "item0", list.getItem( 0 ) );
    
    // Test setItem with invalid index
    list.removeAll();
    try {
      list.setItem( -4, "won't make it" );
      fail( "Must check valid range of index" );
    } catch( IllegalArgumentException e ) {
      // expected
    }
    
    // Test setItem with null argument
    list.removeAll();
    list.add( "abc" );
    try {
      list.setItem( 0, null );
      fail( "Must check valid range of index" );
    } catch( NullPointerException e ) {
      // expected
    }
  }
  
  public void testSetItems() {
    Display display = new Display();
    Composite shell = new Shell( display , SWT.NONE );
    List list = new List( shell, SWT.NONE );
    
    // Test setItems on empty list
    String[] itemsToSet = new String[] { "a", "b", "c" };
    list.setItems( itemsToSet );
    assertTrue( Arrays.equals( itemsToSet, list.getItems() ) );

    // Test setItems on empty list
    String[] otherItemsToSet = new String[] { "a", "b", "c", "d" };
    list.setItems( otherItemsToSet );
    assertTrue( Arrays.equals( otherItemsToSet, list.getItems() ) );
    
    // Test setItems with null-argument
    list.removeAll();
    list.add( "a" );
    try {
      list.setItems( null );
      fail( "setItems(null) is not allowed" );
    } catch( NullPointerException e ) {
      assertEquals( 1, list.getItemCount() );
    }

    // Test setItems with an arrays that contains a null-string
    list.removeAll();
    list.add( "a" );
    try {
      list.setItems( new String[] { "a", null, "b" } );
      fail( "setItems(String[]) with a null-string is not allowed" );
    } catch( IllegalArgumentException e ) {
      assertEquals( 1, list.getItemCount() );
    }
  }
  
  public void testGetItem() {
    Display display = new Display();
    Composite shell = new Shell( display , SWT.NONE );
    List list = new List( shell, SWT.NONE );

    // Test getItem for existing item
    list.add( "item0" );
    assertEquals( "item0", list.getItem( 0 ) );
    
    // Test getItem for non-existing item
    try {
      list.getItem( -2 );
      fail( "Must not allow getItem with invalid index" );
    } catch( IllegalArgumentException e ) {
      // expected
    }
    try {
      list.getItem( 22 );
      fail( "Must not allow getItem with invalid index" );
    } catch( IllegalArgumentException e ) {
      // expected
    }
  }
  
  public void testGetItems() {
    Display display = new Display();
    Composite shell = new Shell( display , SWT.NONE );
    List list = new List( shell, SWT.NONE );
    assertEquals( 0, list.getItems().length );
    list.add( "item1" );
    assertTrue( Arrays.equals( new String[] { "item1" }, list.getItems() ) );
  }
  
  public void testStyle() {
    Display display = new Display();
    Composite shell = new Shell( display , SWT.NONE );
    List list1 = new List( shell, SWT.NONE );
    assertTrue( ( list1.getStyle() & SWT.SINGLE ) != 0 );

    List list2 = new List( shell, SWT.SINGLE );
    assertTrue( ( list2.getStyle() & SWT.SINGLE ) != 0 );

    List list3 = new List( shell, SWT.MULTI );
    assertTrue( ( list3.getStyle() & SWT.MULTI ) != 0 );

    List list4 = new List( shell, SWT.SINGLE | SWT.MULTI );
    assertTrue( ( list4.getStyle() & SWT.SINGLE ) != 0 );
  }
  
  public void testFocusIndexForSingle() {
    Display display = new Display();
    Composite shell = new Shell( display , SWT.NONE );
    List list = new List( shell, SWT.SINGLE );
    
    // Initial focusIndex must be -1
    assertEquals( -1, list.getFocusIndex() );
    
    // focusIndex must be 0 after adding the first item
    list.removeAll();
    list.add( "item0" );
    assertEquals( 0, list.getFocusIndex() );
    
    // focusIndex must be -1 after removeing last item
    list.removeAll();
    list.add( "item0" );
    list.remove( 0 );
    assertEquals( -1, list.getFocusIndex() );
    
    // Test that focusIndex goes with the selection, if any
    list.removeAll();
    list.add( "item0" );
    list.add( "item1" );
    list.add( "item2" );
    list.setSelection( 0 );
    assertEquals( 0, list.getFocusIndex() );
    list.remove( 1 );
    assertEquals( 0, list.getFocusIndex() );
    list.setSelection( 1 );
    assertEquals( 1, list.getFocusIndex() );

    // Setting a selection range must set the focusIndex to the selection start
    list.removeAll();
    list.add( "item0" );
    list.add( "item1" );
    list.add( "item2" );
    list.setSelection( 1, 1 );
    assertEquals( 1, list.getFocusIndex() );
    
    // Removing the last but not only item (when selected and focused)
    list.removeAll();
    list.add( "item0" );
    list.add( "item1" );
    list.add( "item2" );
    list.setSelection( 2 );
    list.remove( 2 );
    assertEquals( 1, list.getFocusIndex() );
    assertEquals( -1, list.getSelectionIndex() );
    
    // Don't move focusIndex when selection a non-existing item
    list.removeAll();
    list.add( "item0" );
    list.add( "item1" );
    list.add( "item2" );
    list.setSelection( 0 );
    list.setSelection( 1234 );
    assertEquals( 0, list.getFocusIndex() );
  }
  
  public void testFocusIndexForMulti() {
    Display display = new Display();
    Composite shell = new Shell( display , SWT.NONE );
    List list = new List( shell, SWT.MULTI );
    
    // Initial focusIndex must be -1
    assertEquals( -1, list.getFocusIndex() );
    
    // focusIndex must be 0 after adding the first item
    list.removeAll();
    list.add( "item0" );
    assertEquals( 0, list.getFocusIndex() );
    
    // focusIndex must be -1 after removeing last item
    list.removeAll();
    list.add( "item0" );
    list.remove( 0 );
    assertEquals( -1, list.getFocusIndex() );
    
    // Test that focusIndex goes with the selection, if any
    list.removeAll();
    list.add( "item0" );
    list.add( "item1" );
    list.add( "item2" );
    list.setSelection( 0 );
    assertEquals( 0, list.getFocusIndex() );
    list.remove( 1 );
    assertEquals( 0, list.getFocusIndex() );
    list.setSelection( 1 );
    assertEquals( 1, list.getFocusIndex() );
    list.setSelection( -1 );
    list.setSelection( 1, 2 );
    assertEquals( 1, list.getFocusIndex() );
    
    // Setting a selection range must set the focusIndex to the selection start
    list.removeAll();
    list.add( "item0" );
    list.add( "item1" );
    list.add( "item2" );
    list.setSelection( 1, 2 );
    assertEquals( 1, list.getFocusIndex() );
  }
  
  public void testDispose() {
    Display display = new Display();
    Composite shell = new Shell( display , SWT.NONE );
    List list = new List( shell, SWT.NONE );
    list.add( "test" );
    list.dispose();
    assertTrue( list.isDisposed() );
  }

  protected void setUp() throws Exception {
    RWTFixture.setUp();
  }

  protected void tearDown() throws Exception {
    RWTFixture.tearDown();
  }
}
