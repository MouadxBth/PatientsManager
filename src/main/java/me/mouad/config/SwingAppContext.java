package me.mouad.config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SwingAppContext extends AnnotationConfigApplicationContext {

    public SwingAppContext(Class<?>... annotatedClasses) {
        super(annotatedClasses);
    }

    @Override
    protected void initPropertySources() {
        // Ignore the "jmxEnabled" property to avoid an error
        getEnvironment().setIgnoreUnresolvableNestedPlaceholders(true);
        super.initPropertySources();
    }

}
