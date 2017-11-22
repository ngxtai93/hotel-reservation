$(document).ready(function(){
	$('#location').change(onChooseLocationChange);
	
	function onChooseLocationChange() {
		var location_id = $('#location').val();
		var url = window.location.href;
		$('#room-add-step-2').empty();
		$('#room-add-step-3').empty();
		
		if(location_id > 0) {
			$.ajax({
				url: (url + "?action=getHotel&location=" + location_id),
				success: function(responseJson) {
					if(responseJson == "") {
						return;
					}
					
					var $form = $("<form id=\"choose-hotel\" method=\"get\">")
						.appendTo($('#room-add-step-2'));
					$(
						"<input type=\"hidden\" name=\"location\" value=\""
						+ location_id + "\">"
					).appendTo($form);
					$form = $("<table>").appendTo($form);
					
					$.each(responseJson, function(index, hotel) {
						$("<tr>").appendTo($form)
							.append($("<td>")
								.html(
										$("<input type=\"radio\" name=\"hotel\" value=\""
										+ hotel.seqNo
										+ "\">")
								))
							.append($("<td>").html($("<span>").text(hotel.name)))
						;
					});
					$form.append($("<br>"));
					
					$("#choose-hotel").on("change", onChooseHotelChange);
				}
			});
		}
	}
	
	function onChooseHotelChange() {
		var location_id = $('#location').val();
		var hotel_id = $("input[name=\"hotel\"]:checked").val();
		var url = window.location.href;
		$('#room-add-step-3').empty();
		
		$.ajax({
			url: (url + "?action=getRoomType"),
			success: function(responseJson) {
				if(responseJson == "") {
					return;
				}
				
				var $roomType = buildSelectRoomType(responseJson);
				var $form = $("<form name=\"room-form\" id=\"room-form\" method=\"post\">")
				.appendTo($('#room-add-step-3'));
				$(
					"<input type=\"hidden\" name=\"hotel-id\" value=\""
					+ hotel_id + "\">"
				).appendTo($form);
				
				var $table = $("<table>").appendTo($form);
				$("<tr>").appendTo($table)
					.append($("<td>").text("Room number"))
					.append($("<td>").html($("<input required type=\"text\" name=\"room-num\">")));
				$("<tr>").appendTo($table)
					.append($("<td>").text("Room type"))
					.append($("<td>").html($roomType));
				$("<button id=\"submit-add-hotel\" type=\"submit\">").text("Submit").appendTo($form);
				$('#room-form').on("submit", onSubmit);
			}
		});
	}
	
	function onSubmit() {
		var roomNumVal = document.forms['room-form'].elements['room-num'].value;
		var hotelId = document.forms['room-form'].elements['hotel-id'].value;
		var url = window.location.href;
		$("#room-add-error").empty();
		var isError = false;
		
		if(!($.isNumeric(roomNumVal)) || roomNumVal < 0) {
			$("<span style=\"color:red\">")
			.text("Invalid room number.").appendTo($("#room-add-error"));
			isError = true;
			return false;
		}
		
		$.ajax({
			url: (url + "?action=isRoomExist&hotel=" + hotelId + "&room=" + roomNumVal),
			method: 'GET',
			async: false,
			success: function(responseJson) {
				console.log(responseJson);
				if(responseJson == "true") {
					$("<span style=\"color:red\">")
					.text("Room number already exists.").appendTo($("#room-add-error"));
					isError = true;
				}
				else if(responseJson != "false") {
					$("<span style=\"color:red\">")
					.text("Error adding room.").appendTo($("#room-add-error"));
					isError = true;
				}
			}
		});
		if(isError) {
			return false;
		}
		return true;
	}
	
	function buildSelectRoomType(responseJson) {
		$result = $("<select name=\"room-type\" id=\"room-type\">");
		$("<option value=\"0\">").text("Select room type").appendTo($result);
		$.each(responseJson, function(index, roomType) {
			$("<option value=\"" + roomType.seqNo + "\">").text(roomType.name).appendTo($result);
		});
		return $result;
	}
});