package org.ocomp.fx;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import org.ocomp.fx.exceptions.FXMLIDDuplicationException;
import org.ocomp.fx.exceptions.FXMLNotFoundException;
import org.reflections.Reflections;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class ControlMaster {

    private static ControlMaster cm;
    private HashMap<String, SimpleController> singleInstance_Startup = new HashMap<>();
    private HashMap<String, URL> singleInstance_OnDemand = new HashMap<>();
    private HashMap<String, URL> multipleInstances = new HashMap<>();
    private HashMap<String, Class<? extends SimpleController>> controllerClasses = new HashMap<>();
    private ResourceBundle language = null;


    private ControlMaster() {

    }

    public static ControlMaster getCm() {
        if (cm == null)
            cm = new ControlMaster();
        return cm;
    }

    protected void setLanguage(ResourceBundle lang) {
        if (lang == null) {
            throw new IllegalArgumentException("The language resource cannot be null");
        }
        language = lang;
        Locale.setDefault(new Locale(lang
                .getString("LANG.SHORT"), lang.getString("LANG.COUNTRY")));
    }

    protected void setLanguage(String lang) throws IOException {
        setLanguage(new PropertyResourceBundle(new InputStreamReader(new FileInputStream(lang), StandardCharsets.UTF_8)));
    }

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
    public SimpleController getInstance(String Id) throws IOException, FXMLNotFoundException {
        SimpleController value = null;
        value = singleInstance_Startup.get(Id);
        if (value == null) {
            URL url = singleInstance_OnDemand.get(Id);
            if (url != null) {
                value = loadController(Id, url);
                //adds the created controller to the singleinstacne_startup pool
                singleInstance_Startup.put(Id, value);
                singleInstance_OnDemand.remove(Id);
            }
        }
        if (value == null) {
            URL url = multipleInstances.get(Id);
            if (url != null) {
                value = loadController(Id, url);

            }
        }
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
        SimpleController res =(SimpleController) loader.getController();
        return res;
    }

    /**
     * This is an internal function, must not be called by the user
     */
    private void addController(ControllerInfo info, Class<? extends SimpleController> controllerClass) throws IOException {
        if (controllerClasses.containsKey(info.Id())) {
            throw new FXMLIDDuplicationException(info.Id());
        }
        String filename = info.FXMLFile();
        if (!filename.startsWith("/"))
            filename = "/" + filename;
        URL url = getClass().getResource(filename);
        controllerClasses.put(info.Id(), controllerClass);
        switch (info.Type()) {
            case SINGLE_INSTANCE_ON_STARTUP:
                singleInstance_Startup.put(info.Id(), loadController(info.Id(), url));
                break;
            case SINGLE_INSTANCE_ON_DEMAND:
                singleInstance_OnDemand.put(info.Id(), url);
                break;
            case MULTIPLE_INSTANCE:
                multipleInstances.put(info.Id(), url);
                break;
        }
    }

    /**
     * This is an internal function, must not be called by the user
     */
    private void findControllers() throws IOException {
        Reflections refs = new Reflections();
        for (Class<?> cl : refs.getTypesAnnotatedWith(ControllerInfo.class)) {
            ControllerInfo ci = cl.getAnnotation(ControllerInfo.class);
            addController(ci, (Class<? extends SimpleController>) cl);
            System.out.println(ci.FXMLFile());
        }
    }
}
