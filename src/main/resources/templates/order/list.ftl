<html>
<body>
    <#--Freemark遍历数据-->
    <#list orderDTOPage.content as orderDTO>
        ${orderDTO.orderId}
    </#list>
</body>

<#-- 播放音乐 -->
<audio id="notice" loop="loop">
    <source src="" type="">
</audio>

<#-- websocket -->
<script>
    var websocket = null;
    if ('WebSocket' in window) {
        websocket = new WebSocket("ws://sell.natapp4.cc/sell/websocket");
    } else {
        alert("该浏览器不支持websocket");
    }

    websocket.onopen = function (event) {
        console.log("建立连接");
    }

    websocket.onclose = function (event) {
        console.log("连接关闭");
    }
    
    websocket.onmessage = function (event) {
        console.log("收到服务端消息: " + event.data);

        document.getElementById('notice').play();
        // document.getElementById('notice').pause();
    }

    websocket.onerror = function () {
        alert("websocket通信发生错误");
    }

    window.onbeforeunload = function () {
        websocket.close();
    }

</script>

</html>