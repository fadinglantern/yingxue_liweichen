<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>

    $(function(){
        $("#videoTable").jqGrid({
            url:"${path}/YxVideo/findVideoList",//查询路径
            editurl:"${path}/YxVideo/edit",
            datatype: "json",//数据形式:json
            rowNum:2,//一次三条
            rowList:[2,6,9,13,26],//每次展示条数
            pager: '#videoPage',//分页
            sortname: 'id',//排序字段
            styleUI:"Bootstrap",//表格类型
            autowidth:true,//自适应宽度
            height:"auto",
            viewrecords: true,  //是否展示总条数书
            colNames:['ID','视频名称','视频描述','视频','上传时间','类别名称','类别id','用户id','视频状态'],
            colModel:[
                {name:'id',index:'id', width:55},
                {name:'title',index:'title', width:90,editable:true},
                {name:'brief',index:'brief', width:90,editable:true},
                {name:'videoPath',index:'title asc, videoPath', width:190,align:'center',edittype:"file",editable:true,
                    formatter:function(cellvalue, options, rowObject){
                        <%--return "<video id='media' src='${path}/uploadFiles/video/2020-11/" + cellvalue + "' controls width='100%' heigt='100%' poster='" + rowObject.cover + "' />";--%>
                        return "<video id='media' src='" + cellvalue + "' controls " +
                            "width='100%' heigt='100%' poster='" + rowObject.coverPath + "' />";
                    }
                },
                {name:'uploadTime',index:'uploadTime', width:80},
                {name:'cateName',index:'cateName', width:80},
                {name:'categoryId',index:'categoryId', width:80,editable:true},
                {name:'userId',index:'userId', width:100,editable:true},
                {name:'state',index:'total', width:80,
                    formatter:function(cellvalue, options, rowObject){
                        if(cellvalue==false){
                            return "<button class='btn btn-info' onclick='changeState(\""+rowObject.id+"\",\""+rowObject.state+"\")'>解除冻结</button>";
                        }else{
                            return "<button class='btn btn-success' onclick='changeState(\""+rowObject.id+"\",\""+rowObject.state+"\")'>冻结</button>";
                        }
                    }
                }
            ]
        });
        $("#videoTable").jqGrid('navGrid','#videoPage',
            {edit:true,add:true,del:true,addtext:"添加",edittext:"修改",deltext:"删除"},
            {//修改
                closeAfterEdit: true,  //操作后关闭 工具框
                reloadAfterSubmit: true,
                beforeShowForm: function (obj) {
                    obj.find("#userId").attr("disabled", true);//禁用input
                },
                afterSubmit:function(data){
                    //1.数据入库  返回id
                    //2.文件上传  根据id修改数据
                    //data.responseText=uuid
                    $.ajaxFileUpload({
                        url: "${path}/YxVideo/videoAndFirstFrameUpload",
                        type: "post",
                        data: {"id": data.responseText},
                        fileElementId: "videoPath",  //文件选择框的id属性  <input type="file" id="picImg" name="picImg">的id
                        success:function(){
                            //刷新页面
                            $("#videoTable").trigger("reloadGrid");
                        }
                    });
                    return "true";
                }
            },
            {//添加
                closeAfterAdd: true,//操作后关闭添加框
                afterSubmit:function(data){
                    //1.数据入库  返回id
                    //2.文件上传  根据id修改数据
                    //data.responseText=uuid
                    $.ajaxFileUpload({
                        url: "${path}/YxVideo/videoAndFirstFrameUpload",
                        type: "post",
                        data: {"id": data.responseText},
                        fileElementId: "videoPath",  //文件选择框的id属性  <input type="file" id="picImg" name="picImg">的id
                        success:function(){
                            //刷新页面
                            $("#videoTable").trigger("reloadGrid");
                        }
                    });
                    return "true";
                }
            },
            {//删除
                closeAfterEdit: true,  //打开面板
                reloadAfterSubmit: true
            }
        );

    });
    function changeState(id,state) {
        if (state==1){
            $.post("${path}/YxVideo/edit",{"id":id,"state":"0","oper":"alter"},function (date) {
                $("#videoTable").trigger("reloadGrid");
            },"text");
        }else {
            $.post("${path}/YxVideo/edit",{"id":id,"state":"1","oper":"alter"},function (date) {
                $("#videoTable").trigger("reloadGrid");
            },"text");
        }
    }

</script>


<div class="panel panel-warning">
    <%--面板头--%>
    <div class="panel panel-heading">
        <h2 class="text-center" style="margin-top: -5px">视频信息</h2>
    </div>
    <%--标签页--%>
    <ul class="nav nav-tabs" role="tablist" style="margin-top: -13px">
        <li role="presentation" class="active">
            <a href="#home" aria-controls="home" role="tab" data-toggle="tab">视频信息</a>
        </li>
    </ul>
    <table id="videoTable" class="text-center"></table>



    <%--分页工具栏--%>
    <div id="videoPage" ></div>

</div>