angular.module('vmComponents', [] )
    .directive('videoElement', function () {
        return {
            restrict:'E',

            scope:{
                videoName:'@names'
            },
            templateUrl: "/partials/videoelement",
            link: function (scope, elem, attrs) {
//                console.log(scope);
//                console.log(elem);
//                console.log(attrs);
            },
            controller: function ($scope) {
//                console.log($scope);


            }

        };
    });