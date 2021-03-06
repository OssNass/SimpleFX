# SimpleFX

A Library designed to simplify the usage of JavaFX framework

This library is built in way that makes the development easy by defining 3 types of FXML GUIs:

1.  Initialize once at startup: this type is initialized once during the application life cycle, at the startup of the application is kept in the memory until the application exists 
2.  Initialize once on demand: similar to the previous type but with a twist, it's initialized when needed and kept in the memory. 
3.  Multiple instances: a new instance is initialize upon each request and is discarded once the stage housing it is closed.

The main reason behind that approach is to allow a balance between memory usage and performance, by delaying the non-essential GUIs for when they are needed.

In order to utilize this feature you need to extend the `SimpleController` class and annotate it with `ControllerInfo` annotation in which you need to specify a unique string ID and the full absolute path to the FXML in the project resources at least.

This library is built and tested on Java 8, and Java 11+.

For examples you can see [examples](examples) folder.


PLEASE BE AWARE THAT AS OF VERSION 0.4 JAVA 8 SUPPORT IS DEPRECATED AND WILL RECEIVE NO MORE UPDATES, WILL CONTINUE WITH JAVA 11+ FROM VERSION 0.5 ONWARDS 

In order to use it in your project please import using Maven:

```xml
<dependency>
    <groupId>io.github.ossnass</groupId>
    <artifactId>simplefx</artifactId>
    <version>0.4</version>
</dependency>
```

or in Gradle:

```
compile group: 'io.github.ossnass', name: 'simplefx', version: '0.4'
```

If you are using Java 11+ please use the following

```xml
<dependency>
    <groupId>io.github.ossnass</groupId>
    <artifactId>simplefx</artifactId>
    <version>0.5.1</version>
</dependency>
```

or in Gradle:

```
compile group: 'io.github.ossnass', name: 'simplefx', version: '0.5-11'
```
