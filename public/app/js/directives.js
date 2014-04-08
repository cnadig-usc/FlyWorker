angular.module('vmComponents', [] )
    .directive('videoElement', function () {
        return {
            restrict:'E',

            scope:{
                videoName:'=name'
            },
            templateUrl: "/partials/videoelement"

        }
    });