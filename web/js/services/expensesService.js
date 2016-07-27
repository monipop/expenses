'use strict';

angular.module('financesApp')
    .constant("baseURL", "http://localhost:8080/expenses/")
    .service('expensesFactory', ['$resource', 'baseURL', function($resource, baseURL) {
        return $resource(baseURL + "from/:date/to/:date");
    }]);