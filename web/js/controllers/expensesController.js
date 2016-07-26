'use strict';
angular.module('financesApp')
       .controller('ExpensesController', ['$scope', '$http', function($scope, $http) {
            $scope.expenses = [];
                $http.get('/expenses/expenses').success(function(data){
                    $scope.expenses = data;

                });
       }]);
