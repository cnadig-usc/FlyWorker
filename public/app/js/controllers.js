'use strict';
function MainController ($scope, $location, $cookies) {
    $scope.msg = "No cohort open";
    $scope.goToDashboard = function(login) {
        $scope.login = login;
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
        //alert("going to root");
            $location.path('/');
    }

    $scope.logout = function () {
            $cookies.VideoMunger = undefined;
            //alert("logout function");
            $scope.login = undefined;

            $scope.setLoginNavigationBar();
            //$scope.goToRoot();
        }
    $scope.setLoginNavigationBar();
}
function DashboardCtrl ($scope, $cookies) {
    //alert('dbc ctrl');
    if ($cookies.VideoMunger) {
        //alert('cookie is set');
        $scope.setDashboardNavigationBar();
    } else {
        $scope.goToRoot();
    }

    $scope.openCohort = false;



    $scope.createCohort = function() {

//        $scope.cname = "";
//        $scope.nflies = "";
//        $scope.ngroups = "";
        $scope.openCohort = true;
        $scope.cohortName = $scope.cname;
        $scope.noOfGroups = $scope.ngroups;
        $scope.noOfFlies = $scope.nflies;
        $scope.msg = "you've opened a new cohort";
    }


     //$scope.$apply();
}
function LoginCtrl($scope, $cookies) {
    //alert("login ctrl");
    if ($scope.login != undefined ) {
        $scope.goToDashboard($scope.login);
        //alert($scope.login);
    } 
    $scope.open = function()  {
        $.ajax ({
            url: "/authenticate",
            type: "POST",
            data: JSON.stringify({"username":$scope.login, "password":$scope.password}),
            datatype:"json",
            contentType: "application/json; charset=utf-8"
        }).success(function(data, textStatus, jqXHR){
            $scope.goToDashboard($scope.login);
        }).done (function(data) {
        }).error(function(jqXHR, textStatus, errorThrown){
            ////alert("error");
        });

    }
};
function LogoutCtrl($scope,$cookies ) {
    //alert('logout ctrl');
    $scope.logout();
    $scope.goToRoot();
}
function AboutCtrl ($scope) {

}
function ContactUsCtrl($scope) {

}

// Please note that $modalInstance represents a modal window (instance) dependency.
// It is not the same as the $modal service used above.

