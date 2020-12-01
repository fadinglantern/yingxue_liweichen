<%--
  Created by IntelliJ IDEA.
  User: 82026
  Date: 2020-11-30
  Time: 23:33
  To change this template use File | Settings | File Templates.
--%>
<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">
    $("#logTable").jqGrid({
        url:"${path}/YxLog/findLogList",//查询路径
        editurl:"",
        datatype: "json",//数据形式:json
        rowNum:2,//一次三条
        rowList:[2,5,10,20,30],//每次展示条数
        pager: '#logPage',//分页
        sortname: 'id',//排序字段
        styleUI:"Bootstrap",//表格类型
        autowidth:true,//自适应宽度
        height:"auto",
        viewrecords: true,  //是否展示总条数书
        colNames:['日志编号','操作用户名','操作时间','操作表名','操作业务类型','操作方法签名','操作数据的ID','操作数据','版本'],
        colModel:[
            {name:'id',index:'id', width:55},
            {name:'username',index:'invdate', width:40,editable:true},
            {name:'operationTime',index:'invdate', width:90,editable:true},
            {name:'tableName',index:'invdate', width:55},
            {name:'operationMethod',index:'amount', width:60,editable:true},
            {name:'methodName',index:'tax', width:100,editable:true},
            {name:'dataId',index:'total', width:60,editable:true},
            {name:'dataInfo',index:'note', width:40, sortable:false},
            {name:'version',index:'note', width:40, sortable:false,editable:true}

        ]
    });
</script>
<div class="panel-primary">

    <%--面板头--%>
    <div class="panel panel-heading">
        <h2 class="text-center" style="margin-top: -5px">日志信息</h2>
    </div>
    <%--标签页--%>
    <ul class="nav nav-tabs" role="tablist" style="margin-top: -13px">
        <li role="presentation" class="active">
            <a href="#home" aria-controls="home" role="tab" data-toggle="tab">展示日志</a>
        </li>
    </ul>

    <%--表单--%>
    <div id="home">
        <table id="logTable" class="text-center"></table>
    </div>


    <%--分页工具栏--%>
    <div id="logPage" ></div>

</div>
