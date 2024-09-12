# GKI Salatiga+ Changelog

## Next RC Update

### English

- New: (Back-End) Added the developer menu
- Improved: Changed the logo to reflect the "new look" of GKI Salatiga
- Fix: Fixed notification cannot be removed and does not call any action upon click
- Fix: Now notifications show up at an exact time of the day; fixed random notification appearance

### Indonesian

- Baru: (Back-End) Penambahan menu pengembang
- Improvisasi: Perubahan logo untuk merefleksikan GKI Salatiga yang lebih terkini
- Perbaikan: Memperbaiki notifikasi tidak hilang dan tidak memunculkan apa-apa ketika diklik
- Perbaikan: Memperbaiki notifikasi muncul pada waktu yang random

## v0.4.4-rc 2024.09.02 (27)

- Info: This release includes code cleaning of some obsolete code blocks, including the `ScreenMinistry` class
- Improved: Added the gallery banner
- Improved: Adjusted paddings in the "About Church" section
- Improved: Now the YouTube player seamlessly transitions between normal and fullscreen player
- Fix: Fixed app update notification gets displayed even when the app is already up-to-date
- Fix: Fixed the YouTube video player seekbar gets trimmed when the device has bottom/navigation bar

## v0.4.3-beta 2024.08.29 (26)

- Release Note: General back-end update before advancing to the closed testing phase

- New: Added snack bar for offline notice and bottom sheet dialog for new app update notice
- New: (Back-End) Added the ability to manually refresh data
- New: (Back-End) Added internet connectivity checker
- New: (Back-End) Added new application update detector

## v0.4.2-beta 2024.08.27 (25)

- Release Note: General bug fixes and feature update without APK compilation

- New: Added English translation for the `strings.xml` file
- New: Added offertory transfer code list
- New: Added offertory bank accoount logo
- Fix: `ScreenMain` leaves a significant white gap between the content and the top banner #42
- Fix: White color on banner image when the carousel data has not been downloaded #46
- Fix: App crashes after 5 seconds since launch on Pixel 5 #44

## v0.4.1-beta 2024.08.25 (24)

- Release Note: General bug fixes and visual improvement

- Improved: Changed the banners on menus: "Warta", "Liturgi", "YKB", "Agenda", and "Forms" by @KimJoZer
- Improved: Changed dependency from "Compose RichText" to "Compose Markdown" for more advanced functionalities
- Improved: The "profile info" static content now has sub-folders and nested content support
- Improved: For performance optimalization, the "profile info" static data now uses JSON files instead of zip archives
- Fix: Fixed carousel page always resetting to page no. 1 after clicking a carousel content/poster
- Fix: Email app not showing the destination email address upon clicking a "mailto" button in GKI Salatiga+
- Fix: Gallery crashes and cannot open when the app is offline #37
- Fix: Performance issue due to continuous reading of JSON main data is now solved
- Fix: StaticMemoryLeak solved by using `LocalContext.current` implementation to obtain the current context in non-main classes

## v0.4.0-beta 2024.08.21 (23)

- New: Added privacy policy notice
- New: Added changelog, license information, open source library attributions, contributor list, and repo link in the "About" screen
- Improved: Adjusted the font size of forms, gallery, YKB devotion, church news, and liturgy menus
- Improved: Changed the image loading graph from "omon-omon" to "no internet connection"
- Fix: Fixed services fragment not displaying the first YouTube video in a playlist

## v0.3.2-beta 2024.08.19 (22)

- New: Added the "Media" screen that displays non-pinned GKI Salatiga YouTube playlists
- Improved: Added thumbnail aspect ratios in order to maintain layout size and position
- Improved: Locked orientation to portrait on non-fullscreen YouTube screens
- Improved: Improved the outlook and display of the video viewer and playlist display
- Improved: (Back-End) Added override debug settings to disable downloading static and carousel zip files

## v0.3.1-beta.2 2024.08.16 (21)

- Fix: Fixed "targeting S+ version 31 and above" error on HyperOS devices

## v0.3.0-beta 2024.08.16 (20)

- New: The event documentation gallery feature has now been introduced
- New: The scheduled app's notification is introduced
- New: Added OnBoot listener so that the app continues to display important notifications even after restarting the phone
- Fix: Fixed app not displaying cached images and static menus when going offline
- Fix: Fixed bottom bar item flickering when transitioning from one fragment to another in ScreenMain
- Fix: Temporary fix for full screen video player displaying white paddings
- Fix: BackHandler not responding to "back button press" on main screen

## v0.2.2-beta 2024.08.14 (19)

- New: Added QRIS code image to the "Offertory" menu
- Fix: Fixed app rotating even with the auto-rotate turned off
- Fix: Fixed banner carousel does not extract the latest zip file

## v0.2.1-alpha 2024.08.04 (18)

- New: Added new screens: agenda and offertory (persembahan)
- New: Added social media call-to-action (CTA) buttons in the FragmentAbout
- Improved: Added representative icons to main menu items

