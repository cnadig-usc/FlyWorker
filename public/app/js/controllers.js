'use strict';

function MainController ($scope, $location, $cookies) {
    $scope.test = "Hey!"
    $scope.goToDashboard = function(login) {
        $scope.login = login;
        //alert("go to dashboard!");
        $location.path('/dashboard');

    }
    $scope.setLoginNavigationBar = function () {
        $scope.navigationBar = [
                {
                    content:"About",
                    link:"/about"

                },
                {
                    content:"Contact Us",
                    link:"/contactus"


                }
            ];
    }
    $scope.setDashboardNavigationBar = function () {
        $scope.navigationBar = [
                {
                    content: "Dashboard",
                    link: "#"
                },
                {
                    content:"Logout",
                    link:"/logout"
                }
            ];
    }
    $scope.goToRoot = function () {
        alert("going to root");
            $location.path('/');
        }
    $scope.logout = function () {
            $cookies.VideoMunger = undefined;

            alert("logfdfdout");

            $scope.setLoginNavigationBar();
            //$scope.goToRoot();
        }
    $scope.setLoginNavigationBar();
}


function DashboardCtrl ($scope, $cookies) {
    alert('dbc ctrl');
    if ($cookies.VideoMunger) {
        alert('cookie is set');
        $scope.setDashboardNavigationBar();
    } else {
        $scope.goToRoot();
    }

     //$scope.$apply();

}


var LoginCtrl = function ($scope, $cookies) {
    alert("login ctrl");
    if ($cookies.VideoMunger) {
        $scope.goToDashboard($scope.login);

        alert($cookies.VideoMunger);
    } else {
        //$scope.goToRoot();
    }


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
function LogoutCtrl($scope,$cookies ) {
    alert('logout ctrl');

    $scope.logout();
    $scope.goToRoot();



}
function AboutCtrl ($scope) {

}
function ContactUsCtrl($scope) {

}

// Please note that $modalInstance represents a modal window (instance) dependency.
// It is not the same as the $modal service used above.

