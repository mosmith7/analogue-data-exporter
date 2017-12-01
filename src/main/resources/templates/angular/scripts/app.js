angular.module('analogeReports')
.config(function($stateProvier, $urlRouterProvider) {
  $stateProvier.state('app', {
    url:'/angular/',
    views: {
      'header': {
        templateUrl: 'views/header.html'
      },
      'content': {
        templateUrl: 'views/home.html'
      },
      'footer': {
        templateUrl: 'views/footer.html'
      }
    }
  })
    .state('app.overview', {
      url: 'reports',
      views: {
        'content@' : {
          templateUrl: 'views/report-overview.html'
        }
      }
    })
})
