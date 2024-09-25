export class TimeUtil {
    static dateTimeFromMilliseconds(ms) {
        return new Date(ms).toLocaleString();
    }

    static timePassedFromMilliseconds(ms) {
        return new Date(ms).toISOString().slice(11, 19)
    }
}