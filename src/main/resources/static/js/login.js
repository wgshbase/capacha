//var basePath = "http://localhost:8080/bestdata-captcha";

var basePath = window.location.href;

// 声明两个变量用以记录鼠标的点击此处以及对应的点击的坐标
var clickCount = 0;
var gaps = "";

//更换图片验证码
function changeImg(){
    clickCount = 0;
    gaps = "";
    $(".hiddenMark4Captcha1").css("display","none");
    $(".hiddenMark4Captcha2").css("display","none");
    $(".hiddenMark4Captcha3").css("display","none");
    var timestamp = new Date().getTime()+"_"+Math.random()*10000;

    $.ajax({
        url: basePath+"loginController/genImg.do",
        data:{"imgname":timestamp},
        type: "post",
        dataType: "json",
        success: function(resultMsg) {
            if(resultMsg.flag){
                //换图片
                $("#valiImage").attr('src',basePath+'/indentyCode/'+timestamp+'.png');
                $(".targetChars").text(resultMsg.targetChars);
            }
        }
    });
}
$(function(){
    $("#valiImage").unbind("click").click(function(event){
        if(clickCount > 2) {
            changeImg();
        } else {
            var x=event.offsetX;//获取点击时鼠标相对图片坐标
            var y=event.offsetY;
            clickCount = clickCount + 1;
            markCaptcha(x, y, clickCount);
            gaps += x + ":" + y + ","
        }

    });
});


// 添加标记
function markCaptcha(x,y,clickCount) {
    setCssStyle(x,y,clickCount);
}
// 设置对应的样式
function setCssStyle(x,y,num) {
    x = x - 12;
    y = y + 38;
    //$(".hiddenMark4Captcha" + num).text(num);
    $(".hiddenMark4Captcha" + num).css({
        "position": "absolute",
        "left": x,
        "top": y,
        "display": "block"
    });
}

// 验证验证码
function checkImage() {
    if(clickCount == 3) {
        $.ajax({
            url:basePath+"loginController/check.do",
            type:"post",
            data:{"gaps":gaps},
            dataType:"json",
            success:function(data){
                if(data.flag) {
                    // 验证通过, 执行后续的操作...
                    alert("验证通过, 请执行后续操作...");
                } else {
                    showImg();
                    alert("图片验证码验证失败");
                }
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                showImg();
                alert("图片验证失败");
            }
        });
    }else{
        showImg();
        alert("图片验证失败");
    }

};

//显示验证码对话框
function showImg(){
    changeImg();
    $("input[name='showImg']").click();
}