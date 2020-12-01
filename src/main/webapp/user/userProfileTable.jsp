<%--
  Created by IntelliJ IDEA.
  User: 82026
  Date: 2020-11-29
  Time: 18:01
  To change this template use File | Settings | File Templates.
--%>
<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<script src="${path}/bootstrap/js/echarts.js"></script>
<script src="${path}/bootstrap/js/jquery.min.js"></script>
<script src="${path}/bootstrap/js/china.js"></script>
<script type="text/javascript" src="https://cdn.goeasy.io/goeasy-1.1.1.js"></script>


<script type="text/javascript">
    $(function(){

        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));
        $.get("${path}/yxUser/sexCityCount",function (datas) {
            var series=[];
            for (var i=datas.length-1;i>=0;i--){
                var data=datas[i];

                series.push(
                    {
                        name: data.sex,
                        type: 'map',
                        mapType: 'china',
                        label: {
                            normal: {
                                show: false
                            },
                            emphasis: {
                                show: true
                            }
                        },
                        data:data.citys
                    }
                )
            }

            var option = {
                title: {
                    text: '每月用户注册量',
                    subtext: '纯属虚构',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    // data: ['女性用户', '男性用户']
                    data: ['男性用户', '女性用户']
                },
                visualMap: {
                    min: 0,
                    max: 200,
                    left: 'left',
                    top: 'bottom',
                    text: ['高', '低'],           // 文本，默认为数值文本
                    calculable: true
                },
                toolbox: {
                    show: true,
                    orient: 'vertical',
                    left: 'right',
                    top: 'center',
                    feature: {
                        mark: {show: true},
                        dataView: {show: true, readOnly: false},
                        restore: {show: true},
                        saveAsImage: {show: true}
                    }
                },
                series:series
            };

            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
        },"json");
    });
</script>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="main" style="width: 90%;height: 506px;margin-top: 13px"></div>
