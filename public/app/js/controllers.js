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
function DashboardCtrl ($scope, $cookies, $modal, $upload) {
    //alert('dbc ctrl');
    if ($cookies.VideoMunger) {
        //alert('cookie is set');
        $scope.login = $cookies["VideoMunger"];
        console.log($cookies);
        $scope.setDashboardNavigationBar();
    } else {
        $scope.goToRoot();
    }

    $scope.openCohort = false;

    $scope.cohortName='';
    $scope.numvials='';
    $scope.noOfGroups = 1;
    $scope.videoGrid = [[]];

     $scope.openNewCohortForm = function () {

        console.log('openNewcohortorm'+$scope.login);
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
             modalInstance.result.then($scope.createCohort, function (err) {

             });
         };

    $scope.createCohort= function(result) {
        console.log(result);
                            $scope.openCohort = true;
                            $scope.cohortName = result.cname;
                            $scope.noOfvials = result.nvials;
                            $scope.groups = result.groups;
                            $scope.noOfGroups = $scope.groups.length | 1;
                            $scope.noOfCams = result.noOfCams | 2;

                            $scope.msg = "you've created a new cohort";


                            if ($scope.groups.length != 0) {
                                for (var i =0; i < $scope.noOfvials / $scope.noOfGroups ; i ++ ) {
                                    $scope.videoGrid[i] = [];
                                    for (var j =0; j < $scope.groups.length; j++) {
                                        $scope.videoGrid[i][j] = 'Group-'+$scope.groups[j]+'-Video-'+(i+1);
                                    }
                                }
                            } else {
                                for (var i=0; i <$scope.noOfvials ; i++) {
                                    $scope.videoGrid[i] = [];
                                    for (var j=0; j < 1; j++) {
                                            $scope.videoGrid[i][j] = 'Video-'+(i+1);
                                    }
                                }

                            }
    }

//    $scope.createCohort ({cname:'Test',nvials:10,groups:['M','V']});

}
function NewCohortFormCtrl($scope,$modalInstance,login) {


    $scope.login = login;
    $scope.data = {
        cname :"",
        numvials:"",
        groups:[],
        exp:"",
        noOfCams:2
    }

    $scope.newCohortAlerts =  [
//                                 { type: 'danger', msg: 'Oh snap! Change a few things up and try submitting again.' },
//                                 { type: 'success', msg: 'Well done! You successfully read this important alert message.' }
                               ];

    $scope.closeNewCohortAlert = function(index) {
        $scope.newCohortAlerts.splice(index, 1);
      };


    $scope.addNewCohortAlert = function (obj) {


        $scope.$apply(function () {

            $scope.newCohortAlerts.push(obj);
        });
    }

    $scope.createNewCohort = function () {


        console.log($scope.login);


                console.log('create Cohort '+JSON.stringify({
                                                             "cohort_name":$scope.data.cname,
                                                             "experiment_id":$scope.data.exp,
                                                             "created_by_user":$scope.login,
                                                             "no_of_vials":$scope.data.numvials,
                                                             "no_of_groups":$scope.data.groups.length | 1,
                                                             "no_of_cams":$scope.data.noOfCams | 2
                                                         }));


                $.ajax({
                    url:"/createCohort",
                    type:"POST",
                    data: JSON.stringify({
                        "cohort_name":$scope.data.cname,
                        "experiment_id":$scope.data.exp,
                        "created_by_user":$scope.login,
                        "no_of_vials":$scope.data.numvials,
                        "no_of_groups":$scope.data.groups.length | 1,
                        "no_of_cams":$scope.data.noOfCams | 2
                    }),
                    contentType: "application/json; charset=utf-8"
                }).success(function(data,textStatus, jqXHR) {
                    console.log('noOfCams: '+$scope.data.noOfCams);
                     $modalInstance.close({cname:$scope.data.cname,nvials:$scope.data.numvials, groups:$scope.data.groups, exp:$scope.data.exp, noOfCams:$scope.data.noOfCams});


                }).error(function(jqXHR,textStatus,errorThrown){

                    var al = {type:'danger'};
                    al['msg'] = jqXHR.responseText.length > 100 ? jqXHR.statusText : jqXHR.responseText;

                    $scope.addNewCohortAlert(al);

                    console.log(jqXHR);
                    console.log(textStatus);
                    console.log(errorThrown);
                    console.log('error');
                });

    };


    $scope.cancelNewCohortForm = function () {
        $modalInstance.dismiss('cancel');
    }

     $scope.addGroup = function (idx) {
            $scope.data.groups.push ("");
//            console.log($scope.data.groups.length);
//                    console.log($scope.data.groups);
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

