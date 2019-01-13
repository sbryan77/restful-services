<%@ page import="java.io.File, java.io.FileFilter" %>
<html>

<header>
    <title>${project.parent.artifactId}</title>
</header>
<head>
<style>
.panel {
  margin-bottom: 20px;
  background-color: #205081;
  border: 1px solid transparent;
  font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 14px;
}
.panel-heading {
  padding: 10px 15px;
  border-bottom: 1px solid transparent;
  border-top-right-radius: 3px;
  border-top-left-radius: 3px;
}
.panel-primary {
  border-color: #dddddd;
}
</style>
</head>
<body style='font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;font-size: 14px'>
<div class="panel panel-primary">
    <div class="panel-heading">
        <h1><font color="white">${context.root}</font> <img style=float:right;" src="viasat.png" alt="ViaSat"></h1>
    </div>
</div>
<table style='font-size:1.5em;'>
<tr><th colspan="3" align="left" >Services</th></tr>
<%
    File dir = new File(application.getRealPath("/"));

    FileFilter filter = new FileFilter()
    {
        @Override
        public boolean accept(File pathname)
        {
            return (pathname.isFile() && pathname.getPath().endsWith("_swagger.json"));
        }
    };

    File[] files = dir.listFiles(filter);

    for (File file : files)
    {
        out.println("<tr><td>" + file.getName().replace("_swagger.json", "") + ": </td>");
        out.println(
                "<td>View as <a href='services/api-docs?url=/${context.root}${context.separator.deploy}${context.version}/"
                        + file.getName() + "'>swagger</a></td>");

        out.println("<td> or as <a href='" + file.getName() + "'>json</a></td></tr>");
    }
%>
</table>
<br/>
<!--
<hr style="border: 0;height: 1px;background-image: linear-gradient(to right, rgba(0, 0, 0, 0), rgba(0, 0, 0, 0.75), rgba(0, 0, 0, 0));"/>
-->
<hr style="border: 0;height: 0;border-top: 1px solid rgba(0, 0, 0, 0.1);border-bottom: 1px solid rgba(255, 255, 255, 0.3);"/>
<span style='font-size:1.5em;font-weight:bold;'>Application: </span><span style='font-size:1.5em;font-weight:normal;'><a href="applicationstatus">Status</a></span>
<div style="position: absolute; bottom: 5px;font-size:small">Copyright &copy; 2016 ViaSat, Inc. All Rights Reserved.</div>
</body>
</html>