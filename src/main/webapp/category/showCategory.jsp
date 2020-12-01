<%--
  Created by IntelliJ IDEA.
  User: 82026
  Date: 2020-11-21
  Time: 16:55
  To change this template use File | Settings | File Templates.
--%>
<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="${path}/bootstrap/img/arrow-up.png" type="image/x-icon">
    <link rel="stylesheet" href="${path}/bootstrap/css/bootstrap.css">

    <%--引入jqgrid中主题css--%>
    <link rel="stylesheet" href="${path}/bootstrap/jqgrid/css/css/hot-sneaks/jquery-ui-1.8.16.custom.css">
    <link rel="stylesheet" href="${path}/bootstrap/jqgrid/boot/css/trirand/ui.jqgrid-bootstrap.css">
    <%--引入js文件--%>
    <script src="${path}/bootstrap/js/jquery.min.js"></script>
    <script src="${path}/bootstrap/js/bootstrap.js"></script>
    <script src="${path}/bootstrap/jqgrid/js/i18n/grid.locale-cn.js"></script>
    <script src="${path}/bootstrap/jqgrid/boot/js/trirand/jquery.jqGrid.min.js"></script>
    <script src="${path}/bootstrap/js/ajaxfileupload.js"></script>
    <script src="${path}/category/assets/js/jquery.validate.min.js"></script>
    <script>

        $(function(){
            pageInit();
            $("#codeButton").click(function () {
                var isOk =  $("#phoneForm").valid();
                if (isOk){
                    $.ajax({
                        url:"${path}/YxCategory/putPhoneCode",
                        type:"post",
                        data:$("#phoneForm").serialize(),
                        dataType:"JSON",
                        success:function (data) {
                            if (data.status===200){//打印发送信息
                                <%--location.href="${path}/main/main.jsp";--%>
                                $("#msgDiv").html("<span class='strong' style='color: lawngreen'>"+data.message+"</span>");
                            }else {
                                $("#msgDiv").html("<span class='strong' style='color: red'>"+data.message+"</span>");
                            }
                            $("#phoneNum").val("");//重置输入框
                        }
                    });
                }
            });
        });

        function pageInit(){
            $("#cateTable").jqGrid({
                url : "${path}/YxCategory/findCategoryList",
                editurl:"${path}/YxCategory/edit",
                datatype : "json",
                rowNum : 3,
                rowList : [ 3, 6, 10, 20 ],
                pager : '#catePage',
                sortname : 'id',
                viewrecords : true,
                styleUI:"Bootstrap",
                autowidth:true,
                height:"auto",
                colNames : [ '编号', '名字', '级别', '父类编号' ],
                colModel : [
                    {name : 'id',index : 'id',  width : 55},
                    {name : 'cateName',index : 'cateName',width : 90,editable:true},
                    {name : 'levels',index : 'levels',width : 100},
                    {name : 'parentId',index : 'parentId',width : 80}

                ],
                subGrid : true,  //开启子表格
                // subgrid_id:是在创建表数据时创建的div标签的ID
                //row_id是该行的ID
                subGridRowExpanded : function(subgrid_id, row_id) {
                    addSubGrid(subgrid_id, row_id);
                }
            });
            $("#cateTable").jqGrid('navGrid', '#catePage',
                {add : true,edit : true,del : true,addtext:"添加",edittext:"修改",deltext:"删除"},
                {
                    closeAfterEdit: true,  //打开面板
                    reloadAfterSubmit: true
                },  //对编辑配置
                {
                    closeAfterAdd: true,
                    reloadAfterSubmit: true
                },  //对添加配置
                {
                    closeAfterDelete: true, //生效
                    reloadAfterSubmit: true,
                    afterSubmit:function (response) {
                        $("#message").html(response.responseJSON.message);
                        //展示警告框
                        $("#message").show();
                        //设置过期时间
                        setTimeout(function () {
                            $("#message").hide();
                        },2000);

                       /* if (response.responseJSON.status==201){
                            alert(response.responseJSON.message)
                        }
                        else {
                            alert(response.responseJSON.message)
                        }*/
                        return "true";
                    }
                } //对删除配置  删除成功之后触发的function,接收删除返回的提示信息,在页面做展示
            );
        }


        //开启子表格的样式
        function addSubGrid(subgridId, rowId){

            var subgridTableTd= subgridId + "Table";
            var pagerId= subgridId+"Page";
            $("#"+subgridId).html("" +
                "<table id='"+subgridTableTd+"' />" +
                "<div id='"+pagerId+"' />"
            );

            $("#" + subgridTableTd).jqGrid({
                url : "${path}/YxCategory/findCategoryList?parentId=" + rowId,
                editurl:"${path}/YxCategory/edit?parentId="+ rowId,
                datatype : "json",
                rowNum : 3,//每页记录
                pager : "#"+pagerId,
                sortname : 'num',
                sortorder : "asc",
                styleUI:"Bootstrap",
                autowidth:true,
                height:"auto",
                viewrecords: true,  //是否展示总条数书
                colNames : [ '编号', '名称', '级别', '父类编号' ],
                colModel : [
                    {name : "id",  index : "id",width : 80,key : true},
                    {name : "cateName",index : "cateName",  width : 130,editable:true},
                    {name : "levels",index : "levels",width : 70},
                    {name : "parentId",index : "parentId",width : 70}
                ]
            });

            $("#" + subgridTableTd).jqGrid('navGrid',"#" + pagerId,
                {add : true,edit : true,del : true,search:false,refresh:true},
                {
                    closeAfterEdit: true,  //打开面板
                    reloadAfterSubmit: true,
                },  //对编辑配置
                {
                    closeAfterAdd: true,
                    reloadAfterSubmit: true,
                },  //对添加配置
                {
                    afterSubmit:function (response) {
                        $("#message").html(response.responseJSON.message);
                        //展示警告框
                        $("#message").show();
                        //设置过期时间
                        setTimeout(function () {
                            $("#message").hide();
                        },2000);

                        /* if (response.responseJSON.status==201){
                             alert(response.responseJSON.message)
                         }
                         else {
                             alert(response.responseJSON.message)
                         }*/
                        return "true";
                       /* if (response.responseJSON.status==201){
                            alert(response.responseJSON.message)
                        }
                        else {
                            alert(response.responseJSON.message)
                        }
                        return "true";*/
                    }
                } //对删除配置  删除成功之后触发的function,接收删除返回的提示信息,在页面做展示
            );
        }
    </script>
    <title>展示类别</title>
</head>
<body>
<div class="panel-success">

    <%--面板头--%>
    <div class="panel panel-heading">
        <h2>类别信息</h2>
    </div>
    <div class="form-bottom" >
        <form class="form-control" style="width: 500px;display: inline;" id="phoneForm" role="form" action="" method="post" >
            <input type="text" name="phoneNum" id="phoneNum">
            <input type="button" id="codeButton" value="发送验证码">
        </form>
        <span id="msgDiv"></span>
        <%--警告框--%>
        <strong>
            <span id="message" style="color: red;display: none;margin-left: 10px"
                  class="alert alert-warning alert-dismissible" role="alert"></span>
        </strong>

    </div>
<%--标签页--%>
    <ul class="nav nav-tabs" role="tablist" style="margin-top: 10px">
        <li role="presentation" class="active">
            <a href="#home" aria-controls="home" role="tab" data-toggle="tab">类别管理</a>
        </li>

    </ul>

    <%--表单--%>
    <table id="cateTable" class="text-center"></table>

    <%--分页工具栏--%>
    <div id="catePage"></div>

</div>
</body>
</html>
