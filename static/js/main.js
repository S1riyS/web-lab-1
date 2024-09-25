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
                HistoryManager.addRecord(data.x, data.y, data.r, response.data.hit, response.data.currentTime, response.data.scriptTime)
            } else {
                swal("Ошибка", response.message, "error")
            }
        },
        error: function (xhr, status, error) {
            swal(
                `Ошибка ${xhr.status}`,
                `Во время выполнения запроса произошла ошибка\n(${xhr.responseJSON.message})`,
                "error"
            )
        }
    })
})