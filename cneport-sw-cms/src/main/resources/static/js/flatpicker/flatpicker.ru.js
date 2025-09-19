(function (global, factory) {
    typeof exports === 'object' && typeof module !== 'undefined' ? factory(exports) :
        typeof define === 'function' && define.amd ? define(['exports'], factory) :
            (global = typeof globalThis !== 'undefined' ? globalThis : global || self, factory(global.ru = {}));
}(this, (function (exports) { 'use strict';

    var fp = typeof window !== "undefined" && window.flatpickr !== undefined
        ? window.flatpickr
        : {
            l10ns: {},
        };
    var Mandarin = {
        weekdays: {
            shorthand: ["Вск", "Пнд", "Втр", "Срд", "Чтв", "Птн", "Сбт"],
            longhand: [
                "Вск",
                "Пнд",
                "Втр",
                "Срд",
                "Чтв",
                "Птн",
                "Сбт",
            ],
        },
        months: {
            shorthand: [
                "января",
                "февраля",
                "марта",
                "апреля",
                "мая",
                "июня",
                "июля",
                "августа",
                "сентября",
                "октября",
                "ноября",
                "декабря",
            ],
            longhand: [
                "января",
                "февраля",
                "марта",
                "апреля",
                "мая",
                "июня",
                "июля",
                "августа",
                "сентября",
                "октября",
                "ноября",
                "декабря",
            ],
        },
        rangeSeparator: " До ",
        weekAbbreviation: "Чжоу",
        scrollTitle: "Переключение прокрутки",
        toggleTitle: "Нажмите, чтобы переключить 12/24 часов",
    };
    fp.l10ns.ru = Mandarin;
    var ru = fp.l10ns;

    exports.Mandarin = Mandarin;
    exports.default = ru;

    Object.defineProperty(exports, '__esModule', { value: true });

})));