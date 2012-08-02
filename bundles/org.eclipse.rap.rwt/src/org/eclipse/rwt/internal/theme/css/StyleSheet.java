/*******************************************************************************
 * Copyright (c) 2008, 2011 Innoopract Informationssysteme GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Innoopract Informationssysteme GmbH - initial API and implementation
 *    EclipseSource - ongoing development
 ******************************************************************************/
package org.eclipse.rwt.internal.theme.css;

import java.util.*;

import org.eclipse.rwt.internal.theme.QxType;
import org.w3c.css.sac.Selector;
import org.w3c.css.sac.SelectorList;


/**
 * Instances of this class represent a parsed CSS stylesheet.
 */
public final class StyleSheet {
  private static final SelectorWrapperComparator COMPARATOR = new SelectorWrapperComparator();

  private final StyleRule[] styleRules;
  private SelectorWrapper[] selectorWrappers;

  public StyleSheet( StyleRule[] styleRules ) {
    this.styleRules = styleRules.clone();
    createSelectorWrappers();
  }

  public StyleRule[] getStyleRules() {
    return styleRules.clone();
  }

  public ConditionalValue[] getValues( String elementName, String propertyName ) {
    TreeMap<Integer, List<ConditionalValue>> bufferMap = new TreeMap<Integer, List<ConditionalValue>>();
    List<ConditionalValue> bufferList = new ArrayList<ConditionalValue>();
    for( SelectorWrapper selectorWrapper : selectorWrappers ) {
      String selectorElement = ( ( SelectorExt )selectorWrapper.selector ).getElementName();
      if( selectorElement == null || selectorElement.equals( elementName ) ) {
        QxType value = selectorWrapper.propertyMap.getValue( propertyName );
        if( value != null ) {
          String[] constraints = ( ( SelectorExt )selectorWrapper.selector ).getConstraints();
          int weight = 0;
          for( String string : constraints ) {
            if( string.startsWith( "." ) ) {
              weight -= 2;
            }
            else {
              weight -= 1;
            }
          }
          if( !containsConstraintsAlready( bufferList, constraints ) ) {
            ConditionalValue conditionalValue = new ConditionalValue( constraints, value );
            bufferList.add( conditionalValue );
            List<ConditionalValue> buffer;
            if( bufferMap.containsKey( Integer.valueOf( weight ) ) ) {
              buffer = bufferMap.get( Integer.valueOf( weight ) );
            }
            else {
              buffer = new ArrayList<ConditionalValue>(constraints.length);
              bufferMap.put( Integer.valueOf( weight ), buffer );
            }
            buffer.add( conditionalValue );
          }
        }
      }
    }
    List<ConditionalValue> buffer = new ArrayList<ConditionalValue>();
    Iterator<List<ConditionalValue>> iterator = bufferMap.values().iterator();
    while( iterator.hasNext() ) {
      List<ConditionalValue> condValues = iterator.next();
      buffer.addAll( condValues );
    }
    return buffer.toArray( new ConditionalValue[ buffer.size() ] );
  }

  public String toString() {
    StringBuilder buffer = new StringBuilder();
    StyleRule[] styleRules = getStyleRules();
    for( int i = 0; i < styleRules.length; i++ ) {
      StyleRule styleRule = styleRules[ i ];
      SelectorList selectors = styleRule.getSelectors();
      int length = selectors.getLength();
      for( int j = 0; j < length; j++ ) {
        if( j > 0 ) {
          buffer.append( "," );
        }
        if( i > 0 ) {
          buffer.append( "\n" );
        }
        buffer.append( selectors.item( j ) );
      }
      buffer.append( "\n" );
      buffer.append( styleRule.getProperties() );
      buffer.append( "\n" );
    }
    return buffer.toString();
  }

  private static boolean containsConstraintsAlready( List<ConditionalValue> conditionalValuesList,
                                                     String[] constraints )
  {
    Iterator<ConditionalValue> iterator = conditionalValuesList.iterator();
    boolean result = false;
    while( iterator.hasNext() && !result ) {
      ConditionalValue condValue = iterator.next();
      if( Arrays.equals( condValue.constraints, constraints ) ) {
        result = true;
      }
    }
    return result;
  }

  private void createSelectorWrappers() {
    List<SelectorWrapper> selectorWrappersList = new LinkedList<SelectorWrapper>();
    for( int pos = 0; pos < styleRules.length; pos++ ) {
      StyleRule styleRule = styleRules[ pos ];
      SelectorList selectors = styleRule.getSelectors();
      IStylePropertyMap properties = styleRule.getProperties();
      int length = selectors.getLength();
      for( int i = 0; i < length; i++ ) {
        Selector selector = selectors.item( i );
        SelectorWrapper selectorWrapper = new SelectorWrapper( selector, properties, pos );
        selectorWrappersList.add( selectorWrapper );
      }
    }
    Collections.sort( selectorWrappersList, COMPARATOR );
    Collections.reverse( selectorWrappersList );
    selectorWrappers = new SelectorWrapper[ selectorWrappersList.size() ];
    selectorWrappersList.toArray( selectorWrappers );
  }

  static class SelectorWrapper {

    public final Selector selector;
    public final IStylePropertyMap propertyMap;
    public final int position;

    public SelectorWrapper( Selector selector, IStylePropertyMap propertyMap, int position ) {
      this.selector = selector;
      this.propertyMap = propertyMap;
      this.position = position;
    }
  }

  private static class SelectorWrapperComparator implements Comparator<SelectorWrapper> {

    public int compare( SelectorWrapper selectorWrapper1, SelectorWrapper selectorWrapper2 ) {
      int result = 0;
      int specificity1 = ( ( Specific )selectorWrapper1.selector ).getSpecificity();
      int specificity2 = ( ( Specific )selectorWrapper2.selector ).getSpecificity();
      if( specificity1 > specificity2 ) {
        result = 1;
      } else if( specificity1 < specificity2 ) {
        result = -1;
      } else if( selectorWrapper1.position > selectorWrapper2.position ) {
        result = 1;
      } else if( selectorWrapper1.position < selectorWrapper2.position ) {
        result = -1;
      }
      return result;
    }
  }
}
