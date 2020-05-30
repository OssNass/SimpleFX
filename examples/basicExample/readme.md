# Basic Example

Explains the basic ideas behind SimpleFX.

This example provides information about the initialization and usage of the 3 types of controls in SimpleFX:
1. Initialize Once at Startup: some controllers you want them to be initialized upon application start up once and to be available all the time
2. Initialize Once on Demand: same as the above, but to speed start up times, the initialization is on demand
3. Multiple Initialization: new controller is initialized upon each request and discarded once the stage housing it is closed

Type `mvn clean install exec:exec` to run.