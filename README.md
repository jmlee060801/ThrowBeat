# ThrowBeat — Music Queue Player

Project 3, Deliverable 3 — First Prototype

## Requirements

Java 17 or newer. Maven is needed for JUnit tests, but **not** for the model driver or GUI.

## How to run

From the project root:

```bash
javac -d out src/main/java/*.java
java -cp out ThrowBeatModelDriver
java -cp out ThrowBeatApp
```

The first command compiles all classes. The driver runs the **model independently** without opening any GUI. The second `java` command opens the thin Swing GUI (requires a graphical desktop). Select a song, click **Add Selected Song**, then click **Play Next**. It displays song metadata; it does not play audio files.

Run JUnit 5 tests with:

```bash
mvn test
```

## Design

- `Song`: immutable song ID, title, artist; validates construction.
- `SongQueue`: custom singly linked FIFO queue, `front`, `rear`, `size`, capacity 1000.
- `ThrowBeatModel`: library, queue, currently selected playing song; never imports Swing or references the view.
- `ThrowBeatView`: displays library, waiting queue, and now playing; raises user actions.
- `ThrowBeatController`: receives view actions, calls model, refreshes view.
- `ThrowBeatApp`: wires components together.
- `ThrowBeatModelDriver`: runs checks without any view.

## Reflection

### What changed from the specification?

The Deliverable 2 specification called for `Song`, a linked-node `SongQueue`, `ThrowBeatModel`, `ThrowBeatView`, `ThrowBeatController`, and `ThrowBeatApp`. We retained those classes and the public queue/model/controller method signatures. We added `ThrowBeatModelDriver` so the core logic can be exercised from the command line without launching a GUI. We also added JUnit test files for the queue, song validation, and model. The specification described a fuller graphical interface; this checkpoint implements a deliberately thin Swing interface with library selection, queue display, and Play Next. These choices were prompted by Deliverable 3's requirement to prove the model works independently before investing in the interface. The sample library contains three demonstration songs because the specification did not define how library data would be loaded. The view exposes listener-registration and update methods to keep user input and rendering separate from model logic.

### Did the model stand alone?

The model, queue, and song classes contain no Swing imports and do not reference the controller or view. `ThrowBeatModelDriver` creates only a `ThrowBeatModel`, adds songs, and checks FIFO playback. The view gets snapshots from the controller, not access to linked nodes or mutable queue state. No logic had to move from the view into the model because validation and FIFO behavior were implemented in the model from the start.

### What happened to the unknowns?

**Resolved in this prototype:** how to implement a singly linked queue with front/rear nodes; how to preserve FIFO order; how to prevent null insertion, over-capacity insertion, and empty removal; how to connect a Swing view through a controller; and how to exercise the model without a GUI. **Still open:** whether to support real audio playback, persistent song libraries, and a more complete UI. Check these against the unknowns in the team's original scope document before submitting.

### What doesn't work yet?

This prototype displays the current song but does not play audio. It uses a fixed demonstration library, so users cannot import their own files. There is no skip/requeue action yet. Automated JUnit tests cover the core model, queue, and several bad inputs, but the GUI/controller behavior is currently checked manually. Record any actual failing tests or platform-specific issues here after running it.

## Next Steps

### Finish the MVP in order

1. Run the driver, JUnit tests, and GUI on every teammate's development environment; fix any failures.
2. Confirm that library selection, Add, Play Next, and Now Playing update correctly end to end.
3. Add automated tests for controller behavior or separate controller logic for easier testing.
4. Improve user-facing feedback for invalid actions and document the remaining limitations.

### Deliverable 4 non-trivial change feature: Skip and Requeue

Add a **Skip and Requeue** action: when songs are waiting, move the first waiting song to the back of the queue. For example, `[A, B, C]` becomes `[B, C, A]`. This changes model state and is reachable from the view through the controller, not just a cosmetic button.

**Predicted edits (specific methods):**

- `SongQueue.java`: add `skipAndRequeue()` using `dequeue()` and `enqueue()` (handle an empty queue and a single-song queue).
- `ThrowBeatModel.java`: add `skipNext()` to delegate to the queue and report whether a skip occurred.
- `ThrowBeatController.java`: add `handleSkipNext()` and call `refreshView()`.
- `ThrowBeatView.java`: add a Skip button and `onSkipNext(Runnable)` listener registration.
- `SongQueueTest.java`, `ThrowBeatModelTest.java`: test the new behavior.
- `ThrowBeatApp.java`: **should not change** because controller construction already registers actions.
- `Song.java`: **should not change** because song metadata is unaffected.
- `ThrowBeatModelDriver.java`: optionally extend independent smoke checks; no structural change expected.

### Stretch goals

**Commit:** Skip and Requeue (new change feature), improved invalid-action messages, and additional model tests. **Defer/drop for Deliverable 4:** audio file playback, playlist persistence, shuffle, and UI redesign. Confirm this list against the team's original scope before submitting.

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

| Class or test file | Planned owner | Actual contribution (complete after team work) |
| --- | --- | --- |
| `Song.java` | Issac | write/revise/review/test |
| `SongQueue.java` | Issac | write/revise/review/test |
| `ThrowBeatModel.java` | Issac | write/revise/review/test |
| `ThrowBeatModelDriver.java` | Shresta / Seth | write/revise/review/test |
| `ThrowBeatView.java` | Isaac | write/revise/review/test |
| `ThrowBeatController.java` | Seth | write/revise/review/test |
| `ThrowBeatApp.java` | Seth | write/revise/review/test |
| `SongTest.java` | Shresta | write/revise/review/test |
| `SongQueueTest.java` | Shresta | write/revise/review/test |
| `ThrowBeatModelTest.java` | Shresta | write/revise/review/test |

### GenAI note

ChatGPT was used to generate an initial Java prototype, linked-node FIFO queue, MVC wiring, model driver, JUnit test drafts, and README draft based on the team's Deliverable 2 specification. It was also asked to predict which classes and methods would change for a Skip and Requeue feature. The team must review, understand, test, and revise the generated code, and record which members actually performed that work. Do not claim tests or GUI checks were run unless they were actually run.
