angular.module('vmComponents', [] )
    .directive('videoElement', function ($timeout) {
        return {
            restrict:'E',

            scope:{
                videoName:'@name',
                no_of_cams:'@noofcams',
                getLoginUser:"&loginuser",
                getCohortId:"&cohortid",
                group:'@group'
            },
            templateUrl: "/partials/videoelement",
            link: function (scope, elem, attrs) {

            },
            controller: function ($scope,$upload) {
//                $scope.vidstatus = 'novideo';
                $scope.videoStatuses = {};
                $scope.videoSetNames = []
                for (var i =0; i<$scope.no_of_cams; i++) {
                    $scope.videoSetNames.push($scope.videoName+'-cam-'+i.toString());
                    $scope.videoStatuses[$scope.videoName+'-cam-'+i.toString()] = 'novideo';
                }

                $scope.openVideoUploader = function (vid) {
                    $timeout(function() {
                                            $("#"+vid).trigger('click');
                                        }, 100);

                }
                $scope.onFileSelect = function($files,vid, camera_id) {

                    console.log($files);
                    console.log(vid);

                    var vObj = {
                        video_name:vid,
                        uploaded_by_user:$scope.getLoginUser(),
                        cohort_id:$scope.getCohortId(),
                        group: $scope.group,
                        camera_id:camera_id
                    };
                    console.log('vObj: '+vObj);
                    console.log(vObj);
                    $scope.upload = $upload.upload({
                        url:'/videoUpload',
                        data:vObj,
                        file:$files[0]
                    }).progress(function(evt){
                        console.log('percent: '+parseInt(100.0*evt.loaded/evt.total));
                        $scope.videoStatuses[vid] = 'uploading';

                    }).success(function(data,status,headers,config){
                        console.log(data);
                        $scope.videoStatuses[vid] = 'success';
                    }).error(function(data,status,headers,config) {
//                        console.log('error: '+data);
                        console.log('error');
                        $scope.videoStatuses[vid] ='error';
                    });
                }
            }
        };
    });