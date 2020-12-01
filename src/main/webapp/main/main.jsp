<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>后台管理系统</title>
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
</head>
<body>
    <!--顶部导航-->
    <nav class="navbar navbar-inverse">
        <div class="container-fluid">
            <!-- 导航条标题 -->
            <div class="navbar-header">
                <!--自适应-->
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                    <span class="sr-only"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">员工管理系统 <small>V1.0</small></a>
            </div>

            <!-- 导航栏上工具-->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <!--右边工具栏-->
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="#">欢迎:<span class="text-primary">${sessionScope.InfoMap.thisAdmin.username}</span></a></li>
                    <li><a href="#">退出系统&nbsp;<span class="glyphicon glyphicon-log-out"></span></a></li>
                </ul>
            </div>
        </div>
    </nav>
    <!--栅格系统-->
    <div class="container-fluid">
        <div class="row" style="margin-top: -12px">
            <!--左边手风琴部分--><!--巨幕开始-->
            <div class="col-sm-2" >
                <!--菜单-->
                <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                    <!--面板-->
                    <div class="panel panel-default panel-danger" aria-expanded="false">
                        <div class="panel-heading" role="tab" id="headingOne">
                            <h4 class="panel-title text-center">
                                <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                                    用户管理</a>
                            </h4>
                        </div>
                        <!--面板内容-->
                        <div id="collapseOne" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                            <div class="panel-body" align="center">
                                <button class="btn btn-danger btn-lg">
                                    <%--<a href="javascript:;" onclick="javascript:$('#content').load('${path}/user/showUser.jsp')" >
                                        用户展示</a>--%>
<%--                                    <a href="javascript:$('#content').load('${path}/user/showUser.jsp')">用户信息</a>--%>
                                    <a href="javascript:$('#content').load('${path}/user/showUserListPage.jsp')">用户信息</a>

                                </button>
                                <br><br>
                                <button class="btn btn-danger btn-lg">
                                    <a href="javascript:$('#content').load('${path}/user/RQTable.jsp')">用户统计</a>
                                    </button>
                                <br><br>
                                <button class="btn btn-danger btn-lg">
                                    <a href="javascript:$('#content').load('${path}/user/userProfileTable.jsp')">用户分布</a>
                                </button>
                            </div>
                        </div>
                    </div>
                    <hr>
                    <!--面板-->
                    <div class="panel panel-default panel-success">
                        <div class="panel-heading" role="tab" id="headingTwo">
                            <h4 class="panel-title text-center">
                                <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="true" aria-controls="collapseOne">
                                    类别管理</a>
                            </h4>
                        </div>
                        <!--面板内容-->
                        <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
                            <div class="panel-body" align="center">
                                <button class="btn btn-lg btn-success">
                                    <a href="javascript:$('#content').load('${path}/category/showCategory.jsp')">分类展示</a>
                                </button>
                            </div>
                        </div>
                    </div>
                    <hr>
                    <div class="panel panel-default panel-warning">
                        <div class="panel-heading" role="tab" id="headingThree">
                            <h4 class="panel-title text-center">
                                <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="true" aria-controls="collapseOne">
                                    视频管理</a>
                            </h4>
                        </div>
                        <!--面板内容-->
                        <div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
                            <div class="panel-body" align="center">
                                <button class="btn btn-lg btn-warning">
                                    <a href="javascript:$('#content').load('${path}/video/showVideoList.jsp')">视频信息</a></button>
                            </div>
                        </div>
                    </div>
                    <hr>
                    <div class="panel panel-default panel-info">
                        <div class="panel-heading" role="tab" id="headingFour">
                            <h4 class="panel-title text-center">
                                <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseFour" aria-expanded="true" aria-controls="collapseOne">
                                    反馈管理</a>
                            </h4>
                        </div>
                        <!--面板内容-->
                        <div id="collapseFour" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFour">
                            <div class="panel-body" align="center">
                                <button class="btn btn-lg btn-info">反馈信息</button>
                            </div>
                        </div>
                    </div>
                    <hr>
                    <div class="panel panel-default panel-primary">
                        <div class="panel-heading" role="tab" id="headingFive">
                            <h4 class="panel-title text-center">
                                <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseFive" aria-expanded="true" aria-controls="collapseOne">
                                    日志管理</a>
                            </h4>
                        </div>
                        <!--面板内容-->
                        <div id="collapseFive" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFive">
                            <div class="panel-body" align="center">
                                <button class="btn btn-lg btn-primary">
                                    <a href="javascript:$('#content').load('${path}/log/showLogList.jsp')">日志信息</a>
                                </button>
                            </div>
                        </div>
                    </div>
                    <hr>
                </div>
            </div>
            <div id="content" class="col-sm-10">
                <!--巨幕开始-->
                <div class="col-sm-12">
                    <div class="jumbotron" >
                        <h1 class="text-center" style="margin-top: -10px">
                            应学视频app 后台管理系统
                        </h1>
                    </div>
                </div>
                <!--轮播图-->
                <div class="col-sm-12" >
                    <div id="carousel-example-generic" class="carousel slide" data-ride="carousel" >
                        <!-- Indicators -->
                        <ol class="carousel-indicators">
                            <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                            <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                            <li data-target="#carousel-example-generic" data-slide-to="2"></li>
                            <li data-target="#carousel-example-generic" data-slide-to="3"></li>
                        </ol>

                        <!-- Wrapper for slides -->
                        <div class="carousel-inner" role="listbox">
                            <div class="item active">
                                <img src="../bootstrap/img/pic1.jpg" style="height: 477px;width: 90%;padding-left: 10%" alt="...">
                            </div>
                            <div class="item">
                                <img src="../bootstrap/img/pic2.jpg " style="height:477px;width: 90%;padding-left: 10%" alt="...">
                            </div>
                            <div class="item">
                                <img src="../bootstrap/img/pic3.jpg" style="height: 477px;width: 90%;padding-left: 10%" alt="...">
                            </div>
                            <div class="item">
                                <img src="../bootstrap/img/pic4.jpg" style="height: 477px;width: 90%;padding-left: 10%" alt="...">
                            </div>
                        </div>
                        <!-- Controls 左右动作-->
                        <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
                            <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                        </a>
                        <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
                            <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
        <!--页脚-->
    <!--栅格系统-->

</body>
</html>
