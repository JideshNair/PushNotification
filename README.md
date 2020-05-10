# Push Templates SDK by CleverTap

The Video Notification Templates SDK helps you engage with your users using Video based  Push Notification templates built specifically to work with CleverTap

# Table of contents

- [Installation](#installation)
- [Template Types](#template-types)
- [Template Keys](#template-keys)
- [Usage](#usage)
- [Sample App](#sample-app)
- [Contributing](#contributing)
- [License](#license)

# Installation

[(Back to top)](#table-of-contents)

### Out of the box

1. Add the dependencies to the `build.gradle`
//TODO add code

2. Add the Service to your `AndroidManifest.xml`
//TODO add code

3. Add the Receiver to your `AndroidManifest.xml`
//TODO add code


### Custom Handling

1. Add the dependencies to the `build.gradle`
//TODO add code

2. Add the Receiver to your `AndroidManifest.xml`
//TODO add code

4. Add the notification builder class MyFirebaseMessagingService where the notification is build and the data from the notifications are custom handled .

# Template Types

[(Back to top)](#table-of-contents)

## Basic Notification 

Basic Template is the basic push notification received on apps.

![Basic Unexpanded](https://github.com/darshanclevertap/PushTemplates/blob/readme-images/screens/basic%20unexpanded.png)

(When Expanded)<br/><br/>
![Basic Expanded](https://github.com/darshanclevertap/PushTemplates/blob/readme-images/screens/basic%20expanded.png)


## Notification video 

 Notification video being played in a dialog
<br/><b>(exoplayer is used for playing the video)<b><br/><br/>
![Auto Carousel](https://github.com/darshanclevertap/PushTemplates/blob/readme-images/screens/autocarousel.gif)



# Template Keys

[(Back to top)](#table-of-contents)

## Video Notification

wzrk_pn

N/A

If present, this notification is sent from CleverTap.

wzrk_id

string

Open rate tracking ID (can be empty or it might not be present).

wzrk_bp

string

If present, the value will be a URL of an image, that needs to be displayed in the notification.

wzrk_sound

boolean or string

If present, it signifies that the default or custom Android notification sound must be played.

nt

string

Notification Title, if absent or empty, fallback to the app name.

nm

string

Notification Body, if absent or empty, ignore this notification.

wzrk_dl

string

If present, this is a deep link that must be followed at the time of notification open.

wzrk_d

N/A

If present, ignore this notification.

ico

string

If present and not empty, it contains the URL of an image, that must be used as the small notification icon.

wzrk_pivot

string

If present and not empty, it contains the URL of an image, it signifies the type of variant in A/B campaigns.

wzrk_rnv

string

If present and not empty, it will raise Push Impressions event

wzrk_nms

string

If present and not empty, it contains the summary text to be displayed along with the image.

wzrk_st

string

If present and not empty, it contains the subtitle text which is displayed next to the App name.

wzrk_clr

string

If present and not empty, it contains the Hex value of the colour to be applied on the small icon (and app name for Android versions below Pie)

videourl(need to create custom key,value pair in notification of Clevertap dashboard)

String

The link to the Video to be showed during the notification click goes here
 

  
  ### NOTE
  (*) - Mandatory
  
# Usage

[(Back to top)](#table-of-contents)

Using the above mentioned keys is very simple. While creating a Push Notification campaign on CleverTap, just follow the steps below -

1. On the "WHAT" section pass some staple values in the "title" and "message" fields (NOTE: These will be ignored)

![Basic](https://github.com/darshanclevertap/PushTemplates/blob/readme-images/screens/basic.png)

2. Click on "Advanced" and then click on "Add pair" to add the above key(videourl)

![KVs](https://github.com/darshanclevertap/PushTemplates/blob/readme-images/screens/kv.png)


4. Send a test push and schedule!

# Sample App

[(Back to top)](#table-of-contents)

The app is a sample app with the implementation .

# Contributing

[(Back to top)](#table-of-contents)

Your contributions are always welcome! Please have a look at the [contribution guidelines](CONTRIBUTING.md) first. :tada:

# License

[(Back to top)](#table-of-contents)


The MIT License (MIT) 2020. Please have a look at the [LICENSE.md](LICENSE.md) for more details.
