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

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import io.github.ossnass.fx.exceptions.FXMLIDDuplicationException;
import io.github.ossnass.fx.exceptions.FXMLNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/**
 * The brains behind SimpleFX library.
 * <p>
 * All your controllers are initialized here, and in order to be initialized, each controller
 * must extend {@link SimpleController} and must be annotated with {@link ControllerInfo}
 * <p>
 * The language file are defined here in order to pass it to all controllers.
 * <p>
 * For CSS files, there are 2 types:
 * <ol>
 *     <li>Global CSS: a list exists inside ControlMaster passed to all {@link SimpleController} objects</li>
 *     <li>A local CSS per controller that can be passed using {@link ControllerInfo#CSS()}, which has to be resource bound.</li>
 * </ol>
 */
public class ControlMaster {

    private static ControlMaster cm;
    private final HashMap<String, SimpleController> singleInstance_Startup = new HashMap<>();
    private final HashMap<String, ControllerInfo> contollerInfos = new HashMap<>();
    private final HashMap<String, Class<? extends SimpleController>> controllerClasses = new HashMap<>();
    private MultiSourceResourceBundle language = null;
    private ObservableList<String> csses = FXCollections.observableArrayList(new ArrayList<>());

    private ControlMaster() {
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

    void addChangeListener(ListChangeListener lcl) {
        csses.addListener(lcl);
    }

    void removeChangeListener(ListChangeListener lcl) {
        csses.removeListener(lcl);
    }

    /**
     * Returns the extra CSS file to be used by the application
     *
     * @return the extra CSS file to be used by the application
     */
    public final ObservableList<String> getCSSes() {
        return csses;
    }


    /**
     * Returns the current language file
     *
     * @return the current language file
     */
    public MultiSourceResourceBundle getLanguage() {
        return language;
    }

    private void setLanguage(MultiSourceResourceBundle lang) {
        if (lang == null) {
            throw new IllegalArgumentException("The language resource cannot be null");
        }
        language = lang;
        Locale.setDefault(new Locale(lang
                .getString("LANG.SHORT"), lang.getString("LANG.COUNTRY")));
    }

    private void setLanguage(String lang) throws IOException {
        setLanguage(new MultiSourceResourceBundle(new InputStreamReader(new FileInputStream(lang), StandardCharsets.UTF_8)));
    }

    /**
     * Initializes the control master.
     * <p>
     * Defines the language and CSS file and the controller will scour the class path
     * for classes extending {@link SimpleController} and annotated with {@link ControllerInfo}
     *
     * @param languageFile the path to the language file cannot be null
     * @throws IOException in case of error while reading the FXML files
     */
    public void initControlMaster(String languageFile) throws IOException {
        setLanguage(languageFile);
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
    public SimpleController getController(String Id) throws IOException, FXMLNotFoundException {
        SimpleController value = null;
        value = contollerInfos.get(Id).Type().getAction().getController(Id);
        if (value == null) {
            throw new FXMLNotFoundException(Id);
        }
        return value;
    }

    private SimpleController loadController(String Id) throws IOException {
        if (language == null) {
            throw new IllegalArgumentException("Language cannot be null");
        }
        URL url = ResourceManager.getURL(contollerInfos.get(Id).FXMLFile());
        FXMLLoader loader = new FXMLLoader(url, language);

        Pane root = loader.load();
        if (loader.getController() == null)
            try {
                loader.setController(controllerClasses.get(Id).getConstructors()[0].newInstance(null));
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
        SimpleController res = (SimpleController) loader.getController();
        Scene scene = new Scene(res.getRoot());
        res.setScene(scene);
        return res;
    }

    private void addController(ControllerInfo info, Class<? extends SimpleController> controllerClass) throws IOException {
        String filename = info.FXMLFile();

        URL url = ResourceManager.getURL(contollerInfos.get(info.Id()).FXMLFile());

        controllerClasses.put(info.Id(), controllerClass);
        info.Type().getAction().addController(info, url, controllerClass);
    }

    private void findControllers() throws IOException {

        try (ScanResult res = new ClassGraph().enableAnnotationInfo().scan()) {
            ClassInfoList cil = res.getClassesWithAnnotation(ControllerInfo.class.getCanonicalName());
            for (ClassInfo cinfo : cil) {
                ControllerInfo ci = (ControllerInfo) cinfo.getAnnotationInfo(ControllerInfo.class.getCanonicalName()).loadClassAndInstantiate();
                if (controllerClasses.containsKey(ci.Id())) {
                    throw new FXMLIDDuplicationException(ci.Id());
                }
                contollerInfos.put(ci.Id(), ci);
                addController(ci, (Class<? extends SimpleController>) cinfo.loadClass());
            }
        }


    }

    interface ControllerAction {
        void addController(ControllerInfo info, URL url, Class<? extends SimpleController> controllerClass) throws IOException;

        SimpleController getController(String id) throws IOException;
    }

    static class initOnceStartup implements ControllerAction {

        private static initOnceStartup instance;

        private initOnceStartup() {
        }

        public static ControllerAction get() {
            if (instance == null)
                instance = new initOnceStartup();
            return instance;
        }

        @Override
        public void addController(ControllerInfo info, URL url, Class<? extends SimpleController> controllerClass) throws IOException {
            ControlMaster.getControlMaster().singleInstance_Startup.put(info.Id(), ControlMaster.getControlMaster().loadController(info.Id()));
        }

        @Override
        public SimpleController getController(String id) throws IOException {
            return ControlMaster.getControlMaster().singleInstance_Startup.get(id);
        }

    }

    static class initOnceOnDemand implements ControllerAction {


        private static initOnceOnDemand instance;

        private initOnceOnDemand() {
        }

        public static ControllerAction get() {
            if (instance == null)
                instance = new initOnceOnDemand();
            return instance;
        }

        @Override
        public void addController(ControllerInfo info, URL url, Class<? extends SimpleController> controllerClass) throws IOException {

        }

        @Override
        public SimpleController getController(String Id) throws IOException {
            SimpleController value = null;
            value = ControlMaster.getControlMaster().singleInstance_Startup.get(Id);
            if (value == null) {
                value = ControlMaster.getControlMaster().loadController(Id);
                ControlMaster.getControlMaster().singleInstance_Startup.put(Id, value);
            }
            return value;
        }
    }

    static class initMulti implements ControllerAction {

        private static initMulti instance;

        private initMulti() {
        }

        public static ControllerAction get() {
            if (instance == null)
                instance = new initMulti();
            return instance;
        }

        @Override
        public void addController(ControllerInfo info, URL url, Class<? extends SimpleController> controllerClass) throws IOException {

        }

        @Override
        public SimpleController getController(String Id) throws IOException {
            return ControlMaster.getControlMaster().loadController(Id);
        }
    }
}

