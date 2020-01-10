package com.vaadin.addons;

import com.vaadin.addons.client.AutocompleteOffExtensionState;
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

    /**
     * Set autocomplete attribute off for input field component with
     * random number autocomplete attribute value
     *
     * @param field
     *            component extending AbstractField
     */
    public static void setAutocompleteOffWithRandomNumber(AbstractField<?> field) {
        AutocompleteOffExtension ex = new AutocompleteOffExtension();
        ex.getState().useRandomNumber = true;
        ex.extend(field);
    }

    /**
     * Set autocomplete attribute off for input field component with
     * given value for autocomplete attribute
     *
     * @param field
     *            component extending AbstractField
     * @param attributeValue
     *            a value for field's autocomplete attribute
     */
    public static void setAutocompleteOffWithManualValue(AbstractField<?> field, String attributeValue) {
        AutocompleteOffExtension ex = new AutocompleteOffExtension();
        ex.getState().attributeValue = attributeValue;
        ex.extend(field);
    }

    @Override
    protected AutocompleteOffExtensionState getState() {
        return (AutocompleteOffExtensionState) super.getState();
    }
}
