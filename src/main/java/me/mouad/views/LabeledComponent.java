package me.mouad.views;

import javax.swing.*;
import java.awt.*;

public interface LabeledComponent<C extends Component> {

    JLabel getLabel();

    C getComponent();

    default void bounds(int x, int y, int width, int height) {
        if (getLabel() != null)
            getLabel().setBounds(x, y, width, height);
        if (getComponent() != null)
            getComponent().setBounds(x, y, width, height);
    }

    default void addTo(Container container) {
        if (container == null)
            return;
        if (getLabel() != null)
            container.add(getLabel());
        if (getComponent() != null)
            container.add(getComponent());
    }
    static <C extends Component> LabeledComponent<C> create(String label, C component) {
        return new LabeledComponent<>() {
            @Override
            public JLabel getLabel() {
                return new JLabel(label);
            }

            @Override
            public C getComponent() {
                return component;
            }
        };
    }

}
