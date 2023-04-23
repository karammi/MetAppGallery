# MetAppGallery

You can use this application to search for the Metropolitan Museum of Art (MET) and view its details.

## Color Palette
| Color             | Hex                                                                |
| ----------------- | ------------------------------------------------------------------ |
| Primary Color | ![#303F9F](http://via.placeholder.com/10/303F9F/303F9F) #303F9F |
| Primary Variant Color | ![#1A237E](http://via.placeholder.com/10/1A237E/1A237E) #1A237E |
| Secondary Color | ![#FFA61C](http://via.placeholder.com/10/FFA61C/FFA61C) #FFA61C |
| Secondary Variant Color | ![#FF8741](http://via.placeholder.com/10/FF8741/FF8741) #FF8741 |
| Surface Color | ![#FFFFFF](http://via.placeholder.com/10/FFFFFF/FFFFFF) #FFFFFF |


## Download APK
[Metropolitan Museum of Art App](https://github.com/karammi/MetAppGallery/raw/dev/app/release/app-release.apk)

## Demo
[device-2023-04-23-233812.webm](https://user-images.githubusercontent.com/18300610/233863404-585a7757-5199-47b3-884f-0ba48c2fb934.webm)


## Technical Topics

These are some of the technical topics of the application. Read more about them in the sections
below.

- Manual Dependency Injection
- Leveraged _javax.net.ssl.HttpsURLConnection_ for network requests
- Structured Concurrency
- Compose UiToolkit
- String Obfuscation
- Background Resource Usage optimization
- Automated Test (Ui Test, Unit Test)


## Manual Dependency Injection

Created all dependencies in the root composable and injected them manually to the features. The
alternative option was to use ***Hilt*** or ***Dagger*** or a ***Service locator*** which I didn't
find necessary in this project because it is not a large one.

## HttpsURLConnection

In this project, a library like ***Retrofit*** isn't used because it has lots of boilerplate which
is not logical for such a small project and a basic network connection.

## Structured Concurrency

Taking these flows in the application as an example:

- When user have searched for a name in the GallerySearch screen and before the data is fetched from
  the server(endPoint api), user changes the query.
- When user have navigated to the GalleryDetail screen and before the data is fetched from server, user
  navigates back. The application must cancel fetching data from network.

This is what I mean by the term ***Structured Concurrency***.
Handled such a case by using ***Kotlin Coroutines*** cancellation APIs.

## Compose UiToolkit
Building out the application with Compose Ui toolkit.
The Jetpack Compose UI toolkit provides a modern and efficient way to build user interfaces in Android applications.
By adding custom components, you can create reusable UI elements that can be easily used in multiple screens, 
improving code reUsability and maintainability.

## Automated Test

Developed unit tests for important functionalities. Note that if you want to run the tests with
coverage, make sure that you have configured your test coverage runner to use JaCoCo.
Used fake implementation of dataSource layers during 
