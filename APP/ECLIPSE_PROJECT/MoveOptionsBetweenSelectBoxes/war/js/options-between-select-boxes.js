var removeSelection = function() {
	$( 'div.option_selected' ).removeClass( 'option_selected' ).addClass( 'option_unselected' );
};

$( document ).ready( function() {
	$( 'div.select_option' ).click( function(){
		var $this = $( this );
		if ( $this.hasClass( 'option_selected' ) ) {
			$this.removeClass( 'option_selected' ).addClass( 'option_unselected' );
		} else {
			$this.removeClass( 'option_unselected' ).addClass( 'option_selected' );
		}
	});
	
	$( '#btn_exchange_from_to' ).click( function(){
		$( '#select_from div div.option_selected' ).each( function(){
			var $this = $( this );
			if ( $( '#select_to div div' ).length ) {
				$( '#select_to div div:last' ).after( $this );
			} else {
				$( '#select_to div' ).append( $this );
			}
		});
		
		removeSelection();
	});
	
	$( '#btn_exchange_to_from' ).click( function(){
		$( '#select_to div div.option_selected' ).each( function(){
			var $this = $( this );
			if ( $( '#select_from div div' ).length ) {
				$( '#select_from div div:last' ).after( $this );
			} else {
				$( '#select_from div' ).append( $this );
			}
		});
		
		removeSelection();
	});
});