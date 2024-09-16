import {LocalstorageManager} from "./localstorage-manager.js";

export class HistoryManager {
    static #wrapInTdTag(data, class_ = null) {
        if (class_ === null) return "<td>" + data + "</td>"
        return `<td class="${class_}">` + data + "</td>"
    }

    static #wrapInTrTag(data) {
        return "<tr>" + data + "</tr>"
    }

    static #processResult(result) {
        return result ? 'Попадание' : 'Промах';
    }

    static #buildItem(x, y, r, result, datetime) {
        return {
            "x": x,
            "y": y,
            "r": r,
            "result": result,
            "datetime": datetime,
        }
    }

    static #renderItem(item) {
        let processedResult = HistoryManager.#processResult(item.result);
        let resultClass = item.result ? 'hit' : 'miss'

        return HistoryManager.#wrapInTrTag(
            HistoryManager.#wrapInTdTag(item.x) +
            HistoryManager.#wrapInTdTag(item.y) +
            HistoryManager.#wrapInTdTag(item.r) +
            HistoryManager.#wrapInTdTag(processedResult, resultClass) +
            HistoryManager.#wrapInTdTag(item.datetime)
        )
    }

    static #addRecordToTable(x, y, r, result, datetime) {
        let tableBody = $('#history-table-body');

        let item = HistoryManager.#buildItem(x, y, r, result, datetime);
        let newRow = HistoryManager.#renderItem(item);

        tableBody.prepend(newRow);
    }

    static #addRecordToLocalStorage(x, y, r, result, datetime) {
        let newItem = HistoryManager.#buildItem(x, y, r, result, datetime);
        LocalstorageManager.addRecord(newItem);
    }

    static addRecord(x, y, r, result) {
        let currentDate = new Date();
        let formattedDate = currentDate.toLocaleString();

        HistoryManager.#addRecordToTable(x, y, r, result, formattedDate);
        HistoryManager.#addRecordToLocalStorage(x, y, r, result, formattedDate);
    }

    static loadRecords() {
        let tableBody = $('#history-table-body');

        let records = LocalstorageManager.loadRecords();
        records.forEach((item) => {
            let newRow = HistoryManager.#renderItem(item);
            tableBody.prepend(newRow);
        })
    }
}