
/* global process, require, Java */

var className;

process.argv.forEach(function (val, index, array) {
    if (index === 2) {
        className = val;
    }
});

var mainClass = Java.type(className);
new mainClass(require).run();