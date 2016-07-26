'use strict';
angular.module('financesApp')
       .controller('TimeFormController', ['$scope', '$stateParams', function($scope, $stateParams) {
           $scope.from = new Date();
           $scope.to = new Date();
           $scope.message = "Random message";
           $scope.filter = function() {
                $scope.message = "se vor filtra";
           };

       }]);


