jQuery(document).ready(function($) {
  $('.theme-poptit .close').click(function(){
    $('.theme-popover-mask').fadeOut(100);
    $('.theme-popover').slideUp(200);
  })
})