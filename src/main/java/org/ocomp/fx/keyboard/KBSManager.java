package org.ocomp.fx.keyboard;

import javafx.scene.Node;
import javafx.scene.input.KeyEvent;

import java.util.HashSet;
import java.util.Iterator;


/**
 * This class is responsable for managing keyboard shortcuts for each controller.
 * <p>
 * You can use it by calling {@link KBSManager#addKeyboardShortcut(KBShortcut)}
 */
public class KBSManager {
    protected final HashSet<KBShortcut> keys;
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
        this.keys = new HashSet<>();
        root.addEventHandler(KeyEvent.KEY_RELEASED, this::handleKeys);
    }


    /**
     * This is an internal function and must not be called by the user
     *
     * @param e
     */
    private void handleKeys(KeyEvent e) {
        if (!this.root.isDisabled()) {
            Iterator<KBShortcut> keysItr = this.keys.iterator();
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
     * @param kbs the new keyboard shortcut to add
     */
    public void addKeyboardShortcut(KBShortcut kbs) {
        if (kbs != null) {
            this.keys.add(kbs);
        }
    }


    /**
     * Removes an existing keyboard shortcut from the list
     *
     * @param kbs the existing keyboard shortcut to remove
     */
    public void removeKeyboardShortcut(KBShortcut kbs) {
        if (kbs != null) {
            this.keys.remove(kbs);
        }
    }

    /**
     * Removes an existing keyboard shortcut from the list
     *
     * @param index the existing keyboard shortcut index to remove
     */

    public void removeKeyboardShortcut(int index) {
        if (index >= 0) {
            this.keys.remove(index);
        }
    }

    /**
     * Clears all the keyboard shortcuts from the list
     */
    public void clearShortcuts() {
        this.keys.clear();
    }
}
