package com.vaadin.addons.demo;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.annotation.WebServlet;

import com.vaadin.addons.AutocompleteOffExtension;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Binder;
import com.vaadin.data.Converter;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;
import com.vaadin.data.validator.DateRangeValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

@Theme("demo")
@Title("AutocompleteOffExtension Demo")
@SuppressWarnings("serial")
public class DemoUI extends UI {

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class)
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        HorizontalLayout hl = new HorizontalLayout();
        hl.setWidth("100%");
        hl.setSpacing(true);

        hl.addComponent(createFormLayout("Use default configuration", false, null));
        hl.addComponent(createFormLayout("Random number for autocomplete attribute", true, null));
        hl.addComponent(createFormLayout("Manual autocomplete attribute value", false, "test"));

        setContent(hl);
    }

    private FormLayout createFormLayout(String caption, boolean useRandomAttributeValue, String manualAttributeValue) {
        Binder<Person> binder = new Binder<>(Person.class);

        TextField t1 = new TextField("Required");
        t1.setPlaceholder("Write something");

        final TextField t2 = new TextField("Autocomplete off");
        t2.setPlaceholder("Write something");

        final DateField df = new DateField("Autocomplete off");
        df.setPlaceholder("Date something");

        // set autocomplete off attribute for t2 and df
        if (useRandomAttributeValue) {
            AutocompleteOffExtension.setAutocompleteOffWithRandomNumber(t2);
            AutocompleteOffExtension.setAutocompleteOffWithRandomNumber(df);
        } else if (manualAttributeValue!=null) {
            AutocompleteOffExtension.setAutocompleteOffWithManualValue(t2, manualAttributeValue);
            AutocompleteOffExtension.setAutocompleteOffWithManualValue(df, manualAttributeValue);
        } else {
            AutocompleteOffExtension.setAutocompleteOff(t2);
            AutocompleteOffExtension.setAutocompleteOff(df);
        }

        // bind fields with validators to test nothing breaks
        binder.forField(t1).asRequired("You need to do this")
                .withNullRepresentation("")
                .withValidator(
                        new StringLengthValidator("Name must be 2-10", 2, 10))
                .bind("firstName");

        binder.forField(t2).asRequired("You need to do this")
                .withNullRepresentation("")
                .withValidator(
                        new StringLengthValidator("Name must be 2-10", 2, 10))
                .bind("lastName");

        binder.forField(df).asRequired("You need to do this")
                .withValidator(
                        new DateRangeValidator("Needs to be before this date",
                                LocalDate.MIN, LocalDate.now()))
                .withNullRepresentation(LocalDate.now())
                .withConverter(new Converter<LocalDate, Date>() {
                    @Override
                    public Result<Date> convertToModel(LocalDate value,
                                                       ValueContext context) {
                        Date date = Date
                                .from(value.atStartOfDay(ZoneId.systemDefault())
                                        .toInstant());
                        return Result.ok(date);
                    }

                    @Override
                    public LocalDate convertToPresentation(Date value,
                            ValueContext context) {
                        return value.toInstant().atZone(ZoneId.systemDefault())
                                .toLocalDate();
                    }
                }).bind("dob");

        final Label label = new Label();
        binder.addValueChangeListener(e -> {
            boolean valid = binder.validate().isOk();
            label.setValue(valid ? "YAY" : "NAY");
        });
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, -20);
        binder.setBean(new Person("Lorem", "Ipsum", c.getTime()));

        final FormLayout layout = new FormLayout();
        layout.setCaption(caption);
        layout.setStyleName("demoContentLayout");
        layout.setSizeFull();
        layout.addComponents(t1, t2, df, label);
        return layout;
    }

}
