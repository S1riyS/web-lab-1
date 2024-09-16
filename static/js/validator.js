export class Validator {
    static _ALLOWED_Y_VALUES = ["-2", "-1.5", "-1", "-0.5", "0", "0.5", "1", "1.5", "2"]
    static _NUMBER_REGEXP = /^-?\d*\.?\d*$/

    static validateX(x) {
        if (isNaN(x)) return false;

        let isNumber = Validator._NUMBER_REGEXP.test(x);
        if (!isNumber) return false;

        return -5 <= +x && +x <= 3;
    }

    static validateY(y) {
        if (isNaN(y)) return false;

        let isNumber = Validator._NUMBER_REGEXP.test(y);
        if (!isNumber) return false;

        return Validator._ALLOWED_Y_VALUES.includes(y)
    }

    static validateR(r) {
        if (isNaN(r)) return false;

        let isNumber = Validator._NUMBER_REGEXP.test(r);
        if (!isNumber) return false;

        return 1 <= +r && +r <= 4;
    }

    static validateAll(x, y, r) {
        return Validator.validateX(x) && Validator.validateY(y) && Validator.validateR(r);
    }
}