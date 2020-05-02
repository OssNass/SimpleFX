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

package org.ocomp.fx.settings;

import java.io.*;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

/**
 * This class is responsible for loading and saving all the settings you can possibly want
 * <p>
 * It uses the {@link Properties} class to load and save properties.
 * <p>
 * There are a variaty of possible types of properties including
 * <ul>
 *     <li>{@link BooleanSetting} to store boolean values</li>
 *     <li>{@link CharSetting} to store char values</li>
 *     <li>{@link StringSetting} to store string values</li>
 *     <li>{@link ByteSetting} to store byte values</li>
 *     <li>{@link DoubleSetting} to store double values</li>
 *     <li>{@link IntegerSetting} to store integer values</li>
 *     <li>{@link LongSetting} to store long values</li>
 *     <li>{@link ShortSetting} to store short values</li>
 *     <li>{@link FloatSetting} to store float values</li>
 * </ul>
 * <p>
 * You need to declare and the settings manually before you can use them, please notice that upon initialization,
 * the setting is added automatically to the setting manager
 */
public class SettingsManager {
    private Properties configuration;
    private File configFile;
    private HashMap<String, Setting> settings;

    /**
     * Stores a setting value in the file
     *
     * @param setting the name of the setting
     * @param value   the string representation of the setting value
     */
    protected void storeSettings(String setting, String value) {
        configuration.setProperty(setting, value);
        try {
            configuration.store(new FileOutputStream(configFile.getAbsolutePath()), "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adding a new setting to the settings
     *
     * @param setting the value of the setting in
     *                if the setting alread exists, it will throw {@link IllegalArgumentException}
     * @throws IllegalArgumentException if adding an existing setting
     */
    public void addSetting(Setting setting) {
        if (settings.containsKey(setting.getName()))
            throw new IllegalArgumentException(String.format("Setting %s already exists", setting.getName()));
        settings.put(setting.getName(), setting);
        if (configuration.containsKey(setting.getName()))
            setting.setData(setting.fromString(configuration.getProperty(setting.getName())), false);
    }

    private SettingsManager() {
        configuration = new Properties();
        settings = new HashMap<>();
    }

    private void loadFile(String filename) throws IOException {
        configFile = new File(filename);
        if (!configFile.exists()) {
            if (!configFile.getParentFile().exists())
                configFile.getParentFile().mkdirs();
            configFile.createNewFile();
        }
        configuration.load(new FileInputStream(configFile.getAbsolutePath()));
        Set<String> names = configuration.stringPropertyNames();
        names.forEach(name -> {
            if (settings.containsKey(name))
                settings.get(name).setData(settings.get(name).fromString(configuration.getProperty(name)), false);
        });
    }

    private static SettingsManager manager;

    /**
     * Initializes the settings manager and loads the settings from file
     *
     * @param filename the file where the settings are stored
     * @throws IOException in case the file is inaccessible
     */
    public static void initSettingsManager(String filename) throws IOException {
        getManager().loadFile(filename);
    }

    /**
     * Returns the only instance of {@link SettingsManager}
     *
     * @return the only instance of {@link SettingsManager}
     */
    public static SettingsManager getManager() {
        if (manager == null)
            manager = new SettingsManager();
        return manager;
    }
}
