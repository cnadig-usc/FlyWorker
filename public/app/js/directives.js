angular.module('vmComponents', [] )
    .directive('videoElement', function () {
        return {
            restrict:'E',

            scope:{
                videoName:'@name',
                no_of_cams:'@noofcams'
            },
            templateUrl: "/partials/videoelement",
            link: function (scope, elem, attrs) {

            },
            controller: function ($scope,$upload) {
                $scope.vidstatus = 'novideo';

                $scope.videoSetNames = []
                for (var i =0; i<$scope.no_of_cams; i++) {
                    $scope.videoSetNames.push($scope.videoName+'-cam-'+i.toString());
                }

                $scope.openVideoUploader = function (vid) {
                    console.log(vid);
                    console.log($scope.no_of_cams);
//                    document.getElementById(vid).click();
                }
                $scope.onFileSelect = function($files,vid) {

                    console.log($files);
                    console.log(vid);

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