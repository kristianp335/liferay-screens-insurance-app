## Insurance Demo

This is a brief summary of how to use and customize this demo and learn how the application works.

# Preparing the server

In order to use this demo you need a liferay portal version >= 7.0 (CE or EE). There are two plugins that will be mandatory for this demo to work:

- Push notification plugin version >=2.2.1: [CE](https://web.liferay.com/marketplace/-/mp/application/48439053) and [EE](https://web.liferay.com/marketplace/-/mp/application/48438926)
- Liferay screens plugin version >=3.0: [CE](https://web.liferay.com/marketplace/-/mp/application/54365664) and [EE](https://web.liferay.com/marketplace/-/mp/application/54369726)

In order to check that the plugins are installed correctly, go to `<url-of-your-server>/api/jsonws`. In the dropdown you have to see screens and pushnotifications sections.

After installing these plugins you have to import [this LAR](./IncidencesDefinition.lar) that contains the data definition that represents an incidence. If everything goes well you will se a new data definition called `Incidence`. 


#Preparing the app


## Server values

All the values that you need from the liferay server are in the [server_context.xml](app/src/main/res/values/server_context.xml) file.
You can find more information [here](https://dev.liferay.com/develop/tutorials/-/knowledge_base/6-2/preparing-android-projects-for-liferay-screens#configuring-communication-with-liferay)

The values it contains are detailed here:

- **liferay_server**: the url of your liferay instance (**NOTE**: If you are using a local installation http://localhost:8080 will be http://10.0.2.2:8080 for the android emulator)
- **liferay\_portal_version**: version of the liferay portal. 70 or 62
- **liferay\_company_id**: instance id of your portal 
- **liferay\_group_id**: id of your site
- **recordsetId**: id of the dynamic data list you have to create for incidents
- **structureId**: id of the data definition that represents an incident
- **default\_user, default_password**: your liferay portal credentials
- **sender_id**: used for push notification. More information about how to configure it [here](https://github.com/liferay-mobile/liferay-push-android/blob/master/README.md)
- **news_url**: the news view is displayed using a WebScreenlet this is the url  that will be load in it.
- **contact_url**: the contact view is displayed using a WebScreenlet this is the url  that will be load in it.
- **latitude, longitude**: the location that will be the center of the map when submitting a new incidence.


## App customization

All the colors of the application are located in the [colors.xml](app/src/main/res/values/colors.xml) file

The styles of the application are defined in [styles.xml](app/src/main/res/values/styles.xml). If you want to add/change a style you have to add it under the AppTheme tag. As you can see the theme inherits from the material_theme, this is a theme that the screens team created based on android material theme. You can take a look at the style [here](https://github.com/liferay/liferay-screens/blob/develop/android/viewsets/material/src/main/res/values/styles_material.xml)

## Some App important details

### Push notifications

Whenever the application receives a push notification, it will show a notification whose title and description are the strings defined in `strings.xml` with name `notification_title` and `notification_title` respectively.

If you click the push notification, MapActivity is show in the screen. This activity will show a map with a fake location, representing the location of the incidence, and a crave coming to that position.

In the `MapActivity.java` file you can customize the 'Speed' of the crave.

### Navigation view

Navigation view has a list of buttons to change the selected section. This list is defined in the `activity_main_drawer.xml` file. It also have a HeaderView (the view where you see the user information). This view is defined in `navigation_header_main.xml` and `NavigationHeader.java`




