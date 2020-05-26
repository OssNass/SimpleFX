/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2020 Ossama Nasser
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.ossnass.fx;

//import io.github.classgraph.AnnotationInfo;
//import io.github.classgraph.ClassGraph;
//import io.github.classgraph.ClassInfo;
//import io.github.classgraph.ScanResult;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import io.github.ossnass.fx.exceptions.FXMLIDDuplicationException;
import io.github.ossnass.fx.exceptions.FXMLNotFoundException;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * The brains behind SimpleFX library.
 * <p>
 * All your controllers are initialized here, and in order to be initialized, each controller
 * must extend {@link SimpleController} and must be annotated with {@link ControllerInfo}
 * <p>
 * The language and CSS files are defined here in order to pass it to all controllers.
 */
public class ControlMaster {

    private static ControlMaster cm;
    private HashMap<String, SimpleController> singleInstance_Startup = new HashMap<>();
    private HashMap<String, ControllerInfo> contollerInfos = new HashMap<>();
    private HashMap<String, URL> singleInstance_OnDemand = new HashMap<>();
    private HashMap<String, URL> multipleInstances = new HashMap<>();
    private HashMap<String, String> icons = new HashMap<>();
    private HashMap<String, Class<? extends SimpleController>> controllerClasses = new HashMap<>();
    private ResourceBundle language = null;
    private String cSSPath = null;

    private ControlMaster() {

    }

    /**
     * Returns the extra CSS file to be used by the application
     *
     * @return the extra CSS file to be used by the application
     */
    public final String getCSSPath() {
        return cSSPath;
    }

    protected final void setCSSPath(String cSSPath2) {
        cSSPath = cSSPath2;
    }

    /**
     * Returns the only instance of {@link ControlMaster}
     *
     * @return the only instance of {@link ControlMaster}
     */
    public static ControlMaster getControlMaster() {
        if (cm == null)
            cm = new ControlMaster();
        return cm;
    }

    private void setLanguage(ResourceBundle lang) {
        if (lang == null) {
            throw new IllegalArgumentException("The language resource cannot be null");
        }
        language = lang;
        Locale.setDefault(new Locale(lang
                .getString("LANG.SHORT"), lang.getString("LANG.COUNTRY")));
    }

    private void setLanguage(String lang) throws IOException {
        setLanguage(new PropertyResourceBundle(new InputStreamReader(new FileInputStream(lang), StandardCharsets.UTF_8)));
    }

    /**
     * Returns the current language file
     *
     * @return the current language file
     */
    public ResourceBundle getLanguage() {
        return language;
    }

    /**
     * Initializes the control master.
     * <p>
     * Defines the language and CSS file and the controller will scour the class path
     * for classes extending {@link SimpleController} and annotated with {@link ControllerInfo}
     *
     * @param languageFile the path to the language file cannot be null
     * @param CSSFile      the path to the CSS file, can be null
     * @throws IOException in case of error while reading the FXML files
     */
    public void initControlMaster(String languageFile, String CSSFile) throws IOException {
        setLanguage(languageFile);
        setCSSPath(CSSFile);
        findControllers();
    }

    /**
     * Creates a new Controller based on its ID regardless of its type.
     *
     * @param Id the ID of the controller as defined by {@link ControllerInfo#Id()}
     * @return the controller of the FXML file
     * @throws IOException           if an error occurred while reading the FXML file
     * @throws FXMLNotFoundException if the Id doesn't match any controller of any type.
     */
    public SimpleController getInstance(String Id) throws IOException, FXMLNotFoundException {
        SimpleController value = null;
        value = contollerInfos.get(Id).Type().getAction().getController(Id);
        if (value == null) {
            throw new FXMLNotFoundException(Id);
        }
        return value;
    }

