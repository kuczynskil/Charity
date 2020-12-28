<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang=""> <![endif]-->
<!--[if IE 7]> <html class="no-js lt-ie9 lt-ie8" lang=""> <![endif]-->
<!--[if IE 8]> <html class="no-js lt-ie9" lang=""> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js" lang="en">
<!--<![endif]-->

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Sufee Admin - HTML5 Admin Template</title>
    <meta name="description" content="Sufee Admin - HTML5 Admin Template">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="apple-touch-icon" href="${pageContext.request.contextPath}/sufee-admin-dashboard-master/apple-icon.png">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/sufee-admin-dashboard-master/favicon.ico">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/sufee-admin-dashboard-master/vendors/bootstrap/dist/css/bootstrap.min.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/sufee-admin-dashboard-master/vendors/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/sufee-admin-dashboard-master/vendors/themify-icons/css/themify-icons.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/sufee-admin-dashboard-master/vendors/flag-icon-css/css/flag-icon.min.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/sufee-admin-dashboard-master/vendors/selectFX/css/cs-skin-elastic.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/sufee-admin-dashboard-master/vendors/jqvmap/dist/jqvmap.min.css">


    <link rel="stylesheet" href="${pageContext.request.contextPath}/sufee-admin-dashboard-master/assets/css/style.css">

    <link href='https://fonts.googleapis.com/css?family=Open+Sans:400,600,700,800' rel='stylesheet' type='text/css'>

</head>

<body>


<!-- Left Panel -->

<aside id="left-panel" class="left-panel">
    <nav class="navbar navbar-expand-sm navbar-default">

        <div class="navbar-header">
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#main-menu"
                    aria-controls="main-menu" aria-expanded="false" aria-label="Toggle navigation">
                <i class="fa fa-bars"></i>
            </button>
            <a class="navbar-brand" href="./"><img
                    src="${pageContext.request.contextPath}/sufee-admin-dashboard-master/images/logo.png"
                    alt="Logo"></a>
            <a class="navbar-brand hidden" href="./"><img
                    src="${pageContext.request.contextPath}/sufee-admin-dashboard-master/images/logo2.png"
                    alt="Logo"></a>
        </div>

        <div id="main-menu" class="main-menu collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li class="menu-item-has-children active dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true"
                       aria-expanded="false"> <i class="menu-icon fa fa-table"></i>Tables</a>
                    <ul class="sub-menu children dropdown-menu">
                        <li><i class="fa fa-table"></i><a href="/admin">Users Table</a></li>
                        <li><i class="fa fa-table"></i><a href="/admin/donations">Donations Table</a></li>
                        <li><i class="fa fa-table"></i><a href="/admin/organizations">Organizations Table</a></li>
                    </ul>
                </li>
                <li class="menu-item-has-children dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true"
                       aria-expanded="false"> <i class="menu-icon fa fa-glass"></i>Pages</a>
                    <ul class="sub-menu children dropdown-menu">
                        <li><i class="menu-icon fa fa-sign-in"></i><a href="page-login.html">App user panel</a></li>
                    </ul>
                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </nav>
</aside><!-- /#left-panel -->

<!-- Left Panel -->

<!-- Right Panel -->

