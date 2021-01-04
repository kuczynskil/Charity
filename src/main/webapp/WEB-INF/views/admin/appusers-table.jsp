<%@include file="/WEB-INF/views/admin/includes/header.jsp" %>

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
                                            <a href="/admin/appusers/edit/${appuser.id}">
                                                <i class="fa fa-edit"></i></a>
                                            <a href="/admin/appusers/delete/${appuser.id}">
                                                <i class="fa fa-trash"></i></a>
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
