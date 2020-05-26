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
 * @param <T> the type of action event
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

