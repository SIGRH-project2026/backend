package sn.gainde2000.backenmfpai.commons.Notification;


import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

public interface INotification {
    Response<Object>  notifyUser(Notification notification);
    Response<Object> getNotifiesByUser(int page, int pageSize,Long idUser,  String codeProfile);
    Response<Object> notifyIsRead(Long idNotification);
    Response<Object> getListNotifiesByUser(Long idUser, String codeProfile);
}
