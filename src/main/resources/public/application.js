angular.module('happy', ['ngResource', 'ngSanitize'])
    .controller('happyController', function ($scope, $resource, $interval) {

        $scope.name = 'World';

        var versionResource = $resource('version');
        var starsResource = $resource('stars');
        var starResource = $resource('stars/:id');


        // STAR ALIVE CHECK
        $scope.alive = false;

        $scope.checkAlive = function () {
            var promise = versionResource.get().$promise;
            promise.then(function (infos) {
                console.info('It is alive!');
                $scope.alive = true;
            });
            promise.catch(function (error) {
                console.error('It is dead :(');
                console.error(error);
                $scope.alive = false;
            });
        };

        // execute checkALive every 30 seconds
        $interval($scope.checkAlive, 30 * 1000);
        // END ALIVE CHECK

        // START: Stars!
        $scope.stars = {};
        $scope.getStars = function () {
            var promise = starsResource.get().$promise;
            promise.then(function (result) {
                console.info(result);
                $scope.stars = result;
            });
            promise.catch(function () {
                console.error(error);
                $scope.stars = {};
            });
        };

        $scope.saveStar = function (newStar) {
            var starToSave = {id: 1, name: newStar, color: 'BLUE'};
            console.info('Saving star:');
            console.info(starToSave);

            var promise = starsResource.save(starToSave).$promise;
            promise.then(function (result) {
                console.info('Star saved!');
                $scope.getStars();
            });
            promise.catch(function () {
                console.error('Star not saved :(');
                console.error(error);
            });
        };

        $scope.deleteStar = function (id) {
            console.info('Deleting star: ' + id);

            var promise = starResource.delete({id: id}).$promise;
            promise.then(function (result) {
                console.info('Star deleted!');
                $scope.getStars();
            });
            promise.catch(function () {
                console.error('Star not deleted :(');
                console.error(error);
            });
        };


        // END: Stars!

        // INIT!
        $scope.checkAlive();
        $scope.getStars();
    }
);