Android TMDB with Clean Architecture concepts
------
[![Build Status](https://travis-ci.org/bresan/android_tmdb_clean_architecture.svg?branch=master)](https://travis-ci.org/bresan/android_tmdb_clean_architecture) [![Known Vulnerabilities](https://snyk.io/test/github/bresan/android_tmdb_clean_architecture/badge.svg)](https://snyk.io/test/github/bresan/android_tmdb_clean_architecture) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/6e921f05223141b1985cfdf1671c3d32)](https://www.codacy.com/app/bresan/android_tmdb_clean_architecture?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=bresan/android_tmdb_clean_architecture&amp;utm_campaign=Badge_Grade)
[![codecov](https://codecov.io/gh/bresan/android_tmdb_clean_architecture/branch/master/graph/badge.svg)](https://codecov.io/gh/bresan/android_tmdb_clean_architecture)


#### What is this?
Just a simple project to try new stuff on Android showing some movies fetched from the TMDB (The Movie Database) API.

The purpose of this project is to show a real-world application example, from using modern
architecture decisions, to having a good code coverage, as well as providing integrations with
many third-party services (Travis CI, Firebase Test Lab, Google Play, Crashlytics, Slack, etc)

Feel free to use it just for learning or for your next Android application - I would be pretty honored!

- **Kotlin**, because no one anymore likes to write huge classes for simple objects, right?
- **Clean Architecture Concepts**
- **MVP (Model-View-Presenter)**
- **RxJava**, mostly for the whole reactive communication between the modules
- **Dagger 2** for Dependency Injection purposes
- **Retrofit** for our HTTP requests
- **Room** for our local persistence (if you want to check some of it using SQLite, you can check the commit history, there is also a previous implementation using it)
- **Mockito** for mock some classes on our tests
- **Robolectric** for our persistence layer (named as cache module) run our unit tests
- **Flavors & Build Types**, so we can have our project generating many different artifacts, with it's own API endpoints, icons, etc
    - **Flavors**: Pro and Demo
    - **Build Types**: Debug, QA, Release
- **Espresso**, for our instrumentation tests
- **Travis CI**, for making a full build of our project, as well as run all tests (unit & instrumentation), once we have any push into the repo.
- **Firebase Test Lab**, so we can have a place to run all our instrumentation tests properly.
- **CodeCov**, for generating our code coverage in the project
- **Fabric/Crashlytics** for providing statistics of our application, as well to provide an excellent Crash Report and release for QA team
- **Slack Notification**, notifies your slack team if the build is done successfully - and also if it failed.
- **Google Play Publisher**, for pushing your release artifacts directly into Google Play (beta channel)


In case of any questions, feel free to open an issue, I will be glad to help.

### Requirements

Android Studio

JDK 1.8

Android SDK


### Project Architecture

This project's architecture was heavily inspired by Bufferoos Clean Architecture (you can check it out [here](https://github.com/bufferapp/android-clean-architecture-boilerplate)). The diagram for it is shown below:

![Project Architecture](https://i.imgur.com/gqyvPRH.png "Project Architecture")

### Continuous Integration Flow

![Continuous Integration](https://i.imgur.com/QEjsqts.png "Continuous Integration")

#### Demo

<p align="center">
<img src="https://media.giphy.com/media/l2QDTDhr4Apsw94yY/giphy.gif" alt="Project Demo"/>
</p>


#### Future improvements

- [x] Add Room;
- [x] Implementation of movie details section;
- [ ] Architecture Components;
- [ ] Auto-bump version;
- [ ] Generate changelog based on the previous commits;
- [ ] Open Movie in Netflix app, in case it's installed;

#### License

All code licensed under the [MIT License](http://www.opensource.org/licenses/mit-license.php). You are free to do whatever you want with this piece of code. Check it out the LICENSE.md file for more info.
