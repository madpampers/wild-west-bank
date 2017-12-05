$(document).ready(function () {
    $('form.delete-button').on("submit", function (e) {
        e.preventDefault();
        if (confirm("Confirm deleting client from database:")) {
            var id = $(this).find('input[name="id"]').val();
            var form = $(this);
            $.ajax({
                type: "POST",
                url: "/clients/delete",
                data: {'id': id},
                success: function () {
                    form.parents('tr').remove()
                }
            });
        }

    });

    $('#editclient').click(function (e) {
        e.preventDefault();

        var clientId, clientName, clientAge, clientAddress;
        clientId = $('#client_id').val();
        clientName = $('#client_name').val();
        clientAge = $('#client_age').val();
        clientAddress = $('#client_address').val();
        if ($.trim(clientName) === "") {
            alert("Client name cannot be empty");
        }
        else if (!$.trim($.isNumeric(clientAge))) {
            alert("Age must be numeric");
        }
        else {
            var data = {};
            data["id"] = clientId;
            data["name"] = clientName;
            data["age"] = clientAge;
            data["address"] = clientAddress;
            $.ajax({
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify(data),
                url: "/clients/save",
                dataType: 'json',
                timeout: 6000,
                success:
                    function (data) {
                        if (confirm("Client " + data.name + " Saved")) {
                            window.location.href = "/clients/" + data.id;
                        } else {
                            window.location.reload();
                        }
                    }
            });
        }
    });

    $('#accountcreate').click(function (e) {
        e.preventDefault();

        var accountType, accountBalance, accountHolder;
        accountType = $('#accounttype').val();
        accountBalance = $('#accountbalance').val();
        accountHolder = $('#accountholder').val();
        if (!$.trim($.isNumeric(accountBalance))) {
            alert("Balance must be numeric");
        }
        else {
            $.ajax({
                type: "POST",
                url: "/account/create",
                data: {
                    'type': accountType,
                    'client': accountHolder,
                    'balance': accountBalance
                },
                success:
                    function () {
                        if (confirm("Account created")) {
                            window.location.reload();
                        }
                    }
            });
        }
    });

    $('.close-button').on("click", function (e) {
        e.preventDefault();
        if (confirm("Confirm deleting account:")) {
            var id = $(this).find('input[name="id"]').val();
            var form = $(this);
            $.ajax({
                type: "POST",
                url: "/account/close",
                data: {'id': id},
                success: function () {
                    form.parents('tr').remove()
                }
            });
        }

    });

    $('#deposit').on("submit", function (e) {
        e.preventDefault();

        var transactionId, transactionFrom, transactionTo, transactionTime, amount, result;
        transactionId = null;
        transactionFrom = "CASH";
        transactionTo = $('#accountid').text();
        transactionTime = null;
        amount = $('#toDeposit').val();
        result = parseFloat(amount) + parseFloat($('#balance').text());
        if (!$.trim($.isNumeric(amount))) {
            alert("Amount must be numeric");
        }
        else {
            var data = {};
            data["id"] = transactionId;
            data["fromAccount"] = transactionFrom;
            data["toAccount"] = transactionTo;
            data["timestamp"] = transactionTime;
            data["amount"] = amount;
            $.ajax({
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify(data),
                url: "/transactions/process",
                dataType: 'json',
                timeout: 6000,
                success:
                    function (data) {
                        if (confirm("Success")) {
                            $('#headerRawDeposit').after('<tr><td>' + moment(data.timestamp).format("DD:MM:YYYY HH:mm:ss") + '</td><td>' + data.fromAccount + '</td><td>' + parseFloat(data.amount) + '</td></tr>');
                            $('#balance').text(result)
                        }
                    }
            });
        }
    });

    $('#withdraw').on("submit", function (e) {
        e.preventDefault();

        var transactionId, transactionFrom, transactionTo, transactionTime, amount, result;
        transactionId = null;
        transactionFrom = $('#accountid').text();
        transactionTo = "CASH";
        transactionTime = null;
        amount = $('#toWithdraw').val();
        result = parseFloat($('#balance').text()) - parseFloat(amount);
        if (!$.trim($.isNumeric(amount))) {
            alert("Amount must be numeric");
        }
        else {
            var data = {};
            data["id"] = transactionId;
            data["fromAccount"] = transactionFrom;
            data["toAccount"] = transactionTo;
            data["timestamp"] = transactionTime;
            data["amount"] = amount;
            $.ajax({
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify(data),
                url: "/transactions/process",
                dataType: 'json',
                timeout: 6000,
                success:
                    function (data) {
                        if (confirm("Success")) {
                            $('#headerRawWithdraw').after('<tr><td>' + moment(data.timestamp).format("DD:MM:YYYY HH:mm:ss") + '</td><td>' + data.toAccount + '</td><td>' + parseFloat(data.amount) + '</td></tr>');
                            $('#balance').text(result)
                        }
                    },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    alert("Status: " + textStatus);
                    alert("Error: " + errorThrown);
                }
            });
        }
    });

    $('#transfer').on("submit", function (e) {
        e.preventDefault();

        var transactionId, transactionFrom, transactionTo, transactionTime, amount;
        transactionId = null;
        transactionFrom = $('#fromAccount').val();
        transactionTo = $('#toAccount').val();
        transactionTime = null;
        amount = $('#amount').val();
        if (!$.trim($.isNumeric(amount))) {
            alert("Amount must be numeric");
        }
        else {
            var data = {};
            data["id"] = transactionId;
            data["fromAccount"] = transactionFrom;
            data["toAccount"] = transactionTo;
            data["timestamp"] = transactionTime;
            data["amount"] = amount;
            $.ajax({
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify(data),
                url: "/transactions/process",
                dataType: 'json',
                timeout: 6000,
                success:
                    function (data) {
                        alert("Success!");
                        $('#transactionsHeaderRow').after('<tr><td>' + data.id + '</td><td>' + data.amount + '</td><td>' + data.fromAccount + '</td><td>' + data.toAccount + '</td><td>' + moment(data.timestamp).format("DD:MM:YYYY HH:mm:ss") + '</td></tr>');
                    },
                error: function (response) {
                    var r = jQuery.parseJSON(response.responseText);
                    alert("Message: " + r.message);
                }
            });
        }
    });
});