var mysql = require('mysql');

// DATABASE SCHEMA -- https://dev.mysql.com/doc/employee/en/employees-installation.html
var connection = mysql.createConnection({
	host: 'localhost',
	user: 'root',
	password: 'foo',
	database: 'employees'
});
var javaPart = Java.type("hackathon.Main").init();
javaPart.connect(connection); /*connection.connect(); */

connection.findName = function(id, salary) {
    connection.query('SELECT first_name, last_name FROM employees WHERE emp_no = ' + id, function(error, results, fields) {
       javaPart.names(id, results[0], salary); 
    });
}

javaPart.doQuery(connection);
connection.query('SELECT * FROM salaries WHERE salary < 39000', function(error, results, fields) {
	if (error) throw error;
	console.log('computed from JS');
        javaPart.results(results, connection);
	connection.end();

	// TODO (1) run a second query: get the names of some employees with salary < 40000 (e.g., "emp_no == 12304") from the table called 'employees'.

	// TODO (2) dump name, surname and salary to file line-by-line using the following JSON format: {name:"foo",surname:"bar",salary:10000}

});
