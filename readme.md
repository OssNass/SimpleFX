# SimpleFX

A Library designed to simplify the useage of JavaFX framework

This library is built in way that makes the development easy by defining 3 types of FXML GUIs:

1.  Initialize once at startup: this type is initialized once during the application life cycle, at the startup of the application is kept in the memory until the application exists 
2.  Initialize once on demand: similar to the previous type but with a twist, it's initialized when needed and kept in the memory. 
3.  Mulitple instances: a new instance is initialize upon each request and is discarded once the stage housing it is closed.

The main reason behind that approach is to allow a balance between memory usage and performance, by delaying the non-essentional GUIs for when they are needed.

In order to utlize this feature you need to extened the `SimpleController` class and annotate it with `ControllerInfo` annotation in which you need to specify a unique string ID and the full absolete path to the FXML in the project resources at least.

Please notice that this is not a finished product, and the examples are yet to be ready as currently I am busy on another project.

More updates will follow.

This library is built and tested on Java 8, with Java 9+ support down the line
