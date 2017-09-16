<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../head.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>babasport-list</title>
	<script >
		//显示修改框
        function showUpdateSku(id){
			$("#m"+id).attr("disabled",false);
			$("#p"+id).attr("disabled",false);
			$("#s"+id).attr("disabled",false);
			$("#d"+id).attr("disabled",false);
			$("#u"+id).attr("disabled",false);
		}
		//进行修改sku
		function toUpdateSku(id) {
			$.post("toUpdateSku.do",
				{"marketPrice":$("#m"+id).val(),
				"price":$("#p"+id).val(),
				"stock":$("#s"+id).val(),
				"deliveFee":$("#d"+id).val(),
				"upperLimit":$("#u"+id).val(),
				"id":id
				},
				function(data){
                    $("#m"+id).attr("disabled",true);
                    $("#p"+id).attr("disabled",true);
                    $("#s"+id).attr("disabled",true);
                    $("#d"+id).attr("disabled",true);
                    $("#u"+id).attr("disabled",true);
			})
        }

	</script>
</head>
<body>
<div class="box-positon">
	<div class="rpos">当前位置: 库存管理 - 列表</div>
	<div class="clear"></div>
</div>
<div class="body-box">
<form method="post" id="tableForm">
<table cellspacing="1" cellpadding="0" border="0" width="100%" class="pn-ltable">
	<thead class="pn-lthead">
		<tr>
			<th width="20"><input type="checkbox" onclick="Pn.checkbox('ids',this.checked)"/></th>
			<th>商品编号</th>
			<th>商品颜色</th>
			<th>商品尺码</th>
			<th>市场价格</th>
			<th>销售价格</th>
			<th>库       存</th>
			<th>购买限制</th>
			<th>运       费</th>
			<th>是否赠品</th>
			<th>操       作</th>
		</tr>
	</thead>
	<tbody class="pn-ltbody">
		<c:forEach items="${skuList}" var="sku">
			<tr bgcolor="#ffffff" onmouseover="this.bgColor='#eeeeee'" onmouseout="this.bgColor='#ffffff'">
				<td><input type="checkbox" name="ids" value="${sku.id}"/></td>
				<td>${sku.id}</td>
				<td align="center">${sku.colorName}</td>
				<td align="center">${sku.size}</td>
				<td align="center"><input type="text" id="m${sku.id}" value="${sku.market_price}" disabled="disabled" size="10"/></td>
				<td align="center"><input type="text" id="p${sku.id}" value="${sku.price}" disabled="disabled" size="10"/></td>
				<td align="center"><input type="text" id="s${sku.id}" value="${sku.stock}" disabled="disabled" size="10"/></td>
				<td align="center"><input type="text" id="d${sku.id}" value="${sku.delive_fee}" disabled="disabled" size="10"/></td>
				<td align="center"><input type="text" id="u${sku.id}" value="${sku.upper_limit}" disabled="disabled" size="10"/></td>
				<td align="center">不是</td>
				<td align="center"><a href="javascript:showUpdateSku(${sku.id})" class="pn-opt">修改</a> | <a href="javascript:toUpdateSku(${sku.id})" class="pn-opt">保存</a></td>
			</tr>
		</c:forEach>
	</tbody>
</table>
</form>
</div>
</body>
</html>