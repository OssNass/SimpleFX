package org.ocomp.fx.keyboard;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

import java.lang.reflect.Method;

/**
 * This class represents the action that can be taken by a keyboard shortcut.
 * <p>
 * This class requires a function that implements {@link EventHandler} interface,
 * meaning for the time being it is associated with javafx event handlers
 * with plans to expand into even broader method signatures.
 *
 * @param <T>
 */
public class KBShortcut<T extends ActionEvent> {
    protected KeyCombination kbs;
    protected Node node;
    protected EventHandler<T> e;
    protected Method m;
    /**
     * Creating a new Keyboard shortcut
     *
     * @param node the node to attach the shortcut to in order to disable the shortcut if the node is disabled, can be null
     * @param kbs  the keyboard shortcut combination, cannot be null
     * @param e    the event handler, cannot be null
     */
    public KBShortcut(Node node, KeyCombination kbs, EventHandler<T> e) {
        if (kbs == null)
            throw new IllegalArgumentException("Key combination cannot be null");
        if (e == null) {
            throw new IllegalArgumentException("Event handler cannot be null");
        }
        this.node = node;
        this.kbs = kbs;
        this.e = e;
    }

    /**
     * This is an internal function and must be called by the user
     */
    boolean checkAction(KeyEvent ke) {
        if (this.node == null || (this.node.isVisible() && this.node.isManaged() && !this.node.isDisable())) {
            if (this.kbs.match(ke)) {
                this.e.handle(null);
                return true;
            }
        }
        return false;
    }
}

