var basketModleEventStore = undefined;
var basketListingEventStore = undefined;
var currentBasketId = undefined;

function createTransaction() {
  $.ajax({
    url : '/credit',
    type : 'POST',
    success : function(data) {
      $("#subscribe-to-basket-id").val(data);
      $(".lastbasketid").text(data);
      manageBasketViewModelUpdatesSubscription();
    },
    data : JSON.stringify({
      'accountId' : $("#accountId").val(),
      'customerId' : $("#customerId").val(),
      'currency' : $("#currency").val(),
      'amount' : $("#amount").val()
    }),
    contentType : "application/json"
  });
}

function customer_info() {
  $.ajax({
    url : '/customerByAccountId/' +$("#account_id").val(),
    type : 'GET',
    dataType: 'json',
    success : function(data) {
      $(".customer_name").text( data.name + ' ' + data.family );
      $(".sure_name").text( data.surname);
    },
  });
}

function listBasketsByType() {
  var typePartial = $("#account_id").val();
  manageBasketsByTypeListUpdatesSubscription(typePartial);
}

function manageBasketsByTypeListUpdatesSubscription(typePartial) {

  if (basketListingEventStore) {
    basketListingEventStore.close();
    basketListingEventStore = undefined;
  }

  $("#baskettypelisting").html("");
  basketListingEventStore = new EventSourcePolyfill('/all?id=' + typePartial,
      {
        headers : {
          authorization : 'bearer my.token.value'
        }
      });
  var listener = function(event) {
    var message;
    if (event.type === "message") {
      var data = JSON.parse(event.data);
      message = '<tr>'
          + '<td>' + data.date + '</td>'
          + '<td>' + data.transactionId + '</td>'
          + '<td>' + data.accountId + '</td>'
          + '<td>' + data.amount + '</td>'
          + '<td>' + data.balance + '</td>'
          + '<td>' + data.transactionType + '</td></tr>';
    } else {
      message = '<div>' + basketListingEventStore.url + '</div>';
    }
    $("#baskettypelisting").prepend(message);
    $(".listedbasket").click(function(event) {
      $("#subscribe-to-basket-id").val($(event.target).attr("data-basket-id"));
      manageBasketViewModelUpdatesSubscription();
    });
  };
  basketListingEventStore.addEventListener("open", listener);
  basketListingEventStore.addEventListener("message", listener);
  basketListingEventStore.addEventListener("error", listener);
}

$(function() {

  $("form").on('submit', function(e) {
    e.preventDefault();
  });
  $("#create-transaction").click(function() {
    createTransaction();
  });

  $("#subscribe-to-basket").click(function() {
    manageBasketViewModelUpdatesSubscription();
  });
  $("#subscribe-to-basket-list-by-type").click(function(event) {
    listBasketsByType();
    customer_info();
  });
});
