'use.strict'

angular.module('videoMunger',['ngRoute', 'ui.bootstrap'])
    .config(['$routeProvider','$locationProvider', function ($routeProvider, $locationProvider) {
        $routeProvider.
            when('/login', {templateUrl: "/partials/login", controller: LoginCtrl}).
            when('/about', {templateUrl: "/partials/about", controller: AboutCtrl}).
            when('/contactus', {templateUrl: "/partials/contactus", controller:ContactUsCtrl}).
            otherwise({redirectTo:'/login'});

        $locationProvider.html5Mode(true);
    }]);
