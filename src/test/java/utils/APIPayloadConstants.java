package utils;

import org.json.JSONObject;

public class APIPayloadConstants {

    public static String createEmployeePayload(){
        return "{\n" +
                "  \"emp_firstname\": \"Moqaddas\",\n" +
                "  \"emp_lastname\": \"Rahim\",\n" +
                "  \"emp_middle_name\": \"JOJO\",\n" +
                "  \"emp_gender\": \"M\",\n" +
                "  \"emp_birthday\": \"2000-11-06\",\n" +
                "  \"emp_status\": \"employed\",\n" +
                "  \"emp_job_title\": \"developer\"\n" +
                "}";
    }

    public static String createEmployeeJsonPayload(){
        JSONObject obj = new JSONObject();
        obj.put("emp_firstname","Moqaddas");
        obj.put("emp_lastname","Rahim");
        obj.put("emp_middle_name","JOJO");
        obj.put("emp_gender","M");
        obj.put("emp_birthday","2000-11-06");
        obj.put("emp_status","employed");
        obj.put("emp_job_title","developer");
        return obj.toString();
    }

    public static String createEmployeeJsonPayloadDynamic(String emp_firstname,
                                                          String emp_lastname,
                                                          String emp_middle_name,
                                                          String emp_gender,
                                                          String emp_birthday,
                                                          String emp_status,
                                                          String emp_job_title){
        JSONObject obj = new JSONObject();
        obj.put("emp_firstname",emp_firstname);
        obj.put("emp_lastname",emp_lastname);
        obj.put("emp_middle_name",emp_middle_name);
        obj.put("emp_gender",emp_gender);
        obj.put("emp_birthday",emp_birthday);
        obj.put("emp_status",emp_status);
        obj.put("emp_job_title",emp_job_title);
        return obj.toString();
    }


}
