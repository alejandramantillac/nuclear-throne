package com.example.nuclearthrone.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.input.KeyEvent;

// Clase encargada de registrar los eventos de teclado
public class KeyboardControl {

    public static BooleanProperty wPressed = new SimpleBooleanProperty(false);
    public static BooleanProperty sPressed = new SimpleBooleanProperty(false);
    public static BooleanProperty dPressed = new SimpleBooleanProperty(false);
    public static BooleanProperty aPressed = new SimpleBooleanProperty(false);

    public static void onKeyPressed(KeyEvent key) {
        switch (key.getCode()) {
            case W:
                wPressed.set(true);
                break;
            case S:
                sPressed.set(true);
                break;
            case D:
                dPressed.set(true);
                break;
            case A:
                aPressed.set(true);
                break;
            default:
                break;
        }
    }

    public static void onKeyReleased(KeyEvent key) {
        switch (key.getCode()) {
            case W:
                wPressed.set(false);
                break;
            case S:
                sPressed.set(false);
                break;
            case D:
                dPressed.set(false);
                break;
            case A:
                aPressed.set(false);
                break;
            default:
                break;
        }
    }
}
