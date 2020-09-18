package io.github.ossnass.fx;

import sun.util.ResourceBundleEnumeration;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.*;

/**
 * This Class allows loading language from multiple files into a single entity.
 * <p>
 * The language files must have the same value for keys:
 * <ul>
 *     <li>{@link LanguageKeys#LANG_SHORT}</li>
 *     <li>{@link LanguageKeys#LANG_COUNTRY_SHORT}</li>
 * </ul>
 * <p>
 * Otherwise the merging will fail and the keys of the new file will load.
 * <p>
 * If two files contain the same key with different values, then the later file value will not load.
 * <p>
 * It is based on {@link PropertyResourceBundle}
 */
public class MultiSourceResourceBundle extends ResourceBundle {
    private final Map<String, Object> values;

    /**
     * Creates a multisource resource bundle from an {@link java.io.InputStream
     * InputStream}.  The property file read with this constructor
     * must be encoded in ISO-8859-1.
     *
     * @param stream an InputStream that represents a property file
     *               to read from.
     * @throws IOException              if an I/O error occurs
     * @throws NullPointerException     if <code>stream</code> is null
     * @throws IllegalArgumentException if {@code stream} contains a
     *                                  malformed Unicode escape sequence.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public MultiSourceResourceBundle(InputStream stream) throws IOException {
        Properties properties = new Properties();
        properties.load(stream);
        values = new HashMap(properties);
    }

    /**
     * Creates a property resource bundle from a {@link java.io.Reader
     * Reader}.  Unlike the constructor
     * {@link #MultiSourceResourceBundle(InputStream)} (java.io.InputStream) PropertyResourceBundle(InputStream)},
     * there is no limitation as to the encoding of the input property file.
     *
     * @param reader a Reader that represents a property file to
     *               read from.
     * @throws IOException              if an I/O error occurs
     * @throws NullPointerException     if <code>reader</code> is null
     * @throws IllegalArgumentException if a malformed Unicode escape sequence appears
     *                                  from {@code reader}.
     * @since 1.6
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public MultiSourceResourceBundle(Reader reader) throws IOException {
        Properties properties = new Properties();
        properties.load(reader);
        values = new HashMap(properties);
    }

    private boolean processProperties(Properties props) {
        Set<Object> keys = props.keySet();
        String shortLang = props.getProperty(LanguageKeys.LANG_SHORT);
        String country = props.getProperty(LanguageKeys.LANG_COUNTRY_SHORT);
        if (!(shortLang.equalsIgnoreCase(values.get(LanguageKeys.LANG_SHORT).toString())
                &&
                country.equalsIgnoreCase(values.get(LanguageKeys.LANG_COUNTRY_SHORT).toString())))
            return false;
        keys.remove(LanguageKeys.LANG_SHORT);
        keys.remove(LanguageKeys.LANG_COUNTRY_SHORT);
        keys.remove(LanguageKeys.LANG_LAYOUT_DIRECTION);
        keys.remove(LanguageKeys.MESSAGE_TITLE_INFO);
        keys.remove(LanguageKeys.MESSAGE_TITLE_CONFIRM);
        keys.remove(LanguageKeys.MESSAGE_TITLE_ERROR);
        keys.remove(LanguageKeys.MESSAGE_TITLE_WARNING);
        keys.remove(LanguageKeys.LANG_NAME);
        for (Object key : keys) {
            if (!values.containsKey(key))
                values.put(key.toString(), props.getProperty(key.toString()));
        }
        return true;
    }

    /**
     * Appends a new language file to the current
     *
     * @param newValues the new language file reader
     * @return true if success, false otherwise
     * @throws IOException
     */
    public boolean appendResource(Reader newValues) throws IOException {
        Properties props = new Properties();
        props.load(newValues);
        return processProperties(props);
    }

    /**
     * Appends a new language file to the current
     *
     * @param newValues the new language file input stream
     * @return true if success, false otherwise
     * @throws IOException
     */
    public boolean appendResource(InputStream newValues) throws IOException {
        Properties props = new Properties();
        props.load(newValues);
        return processProperties(props);
    }

    @Override
    protected Object handleGetObject(String key) {
        if (key == null) {
            throw new NullPointerException();
        }
        return values.get(key);
    }


    /**
     * Returns an <code>Enumeration</code> of the keys contained in
     * this <code>ResourceBundle</code> and its parent bundles.
     *
     * @return an <code>Enumeration</code> of the keys contained in
     * this <code>ResourceBundle</code> and its parent bundles.
     * @see #keySet()
     */
    @Override
    public Enumeration<String> getKeys() {
        ResourceBundle parent = this.parent;
        return new ResourceBundleEnumeration(values.keySet(),
                (parent != null) ? parent.getKeys() : null);
    }
}
