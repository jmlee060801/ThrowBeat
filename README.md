# ThrowBeat — P3 Second Prototype

## Overview
Java Swing music-queue prototype. **No audio playback**: “Play Next” advances the displayed song state. Songs use a custom singly linked FIFO queue.

## Features
- **Custom Mood Tagging:** select a library song, select Happy/Sad/Chill/Workout, click **Save Song Mood**. The Song object changes in the model.
- **Mood-Based Smart Queue:** select a mood, click **Generate Smart Queue** to enqueue matching songs (preserves existing FIFO items).
- **Like / Unlike ♥:** select a library song and toggle its liked flag; the library displays a heart for liked songs. Likes do not affect shuffle or playback priority.
- **Shuffle Queue:** randomly rearranges *waiting songs only*, preserving the number of songs and duplicates; it does not change the current song. Random order is not guaranteed to differ each time.
- **Repeat One:** after a song is playing, enabling Repeat One makes **Play Next** return that same song without consuming waiting songs. If no song is playing, Play Next can start the first waiting song. Turn Repeat One off to advance again.
- **Add Selected Song / Play Next:** standard FIFO queue controls.

## Run
Import the folder containing `pom.xml` in Eclipse as **Existing Maven Projects**. Run `ThrowBeatApp` as a Java Application. To test: run `mvn test` (or Eclipse **Run As → Maven test**). To test model independently, run `ThrowBeatModelDriver`.

## Reflection
**How the design absorbed the change:** The initial D3 plan named Skip and Requeue. For this checkpoint we instead implemented user-editable Mood tags, mood-based queue generation, likes, shuffle, and repeat-one. The new features changed `Song`, `SongQueue`, `ThrowBeatModel`, `ThrowBeatController`, and `ThrowBeatView`, plus new tests. The previous D3 prediction about modifying Model, Controller, View and tests was broadly correct; modifying `Song` for editable mood and liked state was an additional change. `ThrowBeatApp` remained unchanged.

**Model/view boundary:** The View collects selections and displays model snapshots; Controller invokes model methods. The View does not manipulate the Queue directly. `Song.toString()` supplies labels to Swing, so presentation is partly coupled to the Song class; a dedicated renderer would improve separation.

**Changes from D3 plan:** Skip and Requeue was replaced with richer user-driven features; the queue now supports `shuffle(Random)`, while the model supports mood edits, likes and repeat-one. New JUnit tests cover these paths. The current design uses fixed demo songs and in-memory state.

**What does not work yet:** No actual audio playback, imported songs, saved likes/moods across restarts, or true automatic audio-based mood detection. Repeat-one is a simulated playback-state operation, not audio looping. GUI automation tests are not included. Verify JUnit tests in the team's Eclipse/Maven environment before submission.

## Next Steps
1. Run all JUnit tests and fix any issues on the submission machines; manually check GUI actions and bad inputs. **Owner: team to assign.**
2. Decide whether to add actual audio playback and persistent song metadata in the final build. **Owner: team to assign.**
3. Improve separation between model data and Swing labels and polish the GUI. **Owner: team to assign.**
4. Confirm stretch goals: **committing** to user mood tags, likes, shuffle and repeat-one; **dropping for now** AI audio mood recognition and imported audio playback unless time permits.

**Three-minute demo:** Start app; select a song and set its mood; like it; generate a mood-based queue; add a few more songs; shuffle the waiting queue; press Play Next; enable Repeat One and show Play Next stays on the same song; disable Repeat One and advance; show empty-queue handling; mention model tests.

## Attribution (team must verify before submitting)
These are **provisional responsibility assignments, not verified claims of authorship**. Replace with actual wrote/revised/reviewed/tested contributions for each person.

| File | Provisional responsibility | Actual work (verify) |
|---|---|---|
| `Song.java` | Shresta | Review changes to editable mood and liked state |
| `Mood.java` | Shresta | Review enum |
| `SongQueue.java` | Shresta | Review shuffle implementation |
| `ThrowBeatModel.java` | Shresta | Review feature logic |
| `ThrowBeatModelDriver.java` | Shresta / Seth | Run and review driver |
| `ThrowBeatView.java` | Isaac | Review and test Swing UI |
| `ThrowBeatController.java` | Seth | Review and test event wiring |
| `ThrowBeatApp.java` | Seth | Run and review app startup |
| `SongTest.java` | Shresta | Run and review tests |
| `SongQueueTest.java` | Shresta | Run and review tests |
| `MoodTest.java` | Shresta | Run and review tests |
| `ThrowBeatModelTest.java` | Shresta | Run and review tests |
| `ThrowBeatFeaturesTest.java` | Team to assign | Run, review and extend feature tests |

**GenAI note:** ChatGPT generated draft Java code, tests, and README text for the Second Prototype. Team members must inspect, run, debug, and take responsibility for the submitted files; the table above must reflect their actual contributions.

### YouTube Music-inspired GUI update (navigation and clear queue)
- Home, Your Library, Liked Tracks, and Play Queue are now real navigation buttons backed by Swing CardLayout.
- The Mood dropdown has an explicit dark popup renderer and white text for readability.
- Clear Queue removes all waiting tracks via View -> Controller -> Model -> SongQueue; it does not erase the song library or stop/reset the currently selected song.
- New `ClearQueueTest.java` checks clearing populated/empty queues and preserving current song. Run all JUnit tests in Eclipse/Maven before submission.
- **Attribution update required:** record the actual contributors who reviewed, tested, or revised these changes in the attribution table; the GUI and test changes were GenAI-assisted.


## UI visibility fixes (Second Prototype)
- After **Add to queue**, the UI automatically switches to **Play Queue** and displays a green confirmation with the song title and waiting count.
- **Generate mood queue** also opens Play Queue and confirms the number of added songs.
- **Clear Queue** immediately displays an empty queue and confirmation.
- Mood selection uses a dark Swing popup menu instead of the OS-native combo box to avoid unreadable white backgrounds on macOS.
- Changes are limited to `ThrowBeatView.java` and `ThrowBeatController.java`; the queue/model behavior is unchanged.


## Optional user-assigned moods (latest update)

All four sample songs start **without a mood**. Users can select a song in Your Library, choose Happy/Sad/Chill/Workout, and press **Save mood**. Only tagged songs are included by **Generate mood queue**. **Remove mood** clears a song's tag. Untagged songs can still be liked and manually queued. The change updates Song, ThrowBeatModel, ThrowBeatController, ThrowBeatView, SongTest, ThrowBeatFeaturesTest, and adds OptionalMoodTest. Verify actual team attribution before submission.
