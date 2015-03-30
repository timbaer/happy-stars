(function(angular) {
  'use strict';
angular.module('scopeExample', [])
  .controller('GreetController', ['$scope', '$rootScope', function($scope, $rootScope) {
    $scope.name = 'World';
    $rootScope.department = 'Angular';
  }])
})(window.angular);