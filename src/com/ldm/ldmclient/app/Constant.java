package com.ldm.ldmclient.app;

/**
 * fixed var
 * Created by LDM on 2014/6/23. Email : nightkid-s@163.com
 */
public interface Constant {

    boolean DebugMode = true;

    String ACTION_EXIT = "finish_all_activity_teacher"; //exit tag

    String DEFAULT_CHARSET_NAME = "UTF-8"; //default global charset

    //regular expression
    String IMG_FILE_NAME_REGEX = "^.+\\.(?i)(jpg|png|gif|jpeg|raw|bmp)$"; //匹配网络或者本地图片
    String REMOTE_PATH_REGEX = "^(http://\\w+)(\\.(?i)(?:jpg|png|gif|jpeg|raw|bmp))$"; //匹配网络图片
    String DATE_PATTERN1 = "(\\d{1,4}-(\\d{1,2}-\\d{1,2}))\\b.*"; //日期 xxxx-xx-xx
    String DATE_PATTERN2 = "\\d{1,4}-\\d{1,2}-\\d{1,2}\\s+(\\d{1,2}:\\d{1,2}):\\d{1,2}"; //日期 xx:xx
    String DATE_PATTERN3 = "\\d{1,4}-(\\d{1,2})-(\\d{1,2})\\s+(\\d{1,2}:\\d{1,2}):\\d{1,2}"; //日期 xxxx-xx-xx xx:xx:xx
    String PHONE_PATTERN = "^1[0-9][0-9]{9}$"; //匹配电话号码
    String PSW_PATTERN = "^[a-zA-Z0-9]{6,18}$"; //匹配6到18位的密码
    String IGNORE_CASE_PATTERN = ".*(?i:%s).*"; //忽略大小写
    String CHINESE_PATTERN = "[\\u4E00-\\u9FA5].*";//匹配中文
    String DIGIT_PATTERN = "\\d+"; //匹配有理正整数
    String LETTER_PATTERN = "[\\p{Alpha}]+"; //匹配英文
    String LETTER_DIGIT_PATTERN = "[\\d\\p{Alpha}]+"; //匹配数字和英文
    String DIGIT_PATTERN2 = "(?>-?)\\d+"; //匹配有理整数
    String FOOD_DATA_PATTERN = "[\\p{Alnum}\u4E00-\u9FA5\\s~`!！@#$￥%^…&*()（）\\-+=,，.。?？;；‘’':：\"“”/、]+"; //匹配中文英文数字和部分标点符号

}
