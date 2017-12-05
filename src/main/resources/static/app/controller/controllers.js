var module = angular.module('angular-reports', []);

module.controller('ParameterController', ['$scope', '$http', function($scope, $http) {

    $http({
        url: "/sites",
        method: "GET"
    }).then(function success(r) {
        console.log("Sites: " + r);
        console.log("Specific site: " + r.name)
        $scope.sites = r;
    }, function error(e) {
        console.log("Error: " + e)
    })

}])