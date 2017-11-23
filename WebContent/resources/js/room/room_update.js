$(document).ready(function(){
	$('#location').change(onChooseLocationChange);
	
	function onChooseLocationChange() {
		var location_id = $('#location').val();
		var url = window.location.href;
		$('#room-update-step-2').empty();
		$('#room-update-step-3').empty();
		$('#room-update-step-4').empty();
		
		if(location_id > 0) {
			$.ajax({
				url: (url + "?action=getHotel&location=" + location_id),
				success: function(responseJson) {
					if(responseJson == "") {
						return;
					}
					
					var $form = $("<form id=\"choose-hotel\" method=\"get\">")
						.appendTo($('#room-update-step-2'));
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
		$('#room-update-step-3').empty();
		$('#room-update-step-4').empty();
		
		$.ajax({
			url: (url + "?action=getRoom&hotel=" + hotel_id),
			success: function(responseJson) {
				var $table = $("<table>").appendTo($('#room-update-step-3'));
				$.each(responseJson, function(index, room) {
					$("<tr>").appendTo($table)
						.append($("<td>")
							.html(
									$("<input type=\"radio\" name=\"room-id\" value=\""
									+ room.seqNo
									+ "\">")
							))
						.append($("<td>").html($("<span>").text("Room " + room.roomNumber)))
					;
				});
				$('input[type=radio][name=room-id]').on("change", function() {onChooseRoom(responseJson)});
				
			},
			error: function(responseJson) {
				$("<span style=\"color:red\">").text("No room available for update.").appendTo($('#room-update-step-3'));
			}
		});
	}
	
	function onChooseRoom(jsonRoom) {
		$('#room-update-step-4').empty();
		var checked = $("input[type=radio][name=room-id]:checked").val();
		var roomObject = null;
		for(var i = 0; i < jsonRoom.length; i++) {
			if(jsonRoom[i].seqNo == checked) {
				roomObject = jsonRoom[i];
			}
		}
		// get list room type
		var $selectRoomType = null;
		var url = window.location.href;
		$.ajax({
			url: (url + "?action=getRoomType"),
			method: "GET",
			async: false,
			success: function(responseJson) {
				$selectRoomType = buildSelectRoomType(responseJson, roomObject.roomType.seqNo)
			}
		});
		var $form = $("<form name=\"room-update-form\" id=\"room-update-form\" method=\"post\">")
			.appendTo($("#room-update-step-4"));
		$("<input type=\"hidden\" name=\"room-id\" value=\"" + checked + "\">").appendTo($form);
		var $table = $("<table>").appendTo($form);
		$("<tr>").appendTo($table)
			.append($("<td>").append($("<span>").text("Room number")))
			.append($("<td>").append($("<input required name=\"room-num\" type=\"text\" value=\"" + roomObject.roomNumber + "\">")))
		;
		$("<tr>").appendTo($table)
			.append($("<td>").append($("<span>").text("Room type")))
			.append($("<td>").append($selectRoomType))
		;
		$("<button id=\"submit-add-hotel\" type=\"submit\">").text("Submit").appendTo($form);
		$('#room-update-form').on("submit", onSubmit);
	}
	
	function onSubmit() {
		var roomNumVal = document.forms['room-update-form'].elements['room-num'].value;
		var url = window.location.href;
		$("#room-update-error").empty();
		var isError = false;
		
		if(!($.isNumeric(roomNumVal)) || roomNumVal < 0) {
			$("<span style=\"color:red\">")
			.text("Invalid room number.").appendTo($("#room-update-error"));
			isError = true;
			return false;
		}
		
		return true;
	}
	
	function buildSelectRoomType(responseJson, curRoomTypeSeqNo) {
		$result = $("<select name=\"room-type\" id=\"room-type\">");
		$.each(responseJson, function(index, roomType) {
			var selected = "";
			console.log(curRoomTypeSeqNo);
			console.log(roomType.seqNo);
			if(roomType.seqNo == curRoomTypeSeqNo) {
				selected = " selected";
			}
			$("<option value=\"" + roomType.seqNo + "\"" + selected + ">").text(roomType.name).appendTo($result);
		});
		return $result;
	}
});