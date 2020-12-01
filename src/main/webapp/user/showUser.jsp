<%--
  Created by IntelliJ IDEA.
  User: 82026
  Date: 2020-11-19
  Time: 17:07
  To change this template use File | Settings | File Templates.
--%>
<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>

    $(function(){
        $("#userId").jqGrid({
            url:"/yxUser/showAllUser",//查询路径
            editurl:"",
            datatype: "json",//数据形式:json
            rowNum:2,//一次三条
            rowList:[2,5,10,20,30],//每次展示条数
            pager: '#userPage',//分页
            sortname: 'id',//排序字段
            styleUI:"Bootstrap",//表格类型
            autowidth:true,//自适应宽度
            height:"auto",
            viewrecords: true,  //是否展示总条数书
            colNames:['ID','昵称','密码','手机号','头像','描述','学分','注册时间','状态'],
            colModel:[
                {name:'id',index:'id', width:55},
                {name:'nickName',index:'invdate', width:90},
                {name:'userPassword',index:'invdate', width:90},
                {name:'phone',index:'amount', width:80},
                {name:'picImg',index:'name asc, invdate', width:100,align:'center',
                    formatter:function(cellvalue, options, rowObject){
                        return "<img src='${path}/files"+cellvalue+"' width='180px' height='185px'>";
                    }
                },
                {name:'brief',index:'tax', width:80},
                {name:'score',index:'total', width:80},
                {name:'createDate',index:'note', width:100, sortable:false},
                {name:'state',index:'total', width:80,
                    formatter:function(cellvalue, options, rowObject){
                        if(cellvalue==false){
                            return "<button class='btn btn-info'><a href=''>解除冻结</a></button>";
                        }else{
                            return "<button class='btn btn-success'><a href=''>冻结</a></button>";
                        }
                    }
                }
            ]
        });
        $("#userId").jqGrid('navGrid','#userPage',{edit:false,add:false,del:false,search:false});

    });

</script>


<%--设置面板--%>
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

    <div style="margin-top: 10px;margin-left: 10px">
        <button class="btn btn-success">导出用户信息
        </button>&nbsp;&nbsp;
        <button class="btn btn-info">
            <a href="#insertModal" data-toggle="modal">导入用户</a>
        </button>&nbsp;&nbsp;
        <button class="btn btn-warning">test button</button>
    </div><br>

    <%--表单--%>
    <div id="home">
        <table id="userId" class="text-center"></table>
    </div>


    <%--分页工具栏--%>
    <div id="userPage" ></div>

</div>
<div class="modal fade" id="insertModal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">导入用户</h4>
            </div>
            <form action="${path}/yxUser/addOneUser" method="post" enctype="multipart/form-data">
                <div class="modal-body">
                    <div class="row" >
                        <div class="col-sm-8 col-sm-offset-2">
                            <div class="form-group">
                                <label class="" for="addDept">id</label>
                                <input type="text" class="form-control" id="addDept" placeholder="用户id" name="id">
                            </div>
                            <div class="form-group">
                                <label class="" for="addDept2">username</label>
                                <input type="text" class="form-control" id="addDept2" placeholder="输入用户名" name="nickName">
                            </div>
                            <div class="form-group">
                                <label class="" for="addDept3">password</label>
                                <input type="password" class="form-control" id="addDept3" placeholder="输入密码" name="userPassword">
                            </div>
                            <div class="form-group">
                                <label class="" for="addDept4">phone</label>
                                <input type="text" class="form-control" id="addDept4" placeholder="输入phone" name="phone">
                            </div>
                            <div class="form-group">
                                <label class="" for="addDept5">头像</label>
                                <input type="file" class="form-control" id="addDept5" name="picName">
                            </div>
                            <div class="form-group">
                                <label class="" for="addDept6">描述</label>
                                <input type="text" class="form-control" id="addDept6" placeholder="输入描述" name="brief">
                            </div>
                            <div class="form-group">
                                <label class="" for="addDept7">学分</label>
                                <input type="text" class="form-control" id="addDept7" placeholder="输入学分" name="score">
                            </div>
                            <div class="form-group">
                                <label class="" for="addDept8">注册时间</label>
                                <input type="date" class="form-control" id="addDept8" name="createDate">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-info">录入</button>
                    <button type="button" class="btn btn-danger" data-dismiss="modal">取消</button>
                </div>
            </form>
        </div>
    </div>
</div>

