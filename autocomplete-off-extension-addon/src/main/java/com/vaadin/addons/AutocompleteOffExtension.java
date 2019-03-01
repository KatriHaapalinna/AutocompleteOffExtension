package com.vaadin.addons;

import com.vaadin.server.AbstractExtension;
import com.vaadin.ui.AbstractField;

public class AutocompleteOffExtension extends AbstractExtension {

    /**
     * Set autocomplete attribute off for input field component
     * 
     * @param field
     *            component extending AbstractField
     */
    public static void setAutocompleteOff(AbstractField<?> field) {
        AutocompleteOffExtension ex = new AutocompleteOffExtension();
        ex.extend(field);
    }
}
