$('.form-group').on('input', '.prc', function () {

    var totalPrice = 0;
    var counter = 0;
  
    $('.form-group .prc').each(function () {
      var inputVal = $(this).val();
  
      if (inputVal.includes("Junior")) {
        totalPrice = 800;
      }
  
      if (inputVal.includes("Single")) {
        totalPrice = 500;
      }
  
      if (inputVal.includes("Superior")) {
        totalPrice = 1500;
      }
  
      if (inputVal.includes("Executive")) {
        totalPrice = 2500;
      }
  
      if (inputVal.includes("Rooftop")) {
        totalPrice += 2000;
      }

      if (inputVal.includes("Terrace")) {
        totalPrice += 1200;
      }

      if (inputVal.includes("Royal")) {
        totalPrice += 2200;
      }
  
      if (inputVal.includes("Deluxe")) {
        totalPrice += 1500;
      }
  
      if (inputVal.includes("YES")) {
        totalPrice += 400;
      }
  
      if ($.isNumeric(inputVal)) {
        
        //Räknar kostnaden för staying days
        if (counter == 1) {
          totalPrice += (inputVal * 500);
        }
  
        //Räknar kostnaden för rooms
        if (counter == 2) {
          totalPrice += (inputVal * 700);
        }
  
        //Räknar kostnaden för adults
        if (counter == 3) {
          totalPrice += (inputVal * 350);
        }
  
        //Räknar kostnaden för children
        if (counter == 4) {
          totalPrice += (inputVal * 150);
        }
      }
  
      counter++;
    });
  
    document.getElementById('priceField').value = totalPrice;
    $('#price-count').text(totalPrice);
  
  });