<div id="right-panel" class="right-panel">

    <!-- Header-->
    <header id="header" class="header">

        <div class="header-menu">

            <div class="col-sm-7">
                <a id="menuToggle" class="menutoggle pull-left"><i class="fa fa fa-tasks"></i></a>
                <div class="header-left">

                </div>
            </div>

            <div class="col-sm-5">
                <div class="user-area dropdown float-right">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true"
                       aria-expanded="false">
                        <img class="user-avatar rounded-circle"
                             src="${pageContext.request.contextPath}/sufee-admin-dashboard-master/images/admin.jpg"
                             alt="User Avatar">
                    </a>

                    <div class="user-menu dropdown-menu">

                        <a class="nav-link" href="#"><i class="fa fa-power-off"></i> Logout</a>
                    </div>
                </div>

            </div>
        </div>

    </header><!-- /header -->
    <!-- Header-->


    <div class="content mt-3">
        <div class="animated fadeIn">
            <div class="row">
                <div class="col-md-12">
                    <div class="card">
                        <div class="card-header">
                            <strong class="card-title">Users</strong>
                        </div>
                        <div class="card-body">
                            <table id="bootstrap-data-table-export" class="table table-striped table-bordered">
                                <thead>
                                <tr>
                                    <th>Id</th>
                                    <th>Name</th>
                                    <th>Email</th>
                                    <th>Enabled</th>
                                    <th>Role</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${users}" var="appuser">
                                    <tr>
                                        <td>${appuser.id}</td>
                                        <td>${appuser.name}</td>
                                        <td>${appuser.email}</td>
                                        <td>${appuser.enabled}</td>
                                        <td>
                                            <c:forEach items="${appuser.roles}" var="role">
                                                ${role.name}
                                            </c:forEach>
                                        </td>
                                        <td>
                                            <a href="/admin/appusers/edit/${appuser.id}"><i class="fa fa-edit"></i></a>
                                            <a href="/admin/appusers/delete/${appuser.id}"><i
                                                    class="fa fa-trash"></i></a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div> <!-- .content -->
</div><!-- /#right-panel -->

<!-- Right Panel -->

<script src="${pageContext.request.contextPath}/sufee-admin-dashboard-master/vendors/jquery/dist/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/sufee-admin-dashboard-master/vendors/popper.js/dist/umd/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/sufee-admin-dashboard-master/vendors/bootstrap/dist/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/sufee-admin-dashboard-master/assets/js/main.js"></script>


<script src="${pageContext.request.contextPath}/sufee-admin-dashboard-master/vendors/datatables.net/js/jquery.dataTables.min.js"></script>
<script src="${pageContext.request.contextPath}/sufee-admin-dashboard-master/vendors/datatables.net-bs4/js/dataTables.bootstrap4.min.js"></script>
<script src="${pageContext.request.contextPath}/sufee-admin-dashboard-master/vendors/datatables.net-buttons/js/dataTables.buttons.min.js"></script>
<script src="${pageContext.request.contextPath}/sufee-admin-dashboard-master/vendors/datatables.net-buttons-bs4/js/buttons.bootstrap4.min.js"></script>
<script src="${pageContext.request.contextPath}/sufee-admin-dashboard-master/vendors/jszip/dist/jszip.min.js"></script>
<script src="${pageContext.request.contextPath}/sufee-admin-dashboard-master/vendors/pdfmake/build/pdfmake.min.js"></script>
<script src="${pageContext.request.contextPath}/sufee-admin-dashboard-master/vendors/pdfmake/build/vfs_fonts.js"></script>
<script src="${pageContext.request.contextPath}/sufee-admin-dashboard-master/vendors/datatables.net-buttons/js/buttons.html5.min.js"></script>
<script src="${pageContext.request.contextPath}/sufee-admin-dashboard-master/vendors/datatables.net-buttons/js/buttons.print.min.js"></script>
<script src="${pageContext.request.contextPath}/sufee-admin-dashboard-master/vendors/datatables.net-buttons/js/buttons.colVis.min.js"></script>
<script src="${pageContext.request.contextPath}/sufee-admin-dashboard-master/assets/js/init-scripts/data-table/datatables-init.js"></script>
<script>
    (function ($) {
        "use strict";

        jQuery('#vmap').vectorMap({
            map: 'world_en',
            backgroundColor: null,
            color: '#ffffff',
            hoverOpacity: 0.7,
            selectedColor: '#1de9b6',
            enableZoom: true,
            showTooltip: true,
            values: sample_data,
            scaleColors: ['#1de9b6', '#03a9f5'],
            normalizeFunction: 'polynomial'
        });
    })(jQuery);
</script>

</body>

</html>
