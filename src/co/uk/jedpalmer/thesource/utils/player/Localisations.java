package co.uk.jedpalmer.thesource.utils.player;

import org.bukkit.entity.*;

import java.lang.reflect.*;
/**
 * Created by peraldon on 30/06/2015.
 * Thanks to TeeePeee for providing the base for this class. https://bukkit.org/members/teeepeee.90735069/
 */
public enum Localisations {
    AUSTRALIAN_ENGLISH("Australian English", "en_CA"/*"en_AU"*/),
    AFRIKAANS("Afrikaans", "af_ZA"),
    ARABIC("???????", "ar_SA"),
    BULGARIAN("?????????", "bg_BG"),
    CATALAN("Català", "ca_ES"),
    CZECH("?eština", "cs_CZ"),
    CYMRAEG("Cymraeg", "cy_GB"),
    DANISH("Dansk", "da_DK"),
    GERMAN("Deutsch", "de_DE"),
    GREEK("????????", "el_GR"),
    CANADIAN_ENGLISH("Canadian English", "en_CA"),
    ENGLISH("English", "en_CA"/*"en_GB"*/),
    PIRATE_SPEAK("Pirate Speak", "en_PT"),
    ESPERANTO("Esperanto", "eo_EO"),
    ARGENTINEAN_SPANISH("Español Argentino", "es_AR"),
    SPANISH("Español", "es_ES"),
    MEXICO_SPANISH("Español México", "es_MX"),
    URUGUAY_SPANISH("Español Uruguay", "es_UY"),
    VENEZUELA_SPANISH("Español Venezuela", "es_VE"),
    ESTONIAN("Eesti", "et_EE"),
    EUSKARA("Euskara", "eu_ES"),
    ENGLISH1("???? ???????", "en_CA"/*"fa_IR"*/),
    FINNISH("Suomi", "fi_FI"),
    TAGALOG("Tagalog", "fil_PH"),
    FRENCH_CA("Français", "fr_CA"),
    FRENCH("Français", "fr_FR"),
    GAEILGE("Gaeilge", "ga_IE"),
    GALICIAN("Galego", "gl_ES"),
    HEBREW("?????", "he_IL"),
    ENGLISH2("????????", "en_CA"/*"hi_IN"*/),
    CROATIAN("Hrvatski", "hr_HR"),
    HUNGARIAN("Magyar", "hu_HU"),
    ARMENIAN("???????", "hy_AM"),
    BAHASA_INDONESIA("Bahasa Indonesia", "id_ID"),
    ICELANDIC("Íslenska", "is_IS"),
    ITALIAN("Italiano", "it_IT"),
    JAPANESE("???", "ja_JP"),
    GEORGIAN("???????", "ka_GE"),
    KOREAN("???", "ko_KR"),
    KERNEWEK("Kernewek", "kw_GB"),
    ENGLISH3("????????", "en_CA"/*"ky_KG"*/),
    LINGUA_LATINA("Lingua latina", "la_LA"),
    LETZEBUERGESCH("Lëtzebuergesch", "lb_LU"),
    LITHUANIAN("Lietuvi?", "lt_LT"),
    LATVIAN("Latviešu", "lv_LV"),
    MALAY_NZ("Bahasa Melayu", "mi_NZ"),
    MALAY_MY("Bahasa Melayu", "ms_MY"),
    MALTI("Malti", "mt_MT"),
    NORWEGIAN("Norsk", "nb_NO"),
    DUTCH("Nederlands", "nl_NL"),
    NORWEGIAN_NYNORSK("Norsk nynorsk", "nn_NO"),
    NORWEGIAN1("Norsk", "no_NO"),
    OCCITAN("Occitan", "oc_FR"),
    PORTUGUESE_BR("Português", "pt_BR"),
    PORTUGUESE_PT("Português", "pt_PT"),
    QUENYA("Quenya", "qya_AA"),
    ROMANIAN("Român?", "ro_RO"),
    RUSSIAN("???????", "ru_RU"),
    ENGLISH4("Angli?tina", "en_CA"/*"sk_SK"*/),
    SLOVENIAN("Slovenš?ina", "sl_SI"),
    SERBIAN("??????", "sr_SP"),
    SWEDISH("Svenska", "sv_SE"),
    THAI("???????", "th_TH"),
    tlhIngan_Hol("tlhIngan Hol", "tlh_AA"),
    TURKISH("Türkçe", "tr_TR"),
    UKRAINIAN("??????????", "uk_UA"),
    VIETNAMESE("Ti?ng Vi?t", "vi_VI"),
    SIMPLIFIED_CHINESE("????", "zh_CN"),
    TRADITIONAL_CHINESE("????", "zh_TW"),
    POLISH("Polski", "pl_PL");

    private String name;
    private String code;

    private Localisations(String name, String code){
        this.name = name;
        this.code = code;
    }

    public String getName(){
        return name;
    }

    public String getCode(){
        return code;
    }

    public static Localisations getByCode(String code) {
        for (Localisations language : values()) {
            if (language.getCode().equalsIgnoreCase(code)) return language;
        }
        return null;
    }

    private static Method getMethod(String name, Class<?> clazz) {
        for (Method m : clazz.getDeclaredMethods()) {
            if (m.getName().equals(name)) return m;
        }
        return null;
    }

    public static Localisations getLanguage(Player p) {
        try {
            Object ep = getMethod("getHandle", p.getClass()).invoke(p, (Object[]) null);
            Field field = ep.getClass().getDeclaredField("locale");
            field.setAccessible(true);
            String language = (String) field.get(ep);
            return getByCode(language);
        }
        catch (Throwable t) {
            t.printStackTrace();
            return null;
        }
    }
}
