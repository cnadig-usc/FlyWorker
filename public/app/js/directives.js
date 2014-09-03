angular.module('vmComponents', [] )
    .directive('videoElement', function () {
        return {
            restrict:'E',

            scope:{
                videoName:'@name'
            },
            templateUrl: "/partials/videoelement",
            link: function (scope, elem, attrs) {

            },
            controller: function ($scope,$upload) {
                $scope.vidstatus = 'novideo';
                $scope.openVideoUploader = function () {
                    console.log($scope.videoName);
                    document.getElementById($scope.videoName).click();
                }
                $scope.onFileSelect = function($files) {

                    console.log($files);

                    $scope.upload = $upload.upload({
                        url:'/videoUpload',
                        data:{test:'testData'},
                        file:$files[0]

                    }).progress(function(evt){
                        console.log('percent: '+parseInt(100.0*evt.loaded/evt.total));
                        $scope.vidstatus = 'uploading';

                    }).success(function(data,status,headers,config){
                        console.log(data);
                        $scope.vidstatus = 'success';
                    }).error(function(data,status,headers,config) {
//                        console.log('error: '+data);
                        console.log('error');
                        $scope.vidstatus = 'error';
                    });
                }
            }
        };
    });