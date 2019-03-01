package com.vaadin.addons.client;

import com.google.gwt.user.client.ui.Widget;
import com.vaadin.addons.AutocompleteOffExtension;
import com.vaadin.client.BrowserInfo;
import com.vaadin.client.ServerConnector;
import com.vaadin.client.extensions.AbstractExtensionConnector;
import com.vaadin.client.ui.AbstractFieldConnector;
import com.vaadin.client.ui.VDateField;
import com.vaadin.client.ui.datefield.AbstractDateFieldConnector;
import com.vaadin.shared.ui.Connect;

@Connect(AutocompleteOffExtension.class)
public class AutocompleteOffExtensionConnector
        extends AbstractExtensionConnector {

    @Override
    protected void extend(ServerConnector target) {

        /**
         * VDateField input field is wrapped inside the main widget, need to
         * handle separately from other types of input widgets
         */
        if (target instanceof AbstractDateFieldConnector) {
            VDateField field;
            field = ((AbstractDateFieldConnector) target).getWidget();
            if (BrowserInfo.get().isChrome()) {
                field.getElement().getFirstChildElement()
                        .setAttribute("autocomplete", "off");
            } else {
                field.getElement().getFirstChildElement()
                        .setAttribute("autocomplete", Math.random() + "");
            }
        } else {
            Widget field;
            field = ((AbstractFieldConnector) target).getWidget();

            if (BrowserInfo.get().isChrome()) {
                field.getElement().setAttribute("autocomplete", "off");
            } else {
                field.getElement().setAttribute("autocomplete",
                        Math.random() + "");
            }
        }

    }

}
