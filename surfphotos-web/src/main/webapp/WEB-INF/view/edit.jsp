<%--
  Created by IntelliJ IDEA.
  User: Drew
  Date: 10.07.2020
  Time: 11:12
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="tags"   tagdir="/WEB-INF/tags"%>

<tags:edit-form gotoProfileAvailable="false"
                header="Edit my profile"
                isUploadAvatarAvailable="true"
                isAgreeCheckBoxAvailable="false"
                isCancelBtnAvailable="true"
                saveAction="/save"
                saveCaption="Save changes" />
