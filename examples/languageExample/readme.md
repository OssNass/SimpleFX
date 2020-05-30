# Language Example

Describes how to change the language of your application in SimpleFX.

You need to have `.lang` file which at least must have the following entries:
1. `LANG.NAME` the name of the language like `Arabic (Syria)`
2. `LANG.SHORT` the official acronym of the language like `en` for English and `ar` for Arabic
3. `LANG.COUNTRY` the official acronym of the country for locale like `SY` for Syria
4. `LANG.DIR` the direction of the language, `LTR` for left-2-right languages, `RTL` for right-2-left languages
5. `MESSAGE.TITLE.ERROR` the title of the error message dialog
6. `MESSAGE.TITLE.WARNING` the title of the warning message dialog
7. `MESSAGE.TITLE.INFO` the title of the information message dialog
8. `MESSAGE.TITLE.CONFIRM` the title of the confirmation dialog

In order to make your stage has a title, you can either set it in you code, or add the following line for each stage:
`STAGE.<ControllerID>.TITLE`

In the main class of the application, switch between these line:
```
        ControlMaster.getControlMaster().initControlMaster("en-us.lang", null);
        ControlMaster.getControlMaster().initControlMaster("ar-sy.lang",null);
        ControlMaster.getControlMaster().initControlMaster("ar-eg.lang",null);
```
Then type:
`mvn clean install exec:exec`