import * as angular from 'angular';

import {ParametersController} from "./controller/ParametersController";

// Angular imports. Warning: Typescript compiler doesn't know about angular so won't complain if these are missing!
import "angular-route";
import "angular-ui-router";
import {ReportController} from "./controller/ReportController";
import {ParametersService} from "./service/ParametersService";
import {ReportsService} from "./service/ReportsService";
import {URLService} from "./service/URLService";

const module = angular.module('angular-reports',
    [
        'ngRoute',
        'ui.router'
    ]);

// module.config(['$stateProvider', '$urlRouterProvider',
//     function($stateProvider:angular.ui.IStateProvider, $urlRouterProvider:angular.ui.IUrlRouterProvider) {
//     $stateProvider
//         .state('app', {
//             url: '/',
//             views: {
//                 'header': {
//                     templateUrl: 'views/header.html'
//                 },
//                 'content': {
//                     templateUrl: 'views/home.html'
//                 },
//                 'footer': {
//                     templateUrl: 'views/footer.html'
//                 }
//             }
//
//         })
//         .state('app.reports', {
//             url: 'reports',
//             views: {
//                 'content@': {
//                     templateUrl: 'views/reports.html',
//                     controller: 'ParametersController',
//                     controllerAs: 'ctrl'
//                 }
//             }
//         })
//         .state('app.reports_old', {
//             url: 'reports/old',
//             views: {
//                 'content@': {
//                     templateUrl: 'views/reports-old.html'
//                 }
//             }
//         })
//     ;
//     $urlRouterProvider.otherwise('/');
//
//     console.log("Angular routing should be set up");
// }]);

module.config(['$routeProvider',
    function($routeProvider:ng.route.IRouteProvider) {
        $routeProvider
            .when('/', {
                templateUrl: '../views/home.html'
            })
            .when('/report', {
                templateUrl: '../views/report.html',
                controller: ReportController,
                controllerAs: 'ctrl'
            })
            .when('/reports', {
                templateUrl: '../views/reports.html',
                controller: ParametersController,
                controllerAs: 'ctrl'
            })
            .when('/reports/old', {
                templateUrl: '../views/reports-old.html'
            })
            .otherwise({
                redirectTo: '/'
            });

        console.log("Angular routing should be set up");
    }]);

// module.controller('parametersController', ParametersController);
module.service('parametersService', ParametersService);
module.service('reportsService', ReportsService);
module.service('urlService', URLService);