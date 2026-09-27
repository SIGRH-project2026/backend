package sn.gainde2000.backenmfpai.entities.enums;

import org.apache.commons.lang3.EnumUtils;

/**
 * @author Abdou Karim CISSOKHO
 * @created 08/11/2023-16:25
 * @project gestion_courriers
 */
public enum FileCode {
    CONTRACT_LOADED("Contrat chargé");
    private final String formattedName;

    FileCode(String formattedName) {
        this.formattedName = formattedName;
    }

    public static boolean findByName(String name) {
        return EnumUtils.isValidEnum(FileCode.class, name.toUpperCase());
    }

    public String getFormattedName() {
        return formattedName;
    }
}
