<%@include file="/WEB-INF/views/admin/includes/header.jsp" %>

<div class="content mt-3">
        <div class="animated fadeIn">
            <div class="row">
                <div class="col-md-12">
                    <div class="card">
                        <div class="card-header">
                            <strong class="card-title">Charities</strong>
                        </div>
                        <div class="card-body">
                            <table id="bootstrap-data-table-export" class="table table-striped table-bordered">
                                <thead>
                                <tr>
                                    <th>Id</th>
                                    <th>Name</th>
                                    <th>Description</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <form:form action="/admin/organizations/add" modelAttribute="organization"
                                               method="post">
                                        <td></td>
                                        <td>
                                            <div class="col-12 col-md-9">
                                                <form:input path="name"
                                                            name="text-input"
                                                            placeholder="Name"
                                                            class="form-control"/>
                                            </div>
                                        </td>
                                        <td>
                                            <div class="col-12 col-md-9">
                                                <form:input path="description" type="text"
                                                            name="text-input"
                                                            placeholder="Description"
                                                            class="form-control"/>
                                            </div>
                                        </td>
                                        <td>
                                            <button type="submit" class="btn btn-primary btn-sm">
                                                <i class="fa fa-plus-circle"></i>
                                            </button>
                                        </td>
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    </form:form>
                                </tr>
                                <c:forEach items="${organizations}" var="organization">
                                    <tr>
                                        <td>${organization.id}</td>
                                        <td>"${organization.name}"</td>
                                        <td>${organization.description}</td>
                                        <td>
                                            <a href="/admin/organizations/edit/${organization.id}">
                                                <i class="fa fa-edit"></i></a>
                                            <a href="/admin/organizations/delete/${organization.id}">
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
    </div>
</div> <!-- .content -->
</div><!-- /#right-panel -->

<!-- Right Panel -->

<script src="${pageContext.request.contextPath}/sufee-admin-dashboard-master/vendors/jquery/dist/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/sufee-admin-dashboard-master/vendors/popper.js/dist/umd/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/sufee-admin-dashboard-master/vendors/bootstrap/dist/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/sufee-admin-dashboard-master/assets/js/main.js"></script>


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
