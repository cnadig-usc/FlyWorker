'use.strict'

angular.module('videoMunger',['ngRoute', 'ngCookies','ui.bootstrap', 'vmComponents'])
    .config(['$routeProvider','$locationProvider', function ($routeProvider, $locationProvider) {
        $routeProvider.
            when('/login', {templateUrl: "/partials/login", controller: LoginCtrl}).
            when('/about', {templateUrl: "/partials/about", controller: AboutCtrl}).
            when('/contactus', {templateUrl: "/partials/contactus", controller:ContactUsCtrl}).
            when('/dashboard', {templateUrl: "/partials/dashboard", controller:DashboardCtrl}).
            when('/logout', {templateUrl: "/partials/logout", controller:LogoutCtrl}).
            otherwise({redirectTo:'/login'});

        $locationProvider.html5Mode(true);
    }]);
