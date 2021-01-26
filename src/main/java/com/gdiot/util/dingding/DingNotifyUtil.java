package com.gdiot.util.dingding;

import java.io.*;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.gdiot.constant.DingDingConstants;
import com.gdiot.service.DingDeptService;
import com.gdiot.service.DingUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.gdiot.entity.DingDept;
import com.gdiot.entity.DingProcess;
import com.gdiot.entity.DingUser;
import com.gdiot.util.PropertiesUtil;
import com.taobao.api.ApiException;

/**
 * 钉钉通知工具类
 *
 * @author ZhouHR
 * @date 2021/01/26 15:18
 **/
@Slf4j
public class DingNotifyUtil {
    private static DingUserService dingUserService;
    private static DingDeptService dingDeptService;

    public static OapiMessageCorpconversationAsyncsendV2Response sendMessage(String userIdList, String textMsg,
        String accessToken) {
        try {
            DingTalkClient client =
                new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");

            OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
            request.setUseridList(userIdList);
            request.setAgentId(DingDingConstants.AGENTID);
            request.setToAllUser(false);

            OapiMessageCorpconversationAsyncsendV2Request.Msg msg =
                new OapiMessageCorpconversationAsyncsendV2Request.Msg();
            msg.setMsgtype("text");
            msg.setText(new OapiMessageCorpconversationAsyncsendV2Request.Text());
            msg.getText().setContent(textMsg);
            request.setMsg(msg);

            OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request, accessToken);
            System.out.println("getToken()-----" + response);
            return response;
        } catch (ApiException e) {
            e.printStackTrace();
        }
        OapiMessageCorpconversationAsyncsendV2Response res = new OapiMessageCorpconversationAsyncsendV2Response();
        res.setCode("1");
        res.setBody("send msg error!");
        res.setErrcode(1L);
        res.setErrmsg("send msg error!");
        return res;
    }

    /**
     * 生成Excel文件，每行写入内容.开票申请报表
     *
     * @param dingProcessList
     * @param name
     */
    public static void CreateProcessFile(List<DingProcess> dingProcessList, String name) {
        // 获取存放路径 excel
        String path = PropertiesUtil.getValue("path");
        String templatePath = PropertiesUtil.getValue("template");

        String fileName = path + name;

        InputStream in = null;
        OutputStream out = null;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        File file;

        try {
            in = new FileInputStream(templatePath);
            Workbook workbook = new XSSFWorkbook(in);
            Sheet sheet = workbook.getSheetAt(0);
            int rowNum = 1;
            if (dingProcessList != null && dingProcessList.size() > 0) {
                for (DingProcess dingProcess : dingProcessList) {
                    Row row = sheet.createRow(rowNum);
                    rowNum++;

                    int cellNum = 0;
                    Cell cell = row.createCell(cellNum);
                    cell.setCellValue(rowNum - 1);
                    cellNum++;

                    // 开票申请人
                    String userId = dingProcess.getOriginatorUserId();
                    DingUser dingUser = dingUserService.selectOne(userId);
                    cell = row.createCell(cellNum);
                    cell.setCellValue(dingUser.getName());
                    cellNum++;

                    // 金额
                    cell = row.createCell(cellNum);
                    cell.setCellValue(dingProcess.getInvoiceAmount());
                    cellNum++;

                    // 项目
                    cell = row.createCell(cellNum);
                    cell.setCellValue(dingProcess.getInvoiceContent());
                    cellNum++;

                    // 所属部门
                    String deptId = dingProcess.getOriginatorDeptId();
                    DingDept dingDept = dingDeptService.selectOne(deptId);
                    cell = row.createCell(cellNum);
                    cell.setCellValue(dingDept.getDeptName());
                    cellNum++;

                    // 部门领导
                    List<String> userIdList = Arrays.asList(dingDept.getDeptManagerId().split(","));
                    List<String> managerNameList = null;
                    for (String managerId : userIdList) {
                        DingUser manager = dingUserService.selectOne(managerId);
                        managerNameList.add(manager.getName());
                    }
                    cell = row.createCell(cellNum);
                    cell.setCellValue(managerNameList.toString());
                    cellNum++;

                    // 申请回款时间
                    cell = row.createCell(cellNum);
                    cell.setCellValue(dingProcess.getInvoiceFinishTime());
                    cellNum++;

                    // 客户
                    cell = row.createCell(cellNum);
                    cell.setCellValue(dingProcess.getInvoiceCompany());
                    cellNum++;

                }
            } else {
                log.info("---------CreateAssessFile list null-------");
            }

            file = new File(fileName);
            out = new FileOutputStream(file);
            workbook.write(out);
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
