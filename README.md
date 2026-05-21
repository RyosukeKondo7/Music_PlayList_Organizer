# Music PlayList Organizer

## Desktop Application for Managing Music Playback Queues

**Phase 0**
Task 2:
What will the application do?
- Add music to the playlist you selected
- Removing music from playlist if the music is in the playlist
- Sort songs by singer

Who will use it?
- This app is intended for people who regularly listen to music and want to plan the music playlist.

Why is this project of interest to you?
- I often listen to music while studying and exercising, and I like organizing playlists to match my mood.

Task 3:
- As a user, I want to be able to add a music to my playlist.
- As a user, I want to be able to view the list of tracks in my music queue.
- As a user, I want to be able to reset my playlist.
- As a user, I want to be able to select by singer and show the list of music

**Phase 2**
- As a user, I want to be able to save my playlist to file.
- As a user, I want to be able to load my saved playlist.



**Phase 3**

# Instructions for End User
- You can view the songs and chart by category that have been added to the playlist by 
  pressing "View" on the top bar or "View Playlist" in the Home 
- You can add music to the play list in "Add" view. Type in song and singer, and selecte music category,
  then  press "Add to playlist" button.
- You can remove a music from playlist in "View" tab. Tap "Show all" button first, 
  and then select a music you want to remove, then press "Remove selected" button.
- You can see the chart by music category in "View" tab. 
- You can choose the option to load from existing playlist, when this app started.
- You can choose to save or not when this app close.



**Phase 4: Task 2**
Fri Mar 27 00:36:35 PDT 2026
Added music: Starboy (The Weekend)

Fri Mar 27 00:36:35 PDT 2026
Added music: Sunflower (Post Malone)

Fri Mar 27 00:36:35 PDT 2026
Added music: Lovin On Me (Jack Harloe)

Fri Mar 27 00:37:02 PDT 2026
Added music: Circles (Post Malone)

Fri Mar 27 00:37:24 PDT 2026
Added music: I Had Some Help (Post Malone)

Fri Mar 27 00:37:37 PDT 2026
Filtered playlist by: Post Malone

Fri Mar 27 00:37:55 PDT 2026
Filtered playlist by: The Weekend

Fri Mar 27 00:38:00 PDT 2026
Removed I Had Some Help (Post Malone)

Fri Mar 27 00:38:00 PDT 2026
Filtered playlist by: The Weekend


**Phase 4: Task 3**
If I had more time to work on this project, I'd like to refactor the design by extracting the overlapping methods in `PlayListApp` and
`PlayListAppUI` and handling them in a separate class. Currently, both of these class have `PlayList`, `JsonReader`, and `JsonWriter`. This results in partial overlap of application logic between the console and GUI versions. This new class will manage the current playlist and handle add, remove, filter, save, and load operations. This simplifies the UI class and changes related to playlist behavior and persistence can be done in one place.