    private SimpleController loadController(String Id, URL url) throws IOException {
        if (language == null) {
            throw new IllegalArgumentException("Language cannot be null");
        }
        FXMLLoader loader = new FXMLLoader(url, language);

        Pane root = loader.load();
        if (loader.getController() == null)
            try {
                loader.setController(controllerClasses.get(Id).getConstructors()[0].newInstance(null));
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
        SimpleController res = (SimpleController) loader.getController();
        res.setId(Id);
        res.setIcon(icons.get(Id));
        Scene scene = new Scene(res.getRoot());
        res.setScene(scene);
        return res;
    }

    private void addController(ControllerInfo info, Class<? extends SimpleController> controllerClass) throws IOException {
        if (controllerClasses.containsKey(info.Id())) {
            throw new FXMLIDDuplicationException(info.Id());
        }
        String filename = info.FXMLFile();
        if (!filename.startsWith("/"))
            filename = "/" + filename;
        URL url = getClass().getResource(filename);
        controllerClasses.put(info.Id(), controllerClass);
        info.Type().getAction().addController(info,url,controllerClass);
//        switch (info.Type()) {
//            case SINGLE_INSTANCE_ON_STARTUP:
//                singleInstance_Startup.put(info.Id(), loadController(info.Id(), url));
//                break;
//            case SINGLE_INSTANCE_ON_DEMAND:
//                singleInstance_OnDemand.put(info.Id(), url);
//                break;
//            case MULTIPLE_INSTANCE:
//                multipleInstances.put(info.Id(), url);
//                break;
//        }
    }

    private void findControllers() throws IOException {

        try (ScanResult res = new ClassGraph().enableAnnotationInfo().scan()) {
            ClassInfoList cil = res.getClassesWithAnnotation(ControllerInfo.class.getCanonicalName());
            for (ClassInfo cinfo : cil) {
                ControllerInfo ci = (ControllerInfo) cinfo.getAnnotationInfo(ControllerInfo.class.getCanonicalName()).loadClassAndInstantiate();
                contollerInfos.put(ci.Id(), ci);
                addController(ci, (Class<? extends SimpleController>) cinfo.loadClass());
                icons.put(ci.Id(), ci.Icon());
            }
        }
    }

    static interface ControllerAction {
        void addController(ControllerInfo info, URL url, Class<? extends SimpleController> controllerClass) throws IOException;

        SimpleController getController(String id) throws IOException;
    }

    static class initOnceStartup implements ControllerAction {

        @Override
        public void addController(ControllerInfo info, URL url, Class<? extends SimpleController> controllerClass) throws IOException {
            ControlMaster.getControlMaster().singleInstance_Startup.put(info.Id(), ControlMaster.getControlMaster().loadController(info.Id(), url));
        }

        @Override
        public SimpleController getController(String id) throws IOException {
            return ControlMaster.getControlMaster().singleInstance_Startup.get(id);
        }

        private initOnceStartup() {
        }

        private static initOnceStartup instance;

        public static ControllerAction get() {
            if (instance == null)
                instance = new initOnceStartup();
            return instance;
        }

    }

    static class initOnceOnDemand implements ControllerAction {

        @Override
        public void addController(ControllerInfo info, URL url, Class<? extends SimpleController> controllerClass) throws IOException {
            ControlMaster.getControlMaster().singleInstance_OnDemand.put(info.Id(), url);
        }

        @Override
        public SimpleController getController(String Id) throws IOException {
            SimpleController value = null;
            value = ControlMaster.getControlMaster().singleInstance_Startup.get(Id);
            if (value == null) {
                URL url = ControlMaster.getControlMaster().singleInstance_OnDemand.get(Id);
                value = ControlMaster.getControlMaster().loadController(Id, url);
                ControlMaster.getControlMaster().singleInstance_Startup.put(Id, value);
                ControlMaster.getControlMaster().singleInstance_OnDemand.remove(Id);
            }
            return value;
        }

        private initOnceOnDemand() {
        }

        private static initOnceOnDemand instance;

        public static ControllerAction get() {
            if (instance == null)
                instance = new initOnceOnDemand();
            return instance;
        }
    }

    static class initMulti implements ControllerAction {

        @Override
        public void addController(ControllerInfo info, URL url, Class<? extends SimpleController> controllerClass) throws IOException {
            ControlMaster.getControlMaster().multipleInstances.put(info.Id(), url);
        }

        @Override
        public SimpleController getController(String Id) throws IOException {
            return ControlMaster.getControlMaster().loadController(Id, ControlMaster.getControlMaster().multipleInstances.get(Id));
        }

        private initMulti() {
        }

        private static initMulti instance;

        public static ControllerAction get() {
            if (instance == null)
                instance = new initMulti();
            return instance;
        }
    }
}

