<%@taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
	<title>Products</title>
	<!-- kendo css files -->
	<link href="styles/kendo.common.min.css" rel="stylesheet">
	<link href="styles/kendo.default.min.css" rel="stylesheet">
	<!-- jquery and kendo javascript. jquery MUST be first. -->
	<script src="js/jquery.min.js"></script>
	<script src="js/kendo.all.min.js"></script>
	<style>
		.centered {
			text-align: center;
		}
	</style>
</head>
<body>

	<kendo:grid name="products" pageable="true">
		<kendo:grid-editable mode="popup" />
		<kendo:dataSource pageSize="5" serverPaging="true">
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
			<kendo:grid-column field="ProductName" title="Product"></kendo:grid-column>
			<kendo:grid-column field="Supplier" title="Supplier" editor="supplierEditor" template="#: Supplier.SupplierName #"></kendo:grid-column>
			<kendo:grid-column field="Category" title="Category" width="150px" editor="categoryEditor" template="#: Category.CategoryName #"></kendo:grid-column>
			<kendo:grid-column field="UnitPrice" title="Price" format="{0:c}" width="75px"></kendo:grid-column>
			<kendo:grid-column field="UnitsInStock" title="# In Stock" width="80px"></kendo:grid-column>
			<kendo:grid-column title="Discontinued" field="Discontinued" width="100px"
							   template="# if (data.Discontinued) { # <span class='k-icon k-i-tick'></span> # } #" />
			<kendo:grid-column>
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit"></kendo:grid-column-commandItem>
					<kendo:grid-column-commandItem name="destroy"></kendo:grid-column-commandItem>
				</kendo:grid-column-command>
			</kendo:grid-column>
		</kendo:grid-columns>
	</kendo:grid>

	<script>
	
		function supplierEditor(container, options) {
			$("<input data-text-field='SupplierName' data-value-field='SupplierID' data-bind='value:" + options.field + "' />")
			.appendTo(container)
			.kendoDropDownList({
				dataSource: {
					transport: {
						read: "api/suppliers"
					}
				}
			});
		};
		
		function categoryEditor(container, options) {
			$("<input data-text-field='CategoryName' data-value-field='CategoryID' data-bind='value:" + options.field + "' />")
			.appendTo(container)
			.kendoDropDownList({
				dataSource: {
					transport: {
						read: "api/categories"
					}
				}
			});
		};
	
	</script>

</body>
</html>