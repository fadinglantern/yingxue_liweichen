<%--
  Created by IntelliJ IDEA.
  User: 82026
  Date: 2020-11-20
  Time: 14:47
  To change this template use File | Settings | File Templates.
--%>
<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
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
        $("#putUserButton").click(function () {
            var isOk =  $("#userForm").valid();
            if (isOk){
                $.ajax({
                    url:"${path}/yxUser/expdpUser",
                    type:"post",
                    data:$("#userForm").serialize(),
                    dataType:"JSON",
                    success:function (response) {
                        /*if (data.status===200){
                            $("#msgDiv").html("<span class='strong' style='color: lawngreen'>"+data.message+"</span>");
                        }else {
                            $("#msgDiv").html("<span class='strong' style='color: red'>"+data.message+"</span>");
                        }*/

                        $("#msgDiv").html(response.message);
                        //展示警告框
                        $("#msgDiv").show();
                        //设置过期时间
                        setTimeout(function () {
                            $("#msgDiv").hide();
                        },2000);
                    }
                });
            }
        });
        $("#userTable").jqGrid({
            url:"${path}/yxUser/showAllUser",//查询路径
            editurl:"${path}/yxUser/edit",
            datatype: "json",//数据形式:json
            rowNum:2,//一次三条
            rowList:[2,5,10,20,30],//每次展示条数
            pager: '#userPage',//分页
            sortname: 'id',//排序字段
            styleUI:"Bootstrap",//表格类型
            autowidth:true,//自适应宽度
            height:"auto",
            viewrecords: true,  //是否展示总条数书
            colNames:['ID','昵称','密码','salt','手机号','头像','描述','学分','注册时间','状态','性别','城市'],
            colModel:[
                {name:'id',index:'id', width:55},
                {name:'nickName',index:'invdate', width:70,editable:true},
                {name:'userPassword',index:'invdate', width:90,editable:true},
                {name:'salt',index:'invdate', width:90},
                {name:'phone',index:'amount', width:80,editable:true},
                {name:'picImg',index:'name asc, invdate', width:130,align:'center',edittype:"file",editable:true,
                    formatter:function(cellvalue, options, rowObject){
                        return "<img src='"+cellvalue+"' width='100%' height='130px'>";
                    }
                },
                {name:'brief',index:'tax', width:80,editable:true},
                {name:'score',index:'total', width:40,editable:true},
                {name:'createDate',index:'note', width:100, sortable:false},
                {name:'state',index:'total', width:80,
                    formatter:function(cellvalue, options, rowObject){
                        if(cellvalue==false){
                            return "<button class='btn btn-info' onclick='changeState(\""+rowObject.id+"\",\""+rowObject.state+"\")'>解除冻结</button>";
                        }else{
                            return "<button class='btn btn-success' onclick='changeState(\""+rowObject.id+"\",\""+rowObject.state+"\")'>冻结</button>";
                        }
                    }
                },
                {name:'sex',index:'note', width:40, sortable:false,editable:true},
                {name:'city',index:'note', width:40, sortable:false,editable:true}

            ]
        });
        $("#userTable").jqGrid('navGrid','#userPage',
            {edit:true,add:true,del:true,addtext:"添加",edittext:"修改",deltext:"删除"},
            {//修改
                closeAfterEdit: true,  //操作后关闭 工具框
                reloadAfterSubmit: true,
                beforeShowForm: function (obj) {
                    obj.find("#city").attr("disabled", true);//禁用input
                },
                afterSubmit:function(data){
                    //1.数据入库  返回id
                    //2.文件上传  根据id修改数据
                    //data.responseText=uuid
                    $.ajaxFileUpload({
                        url: "${path}/yxUser/photoUpload",
                        type: "post",
                        data: {"id": data.responseText},
                        fileElementId: "picImg",  //文件选择框的id属性  <input type="file" id="picImg" name="picImg">的id
                        success:function(){
                            //刷新页面
                            $("#userTable").trigger("reloadGrid");
                        }
                    });
                    return "true";
                }
            },
            {//添加
                closeAfterAdd: true,//关闭添加框
                afterSubmit:function(data){
                    //1.数据入库  返回id
                    //2.文件上传  根据id修改数据
                    //data.responseText=uuid
                    $.ajaxFileUpload({
                        url: "${path}/yxUser/photoUpload",
                        type: "post",
                        data: {"id": data.responseText},
                        fileElementId: "picImg",  //文件选择框的id属性  <input type="file" id="picImg" name="picImg">的id
                        success:function(){
                            //刷新页面
                            $("#userTable").trigger("reloadGrid");
                        }
                    });
                    return "true";
                }
            },
            {//删除


            }
        );

    });
    function changeState(id,state) {
        if (state==1){
            $.post("${path}/yxUser/edit",{"id":id,"state":"0","oper":"alter"},function (date) {
                $("#userTable").trigger("reloadGrid");
            },"text");
        }else {
            $.post("${path}/yxUser/edit",{"id":id,"state":"1","oper":"alter"},function (date) {
                $("#userTable").trigger("reloadGrid");
            },"text");
        }
    }

</script>


<div class="panel-danger">

    <%--面板头--%>
    <div class="panel panel-heading">
        <h2 class="text-center" style="margin-top: -5px">用户信息</h2>
    </div>
    <%--标签页--%>
    <ul class="nav nav-tabs" role="tablist" style="margin-top: -13px">
        <li role="presentation" class="active">
            <a href="#home" aria-controls="home" role="tab" data-toggle="tab">用户管理</a>
        </li>
    </ul>

    <div style="margin-top: 20px;margin-left: 10px" class="row">
        <form  class="" style="display: inline" id="userForm" role="form" action="" method="post" >
<%--            <button class="btn btn-success">导出用户信息</button>--%>
            <input class="col-sm-1 btn btn-success" id="putUserButton" type="button" value="导出用户信息">
            <input class="col-sm-1 btn btn-info" style="margin-left: 13px" type="button" value="导入用户信息">
        </form>
        <strong>
            <span id="msgDiv" style="display: none;margin-left: 10px;"
                  class="alert alert-warning alert-dismissible" role="alert"></span>
        </strong>
    </div><br>

    <%--表单--%>
    <div id="home">
        <table id="userTable" class="text-center"></table>
    </div>


    <%--分页工具栏--%>
    <div id="userPage" ></div>

</div>

