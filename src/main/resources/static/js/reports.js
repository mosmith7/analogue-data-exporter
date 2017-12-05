var app = angular.module('angular-reports', ["ui.bootstrap"]);

app.factory('siteService', function($http) {

    var getSites = function() {
        return $http.get('/sites').then(function (response) {
            return response.data;
        });
    };

    return {
        getSites:getSites
    }

});

app.controller('parametersController', function($scope, $http, siteService) {

//        $scope.sites = sitesService($http);

//        $scope.channels = channelsService($http, 1);

    siteService.getSites().then(function(data) {
        $scope.sites = data
    });


//        $scope.channels = $http({
//          method: "GET",
//          url: '/channels?channel=%QUERY&siteId='+siteId,
//        })

    $scope.reports = [];
    $scope.areGenerated = []
    $http.get("/reports/all").then(function (reports) {
        $scope.reports = reports;
        var ids = [];
        for (var i=0; i < reports.length; i++) {
            ids[i] = reports[i].id;
        }
        $http.post('/reports/generated', ids).then(function (areGenerated) {
            for (var i=0; i < reports.length; i++) {
                $scope.areGenerated[i] = areGenerated[i];
            }
        }, function (error) {
            console.log("Error retrieving whether csv report was generated");
        });
    }, function (error) {
        console.log("Error retrieving reports");
    });


    $scope.isCSVGenerated = function (report) {
        console.log("report: " + report);
        var id = report.id
        console.log("CSV for id " + id + " generated: " + $scope.areGenerated[id]);
        return $scope.areGenerated[id];
    }

    $scope.submitParameters = function() {
        console.log("Parameters selected: Site: " + $scope.site + ", Channel: "+ $scope.channel+", from: "+$scope.from+", to: "+$scope.to);
    }

});