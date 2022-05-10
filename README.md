# Simple Photo Gallery App

Created by Charles Li and Max Sun. Original repo hosted on [BitBucket](https://bitbucket.org/ms2814/photos-55.git) and pushed to GitHub on 5/10/22

Photo gallery desktop application built in Eclipse IDE and written in Java for backend and JavaFX for GUI.


### Features

- Supports multiple users with login authentification
- Creating and managing Photo Albums
- Importing and Tagging photos
- Viewing Photos

### Application Configuration

Login as `stock` to gain access to the *stock* photos user, which comes with an album full of sample images.

Users can be created by logging in as `admin`. The *admin* portal enables management of users. Note: deleting a user will remove all of their photo albums from the application.

### Technical details

User information and their associated photo albums are stored and accessed locally via data serialization. 
