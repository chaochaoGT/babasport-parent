<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../head.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>babasport-list</title>
<script>


	//删除
    function optDelete() {
        var checkedSize= $("input[name='ids']:checked").size();
        if(checkedSize==0){
            alert("至少选择一行");
            return;
        }

        if(!confirm("确认删除吗?")){
            return;
        }

        //封装上下架的隐藏域
        $("input[name='isShow']").val(0);
        $("#jvForm").attr("action","updateIsShow.do");
        //提交 Form表单
        $("#jvForm").submit();

    }
//上架
function updateIsShow(isShow){
	//请至少选择一个
	var size = $("input[name='ids']:checked").size();
	if(size == 0){
		alert("请至少选择一个");
		return;
	}
	//你确定删除吗
	if(!confirm("你确定上架或下架吗")){
		return;
    }
    //封装上下架的隐藏域
    $("input[name='isShow']").val(isShow);
    $("#jvForm").attr("action","updateIsShow.do");
    //提交 Form表单
	$("#jvForm").submit();

}
</script>
</head>
<body>
<div class="box-positon">
	<div class="rpos">当前位置: 商品管理 - 列表</div>
	<form class="ropt">
		<input class="add" type="button" value="添加" onclick="window.location.href='showAdd.do'"/>
	</form>
	<div class="clear"></div>
</div>
<div class="body-box">
<form action="list.do" method="post" style="padding-top:5px;">
	<input type="hidden" name="pageNum" value="${productPage.pageNum}"/>
	<input type="hidden" name="pageSize" value="${productPage.pageSize}"/>
名称: <input type="text" name="name" value="${name}"/>
	<select name="brandId"  >
		<option value="" >请选择品牌</option>
		<c:forEach items="${brands}" var="brand">
		<option value="${brand.id}">${brand.name}</option>
		</c:forEach>
	</select>

	<select name="isShow" >
		<option  value="">请选择</option>
		<option  value="1">上架</option>
		<option  value="0">下架</option>
	</select>
	<script>

        $(function () {
            $("select[name='brandId']").val("${brandId}");
            $("select[name='isShow']").val("${isShow}");
        });
	</script>
	<input type="submit" class="query" value="查询"/>
</form>
<form id="jvForm" method="post">
	<input type="hidden" name="name" value="${name}"/>
	<input type="hidden" name="isShow" value="${isShow}"/>
	<input type="hidden" name="brandId" value="${brandId}"/>
	<input type="hidden" name="pageNum" value="${productPage.pageNum}"/>
	<input type="hidden" name="pageSize" value="${productPage.pageSize}"/>


<table cellspacing="1" cellpadding="0" width="100%" border="0" class="pn-ltable">
	<thead class="pn-lthead">
		<tr>
			<th width="20"><input type="checkbox" onclick="Pn.checkbox('ids',this.checked)"/></th>
			<th>商品编号</th>
			<th>商品名称</th>
			<th>图片</th>
			<th width="4%">新品</th>
			<th width="4%">热卖</th>
			<th width="4%">推荐</th>
			<th width="4%">上下架</th>
			<th width="12%">操作选项</th>
		</tr>
	</thead>
	<tbody class="pn-ltbody">
		<c:forEach items="${productPage.result}" var="product">
		<tr bgcolor="#ffffff" onmouseover="this.bgColor='#eeeeee'" onmouseout="this.bgColor='#ffffff'">
			<td><input type="checkbox" name="ids" value="${product.id}"/></td>
			<td>${product.id}</td>
			<td align="center">${product.name}</td>
			<td align="center">
				<c:forTokens items="${product.imgUrl}" delims="," var="iu" begin="0" end="0">

					<img width="50" height="50" src="${iu}"/></td>
				</c:forTokens>
			<td align="center">${product.isNew}</td>
			<td align="center">${product.isHot}</td>
			<td align="center">${product.isCommend}</td>
			<td align="center">${product.isShow}</td>
			<td align="center">
			<a href="#" class="pn-opt">查看</a> |
			<a href="#" class="pn-opt">修改</a> |
			<a href="#" onclick="if(!confirm('您确定删除吗？')) {return false;}" class="pn-opt">删除</a> |
			<a href="../sku/list.do?productId=${product.id}" class="pn-opt">库存</a>
			</td>
		</tr>
		</c:forEach>
	</tbody>
</table>
<div class="page pb15">

	<span class="r inb_a page_b">

        <a href="list.do?&amp;pageNum=1&amp;pageSize=${productPage.pageSize}&amp;name=${name}&amp;isShow=${isShow}">
         <font size="2">首页</font></a>

        <c:if test="${productPage.pageNum gt 1}">
        <a href="list.do?&amp;
        pageNum='${productPage.pageNum-1}'&amp;pageSize=${productPage.pageSize}&amp;name=${name}&amp;
        isShow=${isShow}"> <font size="2">上一页</font></a>
		</c:if>

	        <c:forEach begin="${productPage.pageNum-4 lt 0?1:productPage.pageNum-4 }" 			end="${productPage.pageNum+5 gt productPage.pages?productPage.pages:productPage.pageNum+5 }" var="pg">
				<%--第一页--%>
				<c:if test="${pg==productPage.pageNum}"><strong>${pg}</strong></c:if>
				<%--其他页--%>
				<c:if test="${pg!=productPage.pageNum}">
          <a href="list.do?&amp;pageNum=${pg}&amp;name=${name}&amp;isShow=${isShow}">
            <font size="2"><font size="2">${pg}</font></font></a>
				</c:if>
			</c:forEach>

	        <c:if  test="${productPage.pageNum<productPage.total}">
		        <a href="list.do?&amp;pageNum=${productPage.pageNum+1}&amp;pageSize=${productPage.pageSize}
		        &amp;name=${name}&amp;isShow=${isShow}"><font size="2">下一页</font></a>
			</c:if>
		<a href="list.do?&amp;pageNum=${productPage.pages}&amp;pageSize=${productPage.pageSize}
		        &amp;name=${name}&amp;isShow=${isShow}"><font size="2">尾页</font></a>

		共<var>${productPage.pages}</var>页 每页<input type="text" align="center" value="${productPage.pageSize}"
												 size="3" id="PAGENO"/>条 <input type="button"
		 onclick="javascript:window.location.href = 'list.do?&amp;pageNum=${productPage.pageNum}&amp;pageSize=' +
				 $('#PAGENO').val() " value="确定" class="hand btn60x20" id="skip"/>

	</span>
</div>
<div style="margin-top:15px;"><input class="del-button" type="button" value="删除" onclick="optDelete()"/>

	<input class="add" type="button" value="上架" onclick="updateIsShow(1)"/>

	<input class="del-button" type="button"
    value="下架" onclick="updateIsShow(0);"/></div>
</form>
</div>
</body>
</html>