<#import "template.ftl" as layout>
<@layout.registrationLayout; section>
    <#if section = "header">

    <#elseif section = "form">
        
		<div class="register__title">${msg("registerTitle")}</div>
		
        <div class="${properties.kcFormOptionsWrapperClass!}">
            <span>${kcSanitize(msg("backToLoginPrefix"))} <a href="${url.loginUrl}">${kcSanitize(msg("backToLogin"))}</a></span>
        </div>


        <form id="kc-register-form" class="${properties.kcFormClass!}" action="${url.registrationAction}" method="post" autocomplete="off">
            <div class="${properties.kcFormGroupClass!} ${messagesPerField.printIfExists('email',properties.kcFormGroupErrorClass!)} d-flex align-items-center">
                    <input type="text" tabindex="1" id="email" class="${properties.kcInputClass!}" name="email" value="${(register.formData.email!'')}" />
                    <div class="application-text-input__label">${msg("emailAddress")}</div>
            </div>

          <#if !realm.registrationEmailAsUsername>
            <div class="${properties.kcFormGroupClass!} ${messagesPerField.printIfExists('username',properties.kcFormGroupErrorClass!)} d-flex align-items-center">
				<input type="text" tabindex="1" id="username" class="${properties.kcInputClass!}" name="username" value="${(register.formData.username!'')}" autocomplete="username"/>
				<div class="application-text-input__label">${msg("emailAddress")}</div>
            </div>
          </#if>


          <#if passwordRequired>
            <div id="show_hide_password" class="${properties.kcFormGroupClass!} ${messagesPerField.printIfExists('password',properties.kcFormGroupErrorClass!)} d-flex align-items-center">
			  <input type="password" tabindex="2" id="password" class="${properties.kcInputClass!} password" name="password" />
			  <div class="application-text-input__label">${msg("password")}</div>
			  <a href=""><span id="show_password_icon" class="password-eye-slash inputIcon"></span></a>
            </div>

            <div id="show_hide_password_confirm" class="${properties.kcFormGroupClass!} ${messagesPerField.printIfExists('password-confirm',properties.kcFormGroupErrorClass!)} d-flex align-items-center">
				<input type="password" tabindex="3" id="password-confirm" class="${properties.kcInputClass!} password" name="password-confirm" />
				<div class="application-text-input__label">${msg("passwordConfirm")}</div>
				<a href=""><span id="show_password_confirm_icon" class="password-eye-slash inputIcon"></span></a>
            </div>
            </#if>

            <div class="form-group">
               <div class="${properties.kcInputWrapperClass!} newsletterWrapper d-flex">
                   <input type="checkbox" tabindex="4" id="newsletter" name="newsletter"/>
				   <label for="newsletter"></label>
                   <div class="infoText">${msg("newsletter.text")}</div>				   				   
                   <input type="hidden" id="user.attributes.newsletter" name="user.attributes.newsletter" value="${(register.formData['user.attributes.newsletter']!'')}"/>
               </div>
            </div>

            <#if recaptchaRequired??>
            <div class="form-group">
                <div class="${properties.kcInputWrapperClass!}">
                    <div class="g-recaptcha" data-size="compact" data-sitekey="${recaptchaSiteKey}"></div>
                </div>
            </div>
            </#if>

            <div class="">
                <div id="kc-form-buttons" class="">
                    <input id="submitButton" tabindex="5" class="${properties.kcButtonClass!} ${properties.kcButtonPrimaryClass!} ${properties.kcButtonBlockClass!} ${properties.kcButtonLargeClass!}" type="submit" value="${msg("doRegister")}"/>
                </div>
            </div>

            <div class="infoText">
                ${msg("register.text")?no_esc}
            </div>

            <input type="hidden" id="firstName" name="firstName" value="-" />
            <input type="hidden" id="lastName" name="lastName" value="-" />
        </form>
    </#if>
</@layout.registrationLayout>
