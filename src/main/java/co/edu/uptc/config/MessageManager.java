package co.edu.uptc.config;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * MessageManager — Singleton que centraliza el acceso a los textos de la UI.
 *
 * El idioma se determina desde AppConfig (clave "app.language").
 * Si la clave no existe en el bundle, retorna [clave] visible
 * para que el error sea evidente durante el desarrollo.
 *
 * Uso:
 * MessageManager.msg("menu.main.title")
 * MessageManager.msg("person.input.name", 2, 30)
 */
public class MessageManager {

    private static MessageManager instance;
    private ResourceBundle bundle;

    private static final String BUNDLE_BASE = "i18n/messages";

    private MessageManager() {
        String lang = AppConfig.getInstance().getAppLanguage();
        loadBundle(Locale.forLanguageTag(lang));
    }

    public static MessageManager getInstance() {
        if (instance == null) {
            instance = new MessageManager();
        }
        return instance;
    }

    private void loadBundle(Locale locale) {
        try {
            bundle = ResourceBundle.getBundle(BUNDLE_BASE, locale);
        } catch (MissingResourceException e) {
            // Si el idioma no existe, usa español por defecto
            bundle = ResourceBundle.getBundle(BUNDLE_BASE, new Locale("es"));
        }
    }

    public String get(String key) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            return "[" + key + "]";
        }
    }

    public String get(String key, Object... args) {
        return MessageFormat.format(get(key), args);
    }

    // Método estático de conveniencia — evita getInstance() repetitivo
    public static String msg(String key) {
        return getInstance().get(key);
    }

    public static String msg(String key, Object... args) {
        return getInstance().get(key, args);
    }

    public void changeLocale(String lang) {
        loadBundle(Locale.forLanguageTag(lang));
    }
}