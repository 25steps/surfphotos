package com.drew.surfphotos.ejb.service.impl;

import com.drew.surfphotos.common.annotation.cdi.PropertiesSource;
import com.drew.surfphotos.ejb.service.TranslitConverter;
import net.sf.junidecode.Junidecode;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Map;
import java.util.Properties;

@ApplicationScoped
public class JunidecodeTranslitConverter implements TranslitConverter {
    private Properties properties;

    @Inject
    public void setProperties(@PropertiesSource("classpath:translit.properties") Properties properties) {
        this.properties = extendTranslitProperties(properties);
    }

    @Override
    public String translit(String text) {
        String result = customTranslit(text);
        return Junidecode.unidecode(result);
    }

    private String customTranslit(String text) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            String ch = String.valueOf(text.charAt(i));
            sb.append(properties.getProperty(ch, ch));
        }
        return sb.toString();
    }

    private Properties extendTranslitProperties(final Properties properties) {
        Properties result = (Properties) properties.clone();
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            String key = String.valueOf(entry.getKey());
            String value = String.valueOf(entry.getValue());
            if (value.length() <= 1) {
                result.setProperty(key.toUpperCase(), value.toUpperCase());
            } else {
                result.setProperty(key.toUpperCase(), Character.toUpperCase(value.charAt(0)) + value.substring(1));
            }
        }
        return result;
    }
}
