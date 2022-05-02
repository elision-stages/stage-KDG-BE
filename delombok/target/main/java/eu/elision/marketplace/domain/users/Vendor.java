package eu.elision.marketplace.domain.users;

import javax.persistence.Entity;

/**
 * This class contains the extra info of a vendor
 */
@Entity
public class Vendor extends User {
    private String logo;
    private String theme;
    private String introduction;
    private String vatNumber;

    @SuppressWarnings("all")
    public String getLogo() {
        return this.logo;
    }

    @SuppressWarnings("all")
    public String getTheme() {
        return this.theme;
    }

    @SuppressWarnings("all")
    public String getIntroduction() {
        return this.introduction;
    }

    @SuppressWarnings("all")
    public String getVatNumber() {
        return this.vatNumber;
    }

    @SuppressWarnings("all")
    public void setLogo(final String logo) {
        this.logo = logo;
    }

    @SuppressWarnings("all")
    public void setTheme(final String theme) {
        this.theme = theme;
    }

    @SuppressWarnings("all")
    public void setIntroduction(final String introduction) {
        this.introduction = introduction;
    }

    @SuppressWarnings("all")
    public void setVatNumber(final String vatNumber) {
        this.vatNumber = vatNumber;
    }
}
