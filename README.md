# [Demo Video](https://youtu.be/yblZoaDaulg)

# Project Vision

For any users that want to have a calculator that also acts as a unit conversion tool, GuruCalc is the application made just for them. 
Users can peform the usual functions that any calculator can do. It includes the four basic operations (addition, subtraction, multiplication, and division), along
with parantheses for nested expressions, and also includes the squared and square root operators. Users can also press the newline button to save their past calculations.
It also has a conversion page, where users can select four different units (length, currency, temperature, and weight) to convert numbers representing one of those 
four units. Users will be able to convert meters to feet, US dollars to Yuan, Fahrenheit to Celsius, Kilograms to ounces, and many more units. 

# Figma Design

<img width="464" height="487" alt="image" src="https://github.com/user-attachments/assets/658e6033-f05b-4a66-9c28-ee54d050fbc4" />

[Figma Project](https://www.figma.com/design/tukXiS0nkcuXNRK6cJD4gC/GuruCalc?node-id=2-53&t=ANS0MhKGoLpqSPsm-1)

# Android and Jetpack Compose Features

Here are a list of Android and Jetpack Compose Features that make this app possible:
* Kotlin-first Android platform helpers (Core KTX).
* Declarative UI with Jetpack Compose (Composable functions, Modifiers).
* State handling for Composables (View Model).
* UI components and theming (Material 3).
* Lazy srolling lists to handle navigating history (LazyColumn).
* Navigation between pages using Navigation compose (NavHost, NavController).
* Developer tooling and Previews.

# Dependencies 

The app only needs an Android phone in order to function. It does not need any other extraciricular features such as a microphone, speakers, camera, internet connection, etc..
The Android SDK numbers show that the smallest Android version it can run on is 24, but it will target and try to compile on version 36.

# Above and Beyond

GuruCalc also works in landscape mode. In that mode, the calculator screen will show the inputs, outputs, and history on the left side and the buttons on the right side. 
The conversion page is formatted the same way.