## v0.2.0-alpha 2024.08.03 (17)

- New: The main menu now has an elegant scrolling behavior
- Fix: Fixed YouTube player not resetting to "time = 0" when opening a new video

## v0.1.9-alpha 2024.08.02 (16)

- Info: The YouTube player now automatically pauses when the app is minimized to background
- New: The YouTube video viewer now has a fully functional full screen player
- Fix: Fixed current screen state not saved when the phone's orientation changes

## v0.1.8-alpha 2024.08.01 (15)

- Info: The fullscreen YouTube viewer is now being prototyped
- New: Added "clickable logo" in ScreenAbout
- New: Carousel banner now has three action types: YouTube video, internet article, and poster display
- Fix: Main menu fragments now remember scroll state across navigations
- Fix: Fixed church profile menus not showing when there is no static data update
- Fix: LazyVerticalGrid in FragmentHome is now replaced with mathematically calculated rows and columns in order to increase stability
- Fix: OutOfMemoryError when scaling large images on Xiaomi Mi-4c (By moving all images to drawable-nodpi. See: https://stackoverflow.com/a/77082456)

## v0.1.7-alpha 2024.07.31 (14)

- Info: The screen orientation is now locked to portrait mode
- New: Added internal preferences/settings which are persistently saved across launches
- New: Introduced the packed static data for zipping the "profile" menu
- Improved: ScreenVideoLive now has better visual appearance and scrollable description box

## v0.1.6-alpha 2024.07.30 (13)

- Info: The extended Jetpack Compose material icon pack is now enabled in Gradle
- New: Added transition animation (fade) between screens
- Fix: Changed WebView external icon to "OpenAsNew"
- Fix: Enabled zooming by pinching in WebView
- Fix: Element colors now follow main theme
- Fix: YouTube viewer throws NPE when pressing "back"

## v0.1.5-alpha 2024.07.27 (12)

- New: Added YouTube video description display
- New: "Show more videos" feature
- Improved: Smoothed out transitions between HorizontalPager pages in the main menu

## v0.1.4-alpha 2024.07.26 (11)

- New: The JSON database now autofetches online updates without requiring admin supervision

## v0.1.3-alpha 2024.07.23 (10)

- New: Added the internal HTML WebView for displaying custom HTML string data
- Improved: Updated the main menu carousel duration from 1 second to 2 second
- Improved: Replaced the dummy bottom nav bar icons with the appropriate icons
- Improved: In the "About Church" menu, each menu item card now has visually appealing background.
- Improved: Enabled APK compression and optimization
- Fix: YouTube ID grabber now mitigates additional query parameter being added into the URL
- Fix: Fixed the section title in the "Konten" menu overlapping the "show more" button

## v0.1.2-alpha 2024.07.23 (9)

- New: Added horizontal auto-scrolling carousel in the main menu
- Improved: Church news and liturgy are now highlighted in the main menu as the "primary items"
- Improved: Tinted the background color blue during splash screen to harmonize with the logo
- Improved: Merged live and pre-recorded YouTube videos into the "content" navigation menu

## v0.1.1-alpha 2024.07.21 (8)

- New: Added GKI Salatiga+ application logo
- Fix: Fixed bottom nav not scrolling to the intended horizontal pager page

## v0.1.0-alpha 2024.07.20 (7)

- Info: This version is major feature introduction pre-release
- New: Added "Warta Jemaat" PDF document viewer feature
- New: Added "Tata Ibadah" PDF document viewer feature
- New: Added "SaRen Pagi" morning devotional
- New: Added "Renungan YKB" daily devotion from Yayasan Komunikasi Bersama
- New: Added church forms menu

## v0.0.6-alpha 2024.07.20 (6)

- New: Added splash screen to the app
- New: Added the live YouTube video viewer
- Fix: Replaced AnimatedVisibility with HorizontalPager for the main menu fragment display
- Fix: Replaced NavHost-based navigation with custom-made GlobalSchema to improve code cleanliness
- Fix: Reduced the number of bottom navigation menu to 3 menus

## v0.0.5-alpha 2024.07.17 (5)

- New: Added link confirmation dialog for opening external social media links
- New: Added preliminary profile information screen and fragments

## v0.0.4-alpha 2024.07.17 (4)

- New: Added the preliminary layout of the bottom sheet
- Fix: Fixed padding on most YouTube video thumbnails
- Fix: Fixed padding on text-based card elements

## v0.0.3-alpha 2024.07.16 (3)

- New: Added the main screen menus: Home, Services, News, and Events
- New: Added features to display daily Bible verses and welcome banner
- New: Added church profile buttons

## v0.0.2-alpha 2024.07.16 (2)

- New: Added inter-fragment switching mechanism
- Fix: Fixed nested NavHost not working by substituting with AnimatedVisibility

## v0.0.1-alpha 2024.07.15 (1)

- New: Added bottom navigation and FAB
- New: Added top navigation
- New: Created the Composable navigation routing for screens and fragments
