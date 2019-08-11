//复制电话到剪贴板
$('.container').bind('click', function(event) {
  var target = event.target;
  if(target.getAttribute('class') === 'copyphone') {
    var contentHolder = target.previousSibling;
    // 创建 createRange 方法对象
    var range = document.createRange();

    // 创建 getSelection 方法对象，表示用户选择的文本范围或光标的当前位置。
    var selection = window.getSelection();
    
    // 通过 selection.removeAllRanges() 方法清除选择范围
    selection.removeAllRanges();
    
    // selectNodeContents 方法选择 contentHolder 子节点的内容
    range.selectNodeContents(contentHolder);
    
    // 一个区域（Range）对象将被增加到选区（Selection）当中。
    // 将刚刚选中的文本添加到 selection 
    selection.addRange(range);

    // execCommand 中的 copy 方法拷贝刚刚选到的内容
    document.execCommand('copy');
    
    // 重新初始化区域对象 range
    selection.removeAllRanges();
    alert('复制成功！');
  }
});