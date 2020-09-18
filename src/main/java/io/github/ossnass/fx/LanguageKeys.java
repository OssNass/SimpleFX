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

/**
 * This interface provides a set of keys that must be present in every language file used by this library.
 */
public interface LanguageKeys {
    /**
     * The template for stage title key in the language file
     * <p>
     * Used with {@link String#format(String, Object...) to format correctly the title of the stage using the {@link ControllerInfo#Id()}}
     * value to identify
     */
    String LANG_STAGE_TITLE = "STAGE.%s.TITLE";

    /**
     * The key of the language name
     */
    String LANG_NAME = "LANG.NAME";

    /**
     * The key of the language short name
     */
    String LANG_SHORT = "LANG.SHORT";

    /**
     * The key of the language name of country short key
     */
    String LANG_COUNTRY_SHORT = "LANG.COUNTRY";

    /**
     * The key for the direction of the language
     * <p>
     * must be <b>LTR</b> for left to right languages, or <b>RTL</b> for right to left languages
     */
    String LANG_LAYOUT_DIRECTION = "LANG.DIR";

    /**
     * The error message title key
     */
    String MESSAGE_TITLE_ERROR = "MESSAGE.TITLE.ERROR";
    /**
     * The warning message title key
     */
    String MESSAGE_TITLE_WARNING = "MESSAGE.TITLE.WARNING";

    /**
     * The info message title key
     */
    String MESSAGE_TITLE_INFO = "MESSAGE.TITLE.INFO";

    /**
     * The confirm dialogue title key
     */
    String MESSAGE_TITLE_CONFIRM = "MESSAGE.TITLE.CONFIRM";
}

