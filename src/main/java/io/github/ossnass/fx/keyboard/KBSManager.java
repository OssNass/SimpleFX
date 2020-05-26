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

package io.github.ossnass.fx.keyboard;

import javafx.scene.Node;
import javafx.scene.input.KeyEvent;

import java.util.HashMap;
import java.util.Iterator;


/**
 * This class is responsable for managing keyboard shortcuts for each controller.
 * <p>
 * You can use it by calling {@link KBSManager#addKeyboardShortcut(String, KBShortcut)}
 */
public class KBSManager {
    protected final HashMap<String, KBShortcut> keys;
    protected Node root;

    /**
     * Creates a new keyboard shortcut manager
     *
     * @param root the root element to which we need to attach the manger to.
     *             if the root is disabled the keyboard shortcut manager is disabled
     */
    public KBSManager(Node root) {
        if (root == null) {
            throw new IllegalArgumentException("The root node of KBSManager cannot be null");
        }
        this.root = root;
        this.keys = new HashMap<>();
        root.addEventHandler(KeyEvent.KEY_RELEASED, this::handleKeys);
    }


    /**
     * This is an internal function and must not be called by the user
     *
     * @param e the event
     */
    private void handleKeys(KeyEvent e) {
        if (!this.root.isDisabled()) {
            Iterator<KBShortcut> keysItr = this.keys.values().iterator();
            while (keysItr.hasNext()) {
                KBShortcut kbs = keysItr.next();
                if (kbs.checkAction(e)) {
                    break;
                }
            }
        }
    }


    /**
     * Adds a new keyboard shortcut to shortcut list
     *
     * @param ID  the id of the new shortcut to add,cannot be null or empty string
     * @param kbs the new keyboard shortcut to add
     * @return true if successfully added, false if any of the parameters is null or if the ID already exists.
     */
    public boolean addKeyboardShortcut(String ID, KBShortcut kbs) {
        if (ID != null && !ID.trim().equals("") && kbs != null) {
            if (this.keys.containsKey(ID))
                return false;
            this.keys.put(ID, kbs);
            return true;
        }
        return false;
    }


    /**
     * Removes an existing keyboard shortcut from the list
     *
     * @param ID the existing keyboard shortcut ID to remove
     */
    public void removeKeyboardShortcut(String ID) {
        if (ID != null) {
            this.keys.remove(ID);
        }
    }

    /**
     * Returns the shortcut corresponding to the specified ID
     *
     * @param ID the id of the shortcut
     * @return the shortcut corresponding to the specified ID, null if the ID is null or
     * empty string or doesn't exists in the shortcuts
     */
    public KBShortcut getShortcut(String ID) {
        if (ID == null || ID.trim().equals(""))
            return null;
        return this.keys.get(ID);
    }

    /**
     * Clears all the keyboard shortcuts from the list
     */
    public void clearShortcuts() {
        this.keys.clear();
    }
}
