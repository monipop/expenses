/**
 * Created by moni on 1/18/16.
 */
'use strict';

angular.module('financesApp')
    .controller('MenuController', ['$scope', function($scope) {
        $scope.fromDate = new Date();
        $scope.toDate = new Date();

    }]);