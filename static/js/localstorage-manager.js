export class LocalstorageManager {
    static _RECORDS_KEY = 'records'

    static #setData(data) {
        localStorage.setItem(LocalstorageManager._RECORDS_KEY, JSON.stringify(data));

    }
    static loadRecords() {
        let recordsJSON = localStorage.getItem(LocalstorageManager._RECORDS_KEY);
        if (recordsJSON === null) {
            return []
        }
        return JSON.parse(recordsJSON);
    }

    static addRecord(item) {
        let recordsJSON = localStorage.getItem(LocalstorageManager._RECORDS_KEY);
        if (recordsJSON === null) {
            let initialData = [item];
            LocalstorageManager.#setData(initialData)
        } else {
            let existingData = LocalstorageManager.loadRecords();
            existingData.push(item);
            LocalstorageManager.#setData(existingData);
        }
    }
}