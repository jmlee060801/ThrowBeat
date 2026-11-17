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

**Draft for team review — update to reflect what your team actually implemented and observed.**

### What changed from the specification?

The Deliverable 2 specification called for `Song`, a linked-node `SongQueue`, `ThrowBeatModel`, `ThrowBeatView`, `ThrowBeatController`, and `ThrowBeatApp`. We retained those classes and the public queue/model/controller method signatures. We added `ThrowBeatModelDriver` so the core logic can be exercised from the command line without launching a GUI. We also added JUnit test files for the queue, song validation, and model. The specification described a fuller graphical interface; this checkpoint implements a deliberately thin Swing interface with library selection, queue display, and Play Next. These choices were prompted by Deliverable 3's requirement to prove the model works independently before investing in the interface. The sample library contains three demonstration songs because the specification did not define how library data would be loaded. The view exposes listener-registration and update methods to keep user input and rendering separate from model logic.

### Did the model stand alone?

The model, queue, and song classes contain no Swing imports and do not reference the controller or view. `ThrowBeatModelDriver` creates only a `ThrowBeatModel`, adds songs, and checks FIFO playback. The view gets snapshots from the controller, not access to linked nodes or mutable queue state. No logic had to move from the view into the model because validation and FIFO behavior were implemented in the model from the start.

### What happened to the unknowns?

**Resolved in this prototype:** how to implement a singly linked queue with front/rear nodes; how to preserve FIFO order; how to prevent null insertion, over-capacity insertion, and empty removal; how to connect a Swing view through a controller; and how to exercise the model without a GUI. **Still open:** whether to support real audio playback, persistent song libraries, and a more complete UI. Check these against the unknowns in the team's original scope document before submitting.

### What doesn't work yet?

This prototype displays the current song but does not play audio. It uses a fixed demonstration library, so users cannot import their own files. There is no skip/requeue action yet. Automated JUnit tests cover the core model, queue, and several bad inputs, but the GUI/controller behavior is currently checked manually. Record any actual failing tests or platform-specific issues here after running it.

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

### New tests

- `SongQueue.skipAndRequeue()`: empty queue, one song, three songs, repeated skips, and size/order preservation.
- `ThrowBeatModel.skipNext()`: empty and non-empty queues; current song unchanged; correct next song after a skip.
- `ThrowBeatController.handleSkipNext()`: action triggers model change and refresh; empty queue gives feedback.
- Regression: `enqueue`, `dequeue`, `peek`, `playNext`, invalid song IDs, null songs, full queue, and defensive copies.

### Who does what (planned; confirm with teammates)

| Team member | Planned Deliverable 4 ownership |
| --- | --- |
| Shresta | Implement `SongQueue.skipAndRequeue()` and `ThrowBeatModel.skipNext()`; add model/queue tests |
| Isaac | Add Skip button and listener to `ThrowBeatView`; manually test GUI |
| Seth | Implement `ThrowBeatController.handleSkipNext()`; integration and controller tests |
| All | Review integration, run tests, and update README |

## Attribution

**IMPORTANT: This is a proposed ownership table based on Deliverable 2, not a claim that these team members have written or reviewed the generated files. Replace each entry with verified contributions before submission. Every member must contribute substantively to at least one class or test file.**

| Class or test file | Planned owner | Actual contribution (complete after team work) |
| --- | --- | --- |
| `Song.java` | Shresta | Pending verification: write/revise/review/test |
| `SongQueue.java` | Shresta | Pending verification: write/revise/review/test |
| `ThrowBeatModel.java` | Shresta | Pending verification: write/revise/review/test |
| `ThrowBeatModelDriver.java` | Shresta / Seth | Pending verification: write/revise/review/test |
| `ThrowBeatView.java` | Isaac | Pending verification: write/revise/review/test |
| `ThrowBeatController.java` | Seth | Pending verification: write/revise/review/test |
| `ThrowBeatApp.java` | Seth | Pending verification: write/revise/review/test |
| `SongTest.java` | Shresta | Pending verification: write/revise/review/test |
| `SongQueueTest.java` | Shresta | Pending verification: write/revise/review/test |
| `ThrowBeatModelTest.java` | Shresta | Pending verification: write/revise/review/test |

### GenAI note

ChatGPT was used to generate an initial Java prototype, linked-node FIFO queue, MVC wiring, model driver, JUnit test drafts, and README draft based on the team's Deliverable 2 specification. It was also asked to predict which classes and methods would change for a Skip and Requeue feature. The team must review, understand, test, and revise the generated code, and record which members actually performed that work. Do not claim tests or GUI checks were run unless they were actually run.
