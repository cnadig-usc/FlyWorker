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
function DashboardCtrl ($scope, $cookies, $modal, $upload,$http) {
    if ($cookies.VideoMunger) {
        $scope.login = $cookies["VideoMunger"];
        $scope.setDashboardNavigationBar();
    } else {
        $scope.goToRoot();
    }

    $scope.getLoginUser = function () {
        return $scope.login;
    }
    $scope.getCohortId = function () {
        return $scope.cohort_id;
    }

     $scope.openCohort = false;
     $scope.cohortName = '';
     $scope.noOfvials = 0;
    $scope.groups = [];
    $scope.noOfGroups = 0;
    $scope.noOfCams = 0;
    $scope.cohort_id = '';
    $scope.msg = "";


    $scope.videoGrid = [[]];

     $scope.openNewCohortForm = function () {
             var modalInstance = $modal.open({
                 templateUrl:'newCohortForm.html',
                 controller:NewCohortFormCtrl,
                 resolve: {
                    login: function () {
                        return $scope.login;
                    }
                 }
             });

             //new cohort form close
             modalInstance.result.then($scope.createCohort);
         };

    $scope.openContinueTrackingModal = function () {
        var modalInstance = $modal.open({
                         templateUrl:'continueTracking.html',
                         controller:ContinueTrackingModalCtrl,
                         resolve: {
                            login: function () {
                                return $scope.login;
                            }
                         }
                     });

                     //new cohort form close
                     modalInstance.result.then($scope.openCohort);
                 };



    $scope.openCohort = function(result ) {
        
    }

    $scope.createCohort= function(result) {
        console.log(result);
        $scope.openCohort = true;
        $scope.cohortName = result.cohort_name;
        $scope.noOfvials = result.no_of_vials;
        $scope.groups = result.groups;
        $scope.noOfGroups = $scope.groups.length | 1;
        $scope.noOfCams = result.no_of_cams | 2;
        $scope.cohort_id = result.cohort_id;
        $scope.msg = "you've created a new cohort";


        if ($scope.groups.length != 0) {
            for (var i =0; i < $scope.noOfvials / $scope.noOfGroups ; i ++ ) {
                $scope.videoGrid[i] = [];
                for (var j =0; j < $scope.groups.length; j++) {
                    $scope.videoGrid[i][j] = {
                        name: 'Group-'+$scope.groups[j]+'-Video-'+(i+1),
                        group: $scope.groups[j]
                    }
                }
            }
        } else {
            for (var i=0; i <$scope.noOfvials ; i++) {
                $scope.videoGrid[i] = [];
                for (var j=0; j < 1; j++) {
                    $scope.videoGrid[i][j] = {
                        name : 'Video-'+(i+1),
                        group: '0'
                    }
                }
            }
        }
    }
}
function ContinueTrackingModalCtrl($scope,$modalInstance, $http,$timeout,login){

    $scope.cancelContinueTrackingModal = function () {
        $modalInstance.dismiss('cancel continue tracking');
    }
    $scope.openSelectedCohort = function () {
        if($scope.selected ==undefined) {
            $timeout(function() {
                $scope.addNewContinueTrackingAlert({msg:"You did not choose from the drop down. Cannot open cohort!", type:"danger"});
            }, 100);
        }
        else {
            $modalInstance.close($scope.selected);
        }
    }

    $scope.selected = undefined;

    $scope.continueTrackingAlerts = [];

    $scope.closeContinueTrackingAlert = function (index) {
        $scope.continueTrackingAlerts.splice(index,1);
    }

    $scope.addNewContinueTrackingAlert = function (obj) {
        $scope.$apply(function () {
            $scope.continueTrackingAlerts.push(obj);
        });
    }

    $scope.selectCohortFromList = function ($item,$model,$label){
//        $modalInstance.close($item);
        $scope.selected = $item;
    }
    var cohorts = [];
    $scope.getCohorts = function (str) {
          cohorts = [];

         return $http.get('/getCohorts/'+str)
            .then(function(res){
              angular.forEach(res.data, function(item){
                cohorts.push(item);
              });
              if (cohorts.length == 0){
                $scope.selected = undefined;
              }
            return cohorts;
        });

    }
}
function NewCohortFormCtrl($scope,$modalInstance,login) {


    $scope.login = login;

    $scope.data = {
        cname :"",
        numvials:"",
        groups:[],
        exp:"",
        noOfCams:2
    };

    $scope.newCohortAlerts =  [];

    // { type: 'danger', msg: 'Oh snap! Change a few things up and try submitting again.' },
    // { type: 'success', msg: 'Well done! You successfully read this important alert message.' }


    $scope.closeNewCohortAlert = function(index) {
        $scope.newCohortAlerts.splice(index, 1);
      };


    $scope.addNewCohortAlert = function (obj) {
        $scope.$apply(function () {
            $scope.newCohortAlerts.push(obj);
        });
    }

    $scope.createNewCohort = function () {
        var cObj = {
            "cohort_name":$scope.data.cname,
            "experiment_id":$scope.data.exp,
            "created_by_user":$scope.login,
            "no_of_vials":$scope.data.numvials,
            "no_of_groups":$scope.data.groups.length | 1,
            "no_of_cams":$scope.data.noOfCams | 2
        };

        $.ajax({
            url: "/createCohort",
            type: "POST",
            data: JSON.stringify(cObj),
            contentType: "application/json; charset=utf-8"
        }).success(function (data,textStatus, jqXHR){
            data = JSON.parse(data);
            cObj['cohort_id'] = data.cohort_id;
            cObj['groups'] = $scope.data.groups;
            $modalInstance.close(cObj);
        }).error(function(jqXHR,textStatus,errorThrown){
            var al = {type:'danger'};
            al['msg'] = jqXHR.responseText.length > 100 ? jqXHR.statusText : jqXHR.responseText;
            $scope.addNewCohortAlert(al);
            console.log('error');
            console.log(jqXHR);
            console.log(textStatus);
            console.log(errorThrown);
        });

    }
    $scope.cancelNewCohortForm = function () {
        $modalInstance.dismiss('cancel');
    }

    $scope.addGroup = function (idx) {
            $scope.data.groups.push ("")
    }

    $scope.removeGroup = function (idx) {
             $scope.data.groups.splice(idx,1);
    }
}
function LoginCtrl($scope, $cookies) {
    //alert("login ctrl");
    if ($scope.login != undefined ) {
        $scope.goToDashboard($scope.login);
        //alert($scope.login);
    } 
    $scope.loggIn = function()  {
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

