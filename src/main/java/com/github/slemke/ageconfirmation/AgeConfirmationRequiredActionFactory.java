package com.github.slemke.ageconfirmation;

import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;
import org.keycloak.Config.Scope;
import org.keycloak.authentication.RequiredActionFactory;
import org.keycloak.authentication.RequiredActionProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.services.validation.Validation;

public class AgeConfirmationRequiredActionFactory implements RequiredActionFactory {

    private Logger logger = Logger.getLogger(AgeConfirmationRequiredActionFactory.class);

    private static final List<ProviderConfigProperty> configProperties = new ArrayList<ProviderConfigProperty>();

    private String age = "18";

    static {
        ProviderConfigProperty property;
        property = new ProviderConfigProperty();
        property.setName("age");
        property.setLabel("Age");
        property.setType(ProviderConfigProperty.STRING_TYPE);
        property.setHelpText("The age the user needs to confirm");
        configProperties.add(property);
    }

    @Override
    public List<ProviderConfigProperty> getConfigMetadata() {
        return configProperties;
    }

    @Override
    public RequiredActionProvider create(KeycloakSession session) {
        return new AgeConfirmationRequiredAction(age);
    }

    @Override
    public void init(Scope config) {
        String age = config.get("minimum-age");
        if(!Validation.isBlank(age) && isNumber(age)) {
            this.age = age;
        }
        logger.info("The configured minimum age of the age confirmation is " + this.age);
    }

    private Boolean isNumber(String age) {
        try {
            Integer.parseInt(age);
            return true;
        } catch(NumberFormatException exception) {
            logger.warn("Age confirmation configuration is invalid - using " + this.age + " years as default");
            return false;
        }
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {

    }

    @Override
    public void close() {

    }

    @Override
    public String getId() {
        return AgeConfirmationRequiredAction.PROVIDER_ID;
    }

    @Override
    public String getDisplayText() {
        return "Age Confirmation";
    }
}
