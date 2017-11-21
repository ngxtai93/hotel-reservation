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
						$("<table>").appendTo($form);
						$.each(responseJson, function(index, hotel) {
							$("<tr>").appendTo($form)
								.append($("<td>").html($("<input type=\"radio\" name=\"hotel\" value=\"" + hotel.seqNo + "\">")))
								.append($("<td>").html($("<span>").text(hotel.name)))
							;
						});
						$form.append($("<br>"));
					}
				}
			});
		}
	});
});