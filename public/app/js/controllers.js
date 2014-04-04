'use strict';

function MainController ($scope, $location, $cookies) {
    $scope.test = "Hey!"
    $scope.goToDashboard = function(login) {

        $scope.login = login;
        alert("go to dashboard!");
        $scope.$apply($location.path('/dashboard'));

    }



}
function DashboardCtrl ($scope, $location, $cookies) {
    alert('dbc ctrl');
    if ($cookies.VideoMunger) {
        alert('cookie is set');
    } else {
        $scope.apply($location.path('/'));

    }
}

function AboutCtrl ($scope) {

}
function ContactUsCtrl($scope) {

}
var LoginCtrl = function ($scope,$location, $cookies) {

    $scope.open = function()  {

        $.ajax ({
            url: "/authenticate",
            type: "POST",
            data: JSON.stringify({"username":$scope.login, "password":$scope.password}),
            datatype:"json",
            contentType: "application/json; charset=utf-8"
        }).success(function(data, textStatus, jqXHR){

            console.log(data);
            console.log(textStatus);

            $scope.goToDashboard($scope.login);



        }).done (function(data) {


        }).error(function(jqXHR, textStatus, errorThrown){
            alert("error");

        });

    }
};

// Please note that $modalInstance represents a modal window (instance) dependency.
// It is not the same as the $modal service used above.

