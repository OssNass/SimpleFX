# Keyboard Example
Provides an example about using the keyboard shortcut functionality in SimpleFX

In order to use this functionality, you need to use `kbsm` preferably in the `userInit` overridden function of your code.
The functions that are called by the keyboard shortcut must be of JavaFX callback, meaning they return `void` and accept `Event` parameter.
Every keyboard shortcut is optionally tied to a `Node`, for in case this node is disabled or invisible, the keyboard shortcut is not functional

Type `mvn clean install exec:exec` to run