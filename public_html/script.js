$(document).ready(function(){
  $.ajax({
		url: "https://students.ics.uci.edu/~chulm/myProxy.php?https://divvy.com/stations/json",
		dataType: "json",
		success: function(json){
		  window.franklin = json;
		},
		error: function(json){
		}
	});
})
