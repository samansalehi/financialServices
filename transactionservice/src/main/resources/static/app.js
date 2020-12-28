var transactionListingEventStore = undefined;

function createTransaction() {
  $.ajax({
    url : '/credit',
    type : 'POST',
    success : function(data) {
      $("#subscribe-to-basket-id").val(data);
      $(".lastbasketid").text(data);
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
      $(".customer_name").text(data.name);
      $(".sure_name").text(data.surname);
    },
  });
}

function listOfTransactionByAccountId() {
  var typePartial = $("#account_id").val();
  manageBasketsByTypeListUpdatesSubscription(typePartial);
}

function manageBasketsByTypeListUpdatesSubscription(typePartial) {

  if (transactionListingEventStore) {
    transactionListingEventStore.close();
    transactionListingEventStore = undefined;
  }

  $("#transaction_list").html("");
  transactionListingEventStore = new EventSourcePolyfill('/all?id=' + typePartial,
      {
        headers: {
          authorization: 'bearer my.token.value'
        }
      });
  var listener = function (event) {
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
    }
    $("#transaction_list").prepend(message);
  };
  transactionListingEventStore.addEventListener("open", listener);
  transactionListingEventStore.addEventListener("message", listener);
  transactionListingEventStore.addEventListener("error", listener);
}

$(function() {

  $("form").on('submit', function (e) {
    e.preventDefault();
  });
  $("#create-transaction").click(function () {
    createTransaction();
  });

  $("#transaction_list_by_account_id").click(function (event) {
    listOfTransactionByAccountId();
    customer_info();
  });
});
