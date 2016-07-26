(function(angular) {
    'use strict';

    angular.module('financesApp', ['ui.router', 'ngResource'])
        .config(['$httpProvider','$stateProvider', '$locationProvider', '$urlRouterProvider',
            function($httpProvider, $stateProvider, $locationProvider, $urlRouterProvider) {
                //$urlRouterProvider.when('/', '/expenses');
                //$urlRouterProvider.otherwise('/home');

                $stateProvider
                    .state('home', {
                        url:'/home',
                        views: {
                            'menu' : {
                                templateUrl: 'views/menu.html',
                                controller: 'MenuController'
                            },
                            'home' : {
                                templateUrl: 'views/home.html'
                            }
                        }
                    })
                    .state('expenses', {
                        url:'/expenses',
                        views: {
                            'menu': {
                                templateUrl: 'views/menu.html',
                                controller: 'MenuController'
                            },
                            'time-form': {
                                templateUrl: 'views/time-form.html',
                                controller: 'TimeFormController'
                            },
                            'expenses': {
                                templateUrl : 'views/expenses.html',
                                controller: 'ExpensesController'
                            }
                        }
                    });
                $locationProvider.html5Mode(true);

    }]);


/*var app = angular.module('financesApp', ['ui.router', 'ngResource'])

      .config(function($stateProvider, $urlRouterProvider) {
          $urlRouterProvider.otherwise('/');

          $stateProvider
          // route for the home page
          .state('home', {
            url:'/',
            views: {
                'menu' : {
                    templateUrl: 'views/menu.html',
                    controller: 'MenuController'
                },
                'home' : {
                    templateUrl: 'views/home.html'
                }
            }
          })
          .state('expenses', {
              url:'/expenses/from/:from/to/:to',
              views: {
                  'menu': {
                      templateUrl: 'views/menu.html',
                      controller: 'MenuController'
                  },
                  'time-form': {
                      templateUrl: 'views/time-form.html',
                      controller: 'TimeFormController'
                  },
                  'expenses': {
                      templateUrl : 'views/expenses.html',
                      controller: 'ExpensesController'
                  }
              }
          });
      });
*/

})(window.angular);