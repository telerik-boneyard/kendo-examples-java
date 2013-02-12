<%@taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
	<title>Insert title here</title>
	<!-- kendo css files -->
	<link href="styles/kendo.common.min.css" rel="stylesheet">
	<link href="styles/kendo.default.min.css" rel="stylesheet">
	<!-- jquery and kendo javascript. jquery MUST be first. -->
	<script src="js/jquery.min.js"></script>
	<script src="js/kendo.all.min.js"></script>
</head>
<body>

	<kendo:grid name="products" pageable="true">
		<kendo:dataSource pageSize="10" serverPaging="true">
			<kendo:dataSource-transport read="api/products">
				<kendo:dataSource-transport-parameterMap>
					<script>
						function parameterMap(options, operation) {
							return {
								skip: options.skip,
								take: options.take
							};
						}
					</script>
				</kendo:dataSource-transport-parameterMap>
			</kendo:dataSource-transport>
			<kendo:dataSource-schema data="Data" total="Total"></kendo:dataSource-schema>
		</kendo:dataSource>
		<kendo:grid-columns>
			<kendo:grid-column title="Name" field="ProductName" />
			<kendo:grid-column title="Supplier" field="Supplier.SupplierName" />
			<kendo:grid-column title="Category" field="Category.CategoryName" />
			<kendo:grid-column title="Price" field="UnitPrice" />
			<kendo:grid-column title="# In Stock" field="UnitsInStock" />
			<kendo:grid-column title="Discontinued" field="Discontinued" />
		</kendo:grid-columns>
	</kendo:grid>

</body>
</html>