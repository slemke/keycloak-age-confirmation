<#import "template.ftl" as layout>
<@layout.registrationLayout displayMessage=false; section>
    <#if section = "header">
        ${msg("age_confirmation")}
    <#elseif section = "form">
        <p id="kc-backup-email-text">
            ${msg("age_confirmation_message", age)}
        </p>
        <form class="${properties.kcFormClass!}" action="${url.loginAction}" method="POST">
            <div class="${properties.kcFormGroupClass!}">
                <div id="kc-form-buttons" class="${properties.kcFormButtonsClass!}">
                    <button 
                        class="${properties.kcButtonClass!} ${properties.kcButtonPrimaryClass!} ${properties.kcButtonLargeClass!}" 
                        type="submit" 
                        name="accept"
                    >
                        ${msg("doAccept")}
                    </button>
                    <button 
                        class="${properties.kcButtonClass!} ${properties.kcButtonDefaultClass!} ${properties.kcButtonLargeClass!}"
                        type="submit"
                        name="cancel"
                    >
                        ${msg("doDecline")}
                    </button>
                </div>
            </div>
        </form>
    </#if>
</@layout.registrationLayout>