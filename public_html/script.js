$(document).ready(function(){
  function initialize() {
        var mapOptions = {
          center: { lat: 41.8649334, lng: -87.6523329},
          zoom: 11
        };
        window.map = new google.maps.Map(document.getElementById('map-canvas'),
            mapOptions);
      }
      google.maps.event.addDomListener(window, 'load', initialize);

  window.set_marker = function(lat, lng, stationName, numOfBikes) {
    var latLng = new google.maps.LatLng(lat,lng);
    var fillColor = 'green';
    var properties = {
      position: latLng,
      map: map,
      title: stationName
    };
    if(numOfBikes<=3){
      properties["icon"] = {
        path: google.maps.SymbolPath.CIRCLE,
        scale: 10
      }
    }
    var marker = new google.maps.Marker(properties);
  }

  

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
            "<div class='col-md-4'>" + "<a class='oreo' data-lat='" + station.latitude + "' data-long='" + station.longitude + "'>" + station.stationName + "</a>" + "</div>" + 
            "<div class='col-md-2'>" + station.availableBikes + "</div>" + 
            "<div class='col-md-2'>" + station.availableDocks + "</div>" +
          "</td>" +
        "</tr>");
      
      set_marker(station.latitude, station.longitude, station.stationName, station.availableBikes);
          
    }
  $(".oreo").click(function(event){
    var lat = $(event.currentTarget).data("lat");
    var lng = $(event.currentTarget).data("long");
    map.setCenter({lat: lat, lng: lng});
    map.setZoom(16);
  });
      
  };



  $.ajax({
    url: "https://students.ics.uci.edu/~chulm/myProxy.php?https://divvybikes.com/stations/json",
    dataType: "json",
    success: function(json){
      handle_data(json, "stationBeanList", "#info");
      
    },
    error: function(json){
    }
  });
})
