package com.vaadin.addons.client;

import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.addons.AutocompleteOffExtension;
import com.vaadin.client.ServerConnector;
import com.vaadin.client.extensions.AbstractExtensionConnector;
import com.vaadin.client.ui.AbstractFieldConnector;
import com.vaadin.client.ui.VDateField;
import com.vaadin.client.ui.datefield.AbstractDateFieldConnector;
import com.vaadin.shared.ui.Connect;

@Connect(AutocompleteOffExtension.class)
public class AutocompleteOffExtensionConnector
        extends AbstractExtensionConnector {

    private static final String AUTOCOMPLETE = "autocomplete";
    private static final String NAME = "name";

    @Override
    protected void extend(ServerConnector target) {

        /**
         * VDateField input field is wrapped inside the main widget, need to
         * handle separately from other types of input widgets
         */
        if (target instanceof AbstractDateFieldConnector) {
            VDateField field  = ((AbstractDateFieldConnector) target).getWidget();
            disableAutocomplete(field);
        } else {
            Widget field = ((AbstractFieldConnector) target).getWidget();
            disableAutocomplete(field);
            disableHistory(field);
        }

    }

    private void disableAutocomplete(UIObject field) {
        field.getElement().getFirstChildElement().setAttribute(AUTOCOMPLETE, getTimestamp());
    }

    private void disableHistory(Widget field) {
        field.getElement().getFirstChildElement().setAttribute(NAME, getTimestamp());
    }

    /**
     * We use this to prevent the browser from "magically" provide autocomplete functionality
     * @return a
     */
    private String getTimestamp() {
        return String.valueOf(System.currentTimeMillis());
    }

}
