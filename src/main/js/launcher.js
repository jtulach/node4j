
/* global process, require, Java */

try {
    var executor = require('graal/event_loop');
} catch (ignore) {
}

var className;

process.argv.forEach(function (val, index, array) {
    if (index === 2) {
        className = val;
    }
});

var mainClass = Java.type(className);
new mainClass(require, global, executor).initialize();
