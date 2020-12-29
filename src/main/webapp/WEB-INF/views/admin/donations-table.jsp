<%@include file="/WEB-INF/views/admin/includes/header.jsp" %>

<div class="content mt-3">
        <div class="animated fadeIn">
            <div class="row">
                <div class="col-md-12">
                    <div class="card">
                        <div class="card-header">
                            <strong class="card-title">Donations</strong>
                        </div>
                        <div class="card-body">
                            <table id="bootstrap-data-table-export" class="table table-striped table-bordered">
                                <thead>
                                <tr>
                                    <th>Id</th>
                                    <th>User_email</th>
                                    <th>Telephone_number</th>
                                    <th>Quantity</th>
                                    <th>Categories</th>
                                    <th>Street</th>
                                    <th>City</th>
                                    <th>Zipcode</th>
                                    <th>Organization</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${donations}" var="donation">
                                    <tr>
                                        <td>${donation.id}</td>
                                        <td>${donation.appUser.email}</td>
                                        <td>${donation.telephoneNumber}</td>
                                        <td>${donation.quantity}</td>
                                        <td>
                                            <c:forEach items="${donation.categories}" var="category">
                                                ${category.name}<br>
                                            </c:forEach>
                                        </td>
                                        <td>${donation.street}</td>
                                        <td>${donation.city}</td>
                                        <td>${donation.zipCode}</td>
                                        <td>"${donation.organization.name}"</td>
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
