//轮播图自动播放
$('.carousel').carousel();

//复制链接到剪贴板

var clipboard = new ClipboardJS('#urlcopy', {
  text: function () {
      var url = window.location.href;
      return url;
  }
});

clipboard.on('success', function (e) {
  alert("链接复制成功");
});

clipboard.on('error', function (e) {
  alert(e);
});


var scrollTop = document.querySelector('.scroll-to-top');
scrollTop.addEventListener('click', function () {
    $(document).scrollTop(0);
});

// $('.news-item').bind('click', function() {
//   window.location = './chember_news_detail.html';
// });