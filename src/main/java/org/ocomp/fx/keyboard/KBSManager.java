package org.ocomp.fx.keyboard;

import javafx.scene.Node;
import javafx.scene.input.KeyEvent;

import java.util.HashMap;
import java.util.HashSet;
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
     * @param e
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
