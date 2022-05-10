# Simple Photo Gallery App

Created by Charles Li and Max Sun. Original repo hosted on [BitBucket](https://bitbucket.org/ms2814/photos-55.git) and pushed to GitHub on 5/10/22

Photo gallery desktop application built in Eclipse IDE and written in Java for backend and JavaFX for GUI.


<img src="https://user-images.githubusercontent.com/50348516/167721807-88e7b18a-2143-42da-aada-35900345fc8a.png" width=400> <img src="https://user-images.githubusercontent.com/50348516/167721887-d9a1c7ea-022f-438a-8793-434b24fad62f.png" width=400>


### Features

- Supports multiple users with login authentification
- Creating and managing Photos and Photo Albums
- Importing, Captioning, and Tagging Photos
- Filtering and Viewing Photos

### Application Configuration

Login as `stock` to gain access to the *stock* photos user, which comes with an album full of sample images.

Users can be created by logging in as `admin`. The *admin* portal enables management of users. Note: deleting a user will remove all of their photo albums from the application.

### Technical details

User information and their associated photo albums are stored and accessed locally via data serialization. 

## How to Run

### Eclipse IDE
1. Install JavaFX and add it to your PATH
2. Link JavaFX package path to the IDE
3. Import the project and expand the project dropdown.
4. Select under */src* **Photos55.App** and run as Java application
5. Play around with the App :)

### Command Line
1. Install JavaFX and add it to your PATH, note it as *PATH2JavaFX*
3. Open */src* directory and compile with 

  `javac --module-path PATH2JavaFX/lib --add-modules javafx.controls Photos55/App/*.java`
  
  `javac --module-path PATH2JavaFX/lib --add-modules javafx.controls Photos55/View/*.java`
  
4. Run with

  `java --module-path PATH2JavaFX/lib --add-modules javafx.controls Photos55.app.Photos55.App`
  
  
Note: More modules might need to be included besides just *javafx.controls*. But, probably only a few of the common ones in the JavaFX package
