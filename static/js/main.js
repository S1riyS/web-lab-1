import {Validator} from "./validator.js";
import {HistoryManager} from './history-manager.js'
import {BACKEND_URL} from "./config.js";

HistoryManager.loadRecords();

$("#submit").on("click", function () {
    // Checking if form is valid in terms of HTML's built-in validator
    let valid = this.form.checkValidity();
    if (valid) event.preventDefault();
    else return;

    // Gathering data
    let data = {
        "x": $("input[name='x-input']").val(),
        "y": $("input[name='y-input']:checked").val(),
        "r": $("input[name='r-input']").val()
    }

    // Validating data
    // console.log(data.x, data.y, data.r)
    // console.log(Validator.validateX(data.x), Validator.validateY(data.y), Validator.validateR(data.r))
    if (!Validator.validateAll(data.x, data.y, data.r)) {
        swal("Ошибка", "Вы ввели некорректные параметры", "error")
        return;
    }

    // Sending request to server
    $.ajax({
        url: `${BACKEND_URL}?` + $.param(data),
        type: "GET",
        dataType: "json",
        success: function (response) {
            if (response.success) {
                HistoryManager.addRecord(data.x, data.y, data.r, response.hit)
            } else {
                swal("Ошибка", "Во время выполнения запроса произошла ошибка", "error")
            }
        },
        error: function (xhr, status, error) {
            swal("Ошибка", "Во время выполнения запроса произошла ошибка", "error")
        }
    })
})