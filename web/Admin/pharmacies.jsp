<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<link rel="stylesheet" type="text/css" href="../style.css">
<html>
<head>
    <title>Drugs</title>
</head>
<body>
<div class="flex-container">
<header>
    <h1>All Pharmacies in your network, Mr. <s:property value="username"/>.
        Your network id = <s:property value="networkId"/>
    </h1>
</header>
    <nav class="nav">
        <ul>
            <li><a href="/admin/createph.action">Create New</a></li>
            <li><a href="requests.action">View requests</a> </li>
            <li><a href="/user/info">My credentials</a></li></li>
            <li> <a href="pharmacists.action">Pharmacists</a></li>
            <li> <a href="welcome.action">Dashboard</a></li>
            <li> <a href="logout.action">logout</a></li>

        </ul>
    </nav>
    <article class="article">
<s:if test="list.size() > 0">
    <div>
        <table cellpadding="5px" border="1">
            <tr>
                <th>Number</th>
                <th>Pharmacist</th>
                <th>Address</th>
            </tr>

            <s:iterator value="list">
                <tr>
                    <td><s:property value="number"/></td>
                    <td><s:property value="pharmacistId"/></td>
                    <td><s:property value="address"/></td>
                    <td><s:url id="deleteURL" action="phdelete">
                        <s:param name="id" value="%{id}"></s:param>
                    </s:url>
                        <s:a href="%{deleteURL}" onclick="return confirm('Are you sure?')">Delete</s:a></td>

                    <td><s:url id="histURL" action="history">
                        <s:param name="id" value="%{id}"></s:param>
                    </s:url>
                        <s:a href="%{histURL}">Operation history</s:a></td>

                    <td>
                        <s:url id="contentURL" action="stock">
                        <s:param name="id" value="%{id}"></s:param>
                    </s:url>
                        <s:a href="%{contentURL}">View Stock Content</s:a>

                        <s:url id="pdfURL" action="stockPDF">
                            <s:param name="id" value="%{id}"></s:param>
                        </s:url><br>
                        <s:a href="%{pdfURL}">PDF</s:a>|


                        <s:url id="xlsURL" action="stockXLS">
                            <s:param name="id" value="%{id}"></s:param>
                        </s:url>
                        <s:a href="%{xlsURL}">XLS</s:a>|

                        <s:url id="csvURL" action="stockCSV">
                            <s:param name="id" value="%{id}"></s:param>
                        </s:url>
                        <s:a href="%{csvURL}">CSV</s:a>

                    </td>

                    <td><s:url id="editURL" action="edit">
                        <s:param name="id" value="%{id}"></s:param>
                    </s:url>
                        <s:a href="%{editURL}">Edit</s:a></td>

                    <td><s:url id="infoURL" action="info">
                        <s:param name="id" value="%{id}"></s:param>
                    </s:url>
                        <s:a href="%{infoURL}">Info</s:a></td>
                </tr>
            </s:iterator>
        </table>
    </div>
</s:if>
<s:if test="hslist.size() = 0">
    <h1>The list is empty yet.</h1>
</s:if>
    </article>
<s:actionerror/>

<footer>
    <p id="copyright">Copyright 2016, Loosers inc.</p>
</footer>
</div>
</body>
</html>