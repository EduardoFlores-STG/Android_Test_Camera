# Android_Test_Camera
Simple example of an Android application using the phone's back camera and a custom overlay without Intents.

This application does not use an Intent to call whatever is the default camera application already exists in the device,
but instead it creates a new hardware camera object.
The app also creates the preview of what the camera sees, rotates the image to display correctly on the device,
and places a layout on top or around the preview.

Finally, the application takes an image and saves it locally but not within the "camera reel" of the device,
so the image stays only available within our application and not any other system or third party applications.

Project created on Android Studio.
