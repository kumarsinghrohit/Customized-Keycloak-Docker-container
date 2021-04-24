<#macro registrationLayout bodyClass="" displayInfo=false displayMessage=true displayWide=false>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" class="${properties.kcHtmlClass!}">

<head>
    <meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta name="robots" content="noindex, nofollow">

    <#if properties.meta?has_content>
        <#list properties.meta?split(' ') as meta>
            <meta name="${meta?split('==')[0]}" content="${meta?split('==')[1]}"/>
        </#list>
    </#if>
    <title>My&reg; Application</title>
    <link rel="icon" href="${url.resourcesPath}/img/favicon.ico" />
    <link rel="stylesheet" href="${url.resourcesPath}/css/bootstrap.min.css">
    <#if properties.styles?has_content>
        <#list properties.styles?split(' ') as style>
            <link href="${url.resourcesPath}/${style}" rel="stylesheet" />
        </#list>
    </#if>
    <link href="https://fonts.googleapis.com/css?family=Muli:400,700,900&display=swap" rel="stylesheet" />    
</head>

<body class="${properties.kcBodyClass!}">
	<div id="navbar" class="main-header header--shadowed container-fluid d-flex justify-content-center m-0 py-0 nav--down ">
	  <link rel="stylesheet" href="${url.resourcesPath}/css/store-header.css" />
	  <div class="row">
		<div class="col-lg-3 col-sm-2 header__logo-col p-0 m-0">
		  <a href="/" class="d-flex justify-content-around align-items-center">
			<img class="header__logoimg" src="${url.resourcesPath}/img/application-store-logo.png" alt="" />
			<div class="header__title d-none d-md-block">
			  <div class="header__appname">Application</div>
			  <div class="header__tag d-none d-lg-block">Open Access to Software Technology</div>
			</div>
		  </a>
		</div>    
	  </div>
	</div>
	
	
  <#-- App-initiated actions should not see warning messages about the need to complete the action -->
  <#-- during login.                                                                               -->
  <#if displayMessage && message?has_content && (message.type != 'warning' || !isAppInitiatedAction??)>
      <div id="alert" class="application-alert application-alert--${message.type}">          
		<div class="col d-flex align-items-center p-0 m-0">
          <span class="kc-feedback-text">${kcSanitize(message.summary)?no_esc}</span>		  		  		  		  		  
		 </div>
		 <div class="col p-0 m-0 d-flex align-items-center justify-content-end">
			<div class="application-alert__close-button"><span class="close-circle application-pointer"></span></div>
		 </div>
      </div>
  </#if>
  
  
  <div class="container-fluid p-0 m-0">
	  <main class="store__body" role="main">
	  
		  <div class="${properties.kcLoginClass!}">
			<div id="kc-header" class="${properties.kcHeaderClass!}">
			  <div id="kc-header-wrapper" class="${properties.kcHeaderWrapperClass!}">${kcSanitize(msg("loginTitleHtml",(realm.displayNameHtml!'')))?no_esc}</div>
			</div>
			<div class="${properties.kcFormCardClass!} <#if displayWide>${properties.kcFormCardAccountClass!}</#if>">
			  <header class="${properties.kcFormHeaderClass!}">
				<#if realm.internationalizationEnabled  && locale.supported?size gt 1>
					<div id="kc-locale">
						<div id="kc-locale-wrapper" class="${properties.kcLocaleWrapperClass!}">
							<div class="kc-dropdown" id="kc-locale-dropdown">
								<a href="#" id="kc-current-locale-link">${locale.current}</a>
								<ul>
									<#list locale.supported as l>
										<li class="kc-dropdown-item"><a href="${l.url}">${l.label}</a></li>
									</#list>
								</ul>
							</div>
						</div>
					</div>
				</#if>
				<h1 id="kc-page-title"><#nested "header"></h1>
			  </header>
			  <div id="kc-content">
				<div id="kc-content-wrapper">
				  <#nested "form">
				  <#if displayInfo>
					  <div id="kc-info" class="${properties.kcSignUpClass!}">
						  <div id="kc-info-wrapper" class="${properties.kcInfoAreaWrapperClass!}">
							  <#nested "info">
						  </div>
					  </div>
				  </#if>
				</div>
			  </div>
			</div>
		  </div>

	  </main>
  </div>
  
  
  <footer class="main-footer">
	  <link rel="stylesheet" href="${url.resourcesPath}/css/store-footer.css" />
	  <div class="main-footer__row">
		<div class="main-footer__link-container">
		  <a class="main-footer__link" href="#">
			&copy; 2020 Application&reg;
		  </a>
		</div>

		<div class="main-footer__link-container">
		  <a class="main-footer__link" href="/terms/website">
			Website Terms of Use
		  </a>
		</div>

		<div class="main-footer__link-container">
		  <a class="main-footer__link" href="/terms/store">
			Store Terms of Use
		  </a>
		</div>

		<div class="main-footer__link-container">
		  <a class="main-footer__link" href="/legal/privacy">
			Privacy Policy
		  </a>
		</div>

		<div class="main-footer__link-container">
		  <a class="main-footer__link" href="/imprint/website">
			Imprint
		  </a>
		</div>

		<div class="main-footer__link-container">
		  <a class="main-footer__link" href="#">
			All prices exclude VAT
		  </a>
		</div>
	  </div>
	</footer>


  <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
  <script src="${url.resourcesPath}/js/bootstrap.min.js"></script>

<#if properties.scripts?has_content>
    <#list properties.scripts?split(' ') as script>
        <script src="${url.resourcesPath}/${script}" type="text/javascript"></script>
    </#list>
</#if>
<#if scripts??>
    <#list scripts as script>
        <script src="${script}" type="text/javascript"></script>
    </#list>
</#if>
</body>
</html>
</#macro>
