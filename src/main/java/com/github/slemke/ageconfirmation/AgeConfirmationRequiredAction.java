package com.github.slemke.ageconfirmation;

import org.keycloak.authentication.RequiredActionContext;
import org.keycloak.authentication.RequiredActionProvider;
import org.keycloak.models.UserModel;

import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;

public class AgeConfirmationRequiredAction implements RequiredActionProvider {

    public static final String PROVIDER_ID = "age-confirmation";

    private static final String TEMPLATE = "age_confirmation.ftl";
    
    private static final String USER_ATTRIBUTE_KEY = "ageConfirmed";

    private static final String FORM_PARAMETER_KEY = "accept";

    private String age;

    public AgeConfirmationRequiredAction(String age) {
        this.age = age;
    }

    @Override
    public void evaluateTriggers(RequiredActionContext context) {
        UserModel user = context.getUser();
        final String ageConfirmed = user.getFirstAttribute(USER_ATTRIBUTE_KEY);
        if (!Boolean.valueOf(ageConfirmed)) {
            user.addRequiredAction(PROVIDER_ID);
        }
    }

    @Override
    public void requiredActionChallenge(RequiredActionContext context) {
        Response response = context.form()
            .setAttribute("age", age)
            .createForm(TEMPLATE);
        context.challenge(response);
    }

    @Override
    public void processAction(RequiredActionContext context) {
        MultivaluedMap<String, String> decodedFormParameters = context.getHttpRequest().getDecodedFormParameters();
        Boolean accepted = decodedFormParameters.containsKey(FORM_PARAMETER_KEY);
        if (!accepted) {
            context.failure();
            return;
        }
        context.getUser().setSingleAttribute(USER_ATTRIBUTE_KEY, String.valueOf(accepted));
        context.success();
    }
    
    @Override
    public void close() { }
}