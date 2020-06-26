package com.drew.surfphotos.generation.component;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Компонент в зависимости от настроек позволяет создать полноценный объект EJB контайнера
 */
public abstract class AbstractEnvironmentGenerator {
    private final Properties environment = new Properties();
    private final Map<String, String> variables = new HashMap<>();

    protected final void execute() throws Exception {
        Map<String, Object> properties = setupProperties();
        try (EJBContainer ec = EJBContainer.createEJBContainer(properties)) {
            Context ctx = ec.getContext();
            ctx.bind("inject", this);
            generate();
        }
    }

    protected abstract void generate() throws Exception;

    private Map<String, Object> setupProperties() throws IOException {
        Map<String, Object> properties = new HashMap<>();
        readEnvironmentProperties();
        setupVariables();
        setupClasspathEnvironmentProperties(properties);
        properties.put(EJBContainer.MODULES, getModulePath());
        return properties;
    }

    private void readEnvironmentProperties() throws IOException {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("environment.properties")) {
            environment.load(in);
        }
    }

    private void setupVariables() {
        String userHome = System.getProperty("user.home");
        variables.put("${M2_LOCAL}", userHome + "/.m2/repository");
    }

    private void setupClasspathEnvironmentProperties(Map<String, Object> properties) throws IOException {
        for (Map.Entry<Object, Object> entry : environment.entrySet()) {
            properties.put((String) entry.getKey(), entry.getValue());
        }
    }

    private File[] getModulePath() {
        List<File> files = new ArrayList<>();
        String[] modules = environment.getProperty(EJBContainer.MODULES).split(",");
        for (String module : modules) {
            files.add(new File(resolveModule(module)));
        }
        files.add(new File("target/classes"));
        return files.toArray(new File[files.size()]);
    }

    private String resolveModule(String module) {
        String result = module;
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            result = result.replace(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
