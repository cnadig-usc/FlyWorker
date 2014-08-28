angular.module('vmComponents', [] )
    .directive('videoElement', function () {
        return {
            restrict:'E',

            scope:{
                videoName:'@name'
            },
            templateUrl: "/partials/videoelement",
            link: function (scope, elem, attrs) {
//                console.log(scope);
//                console.log(elem);
//                console.log(attrs);
            },
            controller: function ($scope,$upload) {
                $scope.openVideoUploader = function () {
                    console.log($scope.videoName);
                    document.getElementById($scope.videoName).click();
                }

                $scope.onFileSelect = function($files) {
                    console.log($files);
                }
            }

        };
    });