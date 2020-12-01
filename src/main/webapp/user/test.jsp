<%--
  Created by IntelliJ IDEA.
  User: 82026
  Date: 2020-11-29
  Time: 21:58
  To change this template use File | Settings | File Templates.
--%>
<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<html>

    <title>123</title>
    <script src="${path}/bootstrap/js/echarts.js"></script>
    <script src="${path}/bootstrap/js/echarts.min.js"></script>
    <script src="${path}/bootstrap/js/jquery.min.js"></script>
    <script type="text/javascript" src="${path}/bootstrap/js/goeasy-1.0.5.js"></script>
    <script>
        var goEasy = new GoEasy({
            host:'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
            appkey: "BC-072842fda0594c699c4daf892a840de4" //替换为您的应用appkey
        });
        $(function () {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('countTable'));
            goEasy.subscribe({
                channel: "yingxChannel", //替换为您自己的channel
                onMessage: function (message) {
                    var datas=message.content;
                    alert(datas);
                    var data=JSON.parse(datas);
                    // 指定图表的配置项和数据
                    var option = {
                        title: {
                            text: '男性女性注册月份图',//标题
                            textStyle: {
                                fontSize: 30
                            }
                        },
                        tooltip: {},//鼠标提示
                        legend: {
                            data: ['女性用户', '男性用户'],//选项卡
                            itemHeight: 26,
                            textStyle: {
                                fontSize: 26
                            }
                        },
                        xAxis: {
                            data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
                        },
                        yAxis: {},
                        series: [{//数据
                            name: '女性用户',//对应选项卡
                            type: 'bar',
                            data: data.girls
                        }, {//数据
                            name: '男性用户',
                            type: 'bar',
                            data: data.boys
                        }
                        ]
                    };
                    // 使用刚指定的配置项和数据显示图表。
                    myChart.setOption(option);
                }
            });
        })
    </script>

    <div id="countTable" style="width: 90%;height: 506px;margin-top: 13px">
    </div>
</html>
