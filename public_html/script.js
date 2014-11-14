$(document).ready(function(){

  window.handle_data = function(my_data, where_it_is, destination){
    
    for (var i = 0; i < my_data[where_it_is].length; i++) { 
      var station = my_data[where_it_is][i];
      var labelclass = "label-success";
      if (station.statusKey == 0) {
        labelclass = "label-danger"
      };
      $(destination).append(
        "<tr>" +
          "<td>" +
            "<div class='col-md-2'><label class='label " + labelclass + "'>" + station.statusValue + "</label></div>" +
            "<div class='col-md-4'>" + station.stationName + "</div>" + 
            "<div class='col-md-1' style='overflow:hidden;'>" + station.latitude + "</div>" +
            "<div class='col-md-1' style='overflow:hidden;'>" + station.longitude + "</div>" +
            
            "<div class='col-md-2'>" + station.availableBikes + "</div>" + 
            "<div class='col-md-2'>" + station.availableDocks + "</div>" +
          "</td>" +
        "</tr>");
    }

  };

  $.ajax({
    type: "GET",
    url: "https://students.ics.uci.edu/~chulm/myProxy.php?https://divvy.com/stations/json",
    dataType: "json",
    contentType: "application/json",
    success: function(json){
      handle_data(json, "stationBeanList", "#info");
      
    },
    error: function(json){
    }
  });
})
