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

//页面滚动到顶部
// $('.scroll-to-top').bind('click', function() {
//   window.scrollTo({
//       left: 0,
//       top: 0,
//       behavior: 'smooth'
//   })
// });

// $('.news-item').bind('click', function() {
//   window.location = './chember_news_detail.html';
// });