<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
 <kendo:treeView name="employees" dataTextField="FullName">
 	<kendo:dataSource>
 		<kendo:dataSource-transport read="api/employees">
 			<kendo:dataSource-transport-parameterMap>
 				<script>
 					function parameterMap(options, operation) {
 						if (operation === "read") {
 							return { EmployeeID: options.EmployeeID };
 						} 
 						else {
 							return options;
 						}
 					}
 				</script>
 			</kendo:dataSource-transport-parameterMap>
 		</kendo:dataSource-transport>
 		<kendo:dataSource-schema>
 			<kendo:dataSource-schema-hierarchical-model hasChildren="HasChildren" id="EmployeeID"></kendo:dataSource-schema-hierarchical-model>
 		</kendo:dataSource-schema>
 	</kendo:dataSource>
 </kendo:treeView>
</body>
</html>