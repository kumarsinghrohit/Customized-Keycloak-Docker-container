$(document).ready(function() {

  if($('input[name="user.attributes.newsletter"]')){
    if($('input[name="user.attributes.newsletter"]').val() == "checked") {
      $('input[name="newsletter"]').prop('checked', true);
    } else {
      $('input[name="newsletter"]').prop('checked', false);
    }
  }

  $("#newsletter").on('click', function(event) {
    if($("#newsletter").is(":checked")){
      $('input[name="user.attributes.newsletter"]').val("checked");
    } else {
      $('input[name="user.attributes.newsletter"]').val("");
    }
  });

  $("#show_hide_password a").on('click', function(event) {
      event.preventDefault();
      if($('#show_hide_password input').attr("type") == "text"){
          $('#show_hide_password input').attr('type', 'password');
          $('#show_password_icon').removeClass( "password-eye" );
          $('#show_password_icon').addClass( "password-eye-slash" );
      }else if($('#show_hide_password input').attr("type") == "password"){
          $('#show_hide_password input').attr('type', 'text');
          $('#show_password_icon').removeClass( "password-eye-slash" );
          $('#show_password_icon').addClass( "password-eye" );
      }
  });

  $("#show_hide_password_confirm a").on('click', function(event) {
      event.preventDefault();
      if($('#show_hide_password_confirm input').attr("type") == "text"){
          $('#show_hide_password_confirm input').attr('type', 'password');
          $('#show_password_confirm_icon').removeClass( "password-eye" );
          $('#show_password_confirm_icon').addClass( "password-eye-slash" );
      }else if($('#show_hide_password_confirm input').attr("type") == "password"){
          $('#show_hide_password_confirm input').attr('type', 'text');
          $('#show_password_confirm_icon').removeClass( "password-eye-slash" );
          $('#show_password_confirm_icon').addClass( "password-eye" );
      }
  });
});

$(() => {
  $(document).on("click", function() {
    $(".application-alert").css("opacity", 0);
  });

  $(".application-alert").on("click", function(e) {
    e.stopPropagation();
    return false;
  });

  $(".application-alert__close-button").on("click", function() {
    $(this)
      .parents(".application-alert").css("opacity", 0);
  });
});