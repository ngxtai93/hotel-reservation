$(document).ready(function(){
	$('#location').change(function() {
		var location_id = $('#location').val();
		var url = window.location.href;
		$('#room-add-step-2').empty();
		
		if(location_id > 0) {
			$.ajax({
				url: (url + "?location=" + location_id),
				success: function(responseJson) {
					if(responseJson != "") {
						var $form = $("<form method=\"get\">").appendTo($('#room-add-step-2'));
						$.each(responseJson, function(index, hotel) {
							$form
								.append($("<input type=\"radio\" name=\"hotel\" value=\"" + hotel.seqNo + "\">"))
								.append($("<span>").text(hotel.name))
								.append($("<br>"))
							;
						});
					}
				}
			});
		}
	});
});