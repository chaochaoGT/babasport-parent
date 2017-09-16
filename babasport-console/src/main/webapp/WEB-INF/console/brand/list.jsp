<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../head.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>babasport-list</title>
	<script>
		function checkBox(ids,checked) {
			$("#tableList input[type='checkbox']").attr("checked", checked);
//			var checkBoxSezi=$("input[type='checked']");
//			alert(checkBoxSezi.size());
//			if(checkBoxSezi.size()==0){
//				$("input[name='ids']").attr("checked",'checked');
//			}else {
//				$("input[name='ids']").removeAttr("checked");
//			}
		}

		function optDelete(name,isDisplay,pageNum,pageSize) {
			var checkedSize= $("input[name='ids']:checked").size();
			if(checkedSize==0){
				alert("至少选择一行");
				return;
			}

			if(!confirm("确认删除吗?")){
				return;
			}

			$("#formList")[0].action="deleteByids.do?&pageNum="+pageNum+
					"&pageSize="+pageSize;
			$("#formList").submit();

		}

	</script>
</head>
<body>
<div class="box-positon">
	<div class="rpos">当前位置: 品牌管理 - 列表</div>
	<form class="ropt">
		<input class="add" type="button" value="添加" onclick="javascript:window.location.href='add.do'"/>
	</form>
	<div class="clear"></div>
</div>
<div class="body-box">
<form action="list.do" method="get" style="padding-top:5px;">
品牌名称: <input type="text" name="name" value="${name}"/>
	<select name="isDisplay">
		<option value="1">是</option>
		<option value="0">否</option>
	</select>
    <script>
        $(function () {
           $("select[name='isDisplay']").val("${isDisplay}")

        });

    </script>
	<input type="submit" class="query" value="查询"/>
</form>
	<form id="formList" method="post" action="#deleteByids.do">

		<input  type="hidden" name="name" value="${name}"  />
		<input type="hidden" name="isDisplay" value="${isDisplay}" />
<table  id="tableList" cellspacing="1" cellpadding="0" border="0" width="100%" class="pn-ltable">
	<thead class="pn-lthead">
		<tr>
			<th width="20"><input type="checkbox" onclick="checkBox('ids',this.checked)"/></th>
			<th>品牌ID</th>
			<th>品牌名称</th>
			<th>品牌图片</th>
			<th>品牌描述</th>
			<th>排序</th>
			<th>是否可用</th>
			<th>操作选项</th>
		</tr>
	</thead>
    <tbody class="pn-ltbody">

	<c:forEach  items="${brandPage.result}"  var="brand">

		<tr bgcolor="#ffffff" onmouseout="this.bgColor='#ffffff'"
			onmouseover="this.bgColor='#eeeeee'">
			<td><input type="checkbox" value="${brand.id}" name="ids" /></td>
			<td align="center">${brand.id}</td>
			<td align="center">${brand.name}</td>
			<td align="center"><img width="40" height="40"
									src="${brand.imgUrl}" /></td>
			<td align="center">${brand.description}</td>
			<td align="center">${brand.sort}</td>
			<td align="center">
				<c:if test="${brand.isDisplay==1}">是</c:if>
				<c:if test="${brand.isDisplay==0}">否</c:if>
			</td>
			<td align="center"><a class="pn-opt" href="showEdit.do?&amp;brandID=${brand.id}">修改</a> | <a
					class="pn-opt" onclick="if(!confirm('您确定删除吗？')) {return false;}"
					href="#">删除</a></td>
		</tr>
	</c:forEach>


    </tbody>
</table>
	</form>
<div class="page pb15">
	<span class="r inb_a page_b">

        <a href="list.do?&amp;pageNum=1&amp;pageSize=${brandPage.pageSize}&amp;name=${name}&amp;isDisplay=${isDisplay}">
         <font size="2">首页</font></a>



        <c:if test="brandPage.pageNum<=1">
         <font size="2">上一页</font></c:if>
        <c:if test="brandPage.pageNum>1">
        <a href="list.do?&amp;
        pageNum='${brandPage.pageNum-1}'&amp;pageSize=${brandPage.pageSize}&amp;name=${name}&amp;
        isDisplay=${isDisplay}"> <font size="2"><font size="2">上一页</font></font></a>
        </c:if>

	        <c:forEach begin="1" end="${brandPage.pages}" var="pg">
                <%--第一页--%>
                <c:if test="${pg==brandPage.pageNum}"><strong>${pg}</strong></c:if>
                <%--其他页--%>
                <c:if test="${pg!=brandPage.pageNum}">
          <a href="list.do?&amp;pageNum=${pg}&amp;name=${name}&amp;isDisplay=${isDisplay}">
            <font size="2"><font size="2">${pg}</font></font></a>
                </c:if>
            </c:forEach>

	        <c:if  test="${brandPage.pageNum<brandPage.total}">
		        <a href="list.do?&amp;pageNum=${brandPage.pageNum+1}&amp;pageSize=${brandPage.pageSize}
		        &amp;name=${name}&amp;isDisplay=${isDisplay}"><font size="2">下一页</font></a>
            </c:if>
		<a href="list.do?&amp;pageNum=${brandPage.pages}&amp;pageSize=${brandPage.pageSize}
		        &amp;name=${name}&amp;isDisplay=${isDisplay}"><font size="2">尾页</font></a>
	
		共<var>${brandPage.pages}</var>页 每页<input type="text" align="center" value="${brandPage.pageSize}" size="3" id="PAGENO"/>条 <input type="button"
       onclick="javascript:window.location.href = 'list.do?&amp;pageNum=${brandPage.pageNum}&amp;pageSize=' + $('#PAGENO').val() " value="确定" class="hand btn60x20" id="skip"/>
	
	</span>
</div>
<div style="margin-top:15px;"><input class="del-button" type="button" value="删除" onclick="optDelete('${name}','${isDisplay}','${brandPage.pageNum}','${brandPage.pageSize}');"/></div>
</div>
</body>
</html>