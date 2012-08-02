/*******************************************************************************
 *  Copyright: 2004, 2012 1&1 Internet AG, Germany, http://www.1und1.de,
 *                        and EclipseSource
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this
 * distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors:
 *    1&1 Internet AG and others - original API and implementation
 *    EclipseSource - adaptation for the Eclipse Rich Ajax Platform
 ******************************************************************************/

qx.Class.define("org.eclipse.rwt.widgets.ListItem", {

  extend : org.eclipse.rwt.widgets.MultiCellWidget,

  construct : function() {
    this.base( arguments, [ "label" ] );
    this.initMinWidth();
    this.setHorizontalChildrenAlign( "left" );
    if( org.eclipse.rwt.Client.isMobileSafari() ) {
      this.setStyleProperty( "webkitTransform", "translateZ(0)" ); // prevent rendering glitch with touch-scrolling
    }
  },

  properties : {

    appearance : {
      refine : true,
      init : "list-item"
    },

    minWidth : {
      refine : true,
      init : "0px"
    },

    width : {
      refine : true,
      init : null
    },

    allowStretchX : {
      refine : true,
      init : true
    }

  },

  members : {

    setLabel : function( value ) {
      this.setCellContent( 0, value );
    },

    getLabel : function( value ) {
      return this.getCellContent( 0 );
    },
    
    // NOTE: there are issues with list and markup based on the items width/height
    // MultiCellWidget might not be ideal since it tries to measure and layout its content
    
    getCellWidth : function( cell, ignoreFlexible ) {
      var result;
      if( cell === 0 && !ignoreFlexible ) {// and markup enable
        result = this.getInnerWidth(); // for markup to be able to work with relative widths/right
      } else {
        result = this.base( arguments, cell, ignoreFlexible );
      }
      return result;
    },
    
    getCellHeight : function( cell, ignoreFlexible ) {
      var result;
      if( cell === 0 && !ignoreFlexible ) { // and markup enabled
        result = this.getInnerHeight(); // for markup to be able to work with relative heights/bottom
      } else {
        result = this.base( arguments, cell, ignoreFlexible );
      }
      return result;
    },
    
    cellIsDisplayable : function() {
      return true;
    },

    matchesString : function( value ) {
      var content;
      var el = this.getCellNode( 0 );
      if( el ) {
        content = el.innerText || el.textContent;
      } else {
        content = this.getLabel();
      }
      var input = ( typeof value === "string" ) ? value.toLowerCase() : "";
      content = ( typeof content === "string" ) ? content.toLowerCase() : "";
      return input !== "" && content.indexOf( input ) === 0;
    }
  
  }
} );
