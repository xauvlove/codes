package com.xauv;

import com.xauv.model.ModelDO;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author ling yue
 * @Date 2021/6/7 下午8:10
 * @Pkg com.xauv
 * @Desc description
 */
public class MapperXml生成 {
    private static String head = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
            "<!DOCTYPE mapper\n" +
            "        PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n" +
            "<mapper namespace=\"xx.xx.xx.xx.xx.xx.xx.xxxDAO\">\n";

    private static String tableName = "\t<sql id=\"tableName\">\n" +
            "<#>\n" +
            "\t</sql>\n";

    private static String allColumn = "\t<sql id=\"allColumn\">\n" +
            "<#>\n" +
            "\t</sql>\n";

    private static String efficientColumn = "\t<sql id=\"efficientColumn\">\n" +
            "<#>\n" +
            "\t</sql>\n";

    private static String selectiveKeys = "\t<sql id=\"selectiveKeys\">\n" +
            "<#>\n" +
            "\t</sql>\n";

    private static String selectiveValues = "\t<sql id=\"selectiveValues\">\n" +
            "<#>\n" +
            "\t</sql>\n";

    private static String selectiveKeyValues = "\t<sql id=\"selectiveUpdateKeyValues\">\n" +
            "<#>\n" +
            "\t</sql>\n";

    private static String insertSelective = "\t<insert id=\"insertSelective\" parameterType=\"xx.xx.xx.xx.xx.xx.xx.xxxDO\" useGeneratedKeys=\"true\" keyProperty=\"id\">\n" +
            "\t\tinsert into\n" +
            "\t\t<include refid=\"tableName\"></include>\n" +
            "\t\t<trim suffixOverrides=\",\" prefix=\"(\" suffix=\")\">\n" +
            "\t\t\t<include refid=\"selectiveKeys\"></include>\n" +
            "\t\t</trim>\n" +
            "\t\tvalues\n" +
            "\t\t<trim suffixOverrides=\",\" prefix=\"(\" suffix=\")\">\n" +
            "\t\t\t<include refid=\"selectiveValues\"></include>\n" +
            "\t\t</trim>\n" +
            "\t</insert>\n";

    private static String updateSelectiveById = "\t<update id=\"updateSelectiveById\" parameterType=\"xx.xx.xx.xx.xx.xx.xx.xxxDO\">\n" +
            "\t\tupdate\n" +
            "\t\t<include refid=\"tableName\"></include>\n" +
            "\t\tset\n" +
            "\t\t<trim suffixOverrides=\",\">\n" +
            "\t\t\t<include refid=\"selectiveUpdateKeyValues\"></include>\n" +
            "\t\t</trim>\n" +
            "\t\twhere id = #{id}\n" +
            "\t</update>\n";

    private static String tail = "</mapper>";

    public static void main(String[] args) {

        StringBuilder xmlContent = new StringBuilder();

        System.out.println("\n====================== 生成 ======================\n");

        xmlContent.append(head.replace("<#>", generateHeadContent(ModelDO.class)));
        xmlContent.append("\n");

        xmlContent.append(tableName.replace("<#>", generateTableNameContent(ModelDO.class)));
        xmlContent.append("\n");

        xmlContent.append(allColumn.replace("<#>", generateAllColumnsName(ModelDO.class)));
        xmlContent.append("\n");

        xmlContent.append(efficientColumn.replace("<#>", generateEfficientColumnsName(ModelDO.class)));
        xmlContent.append("\n");

        xmlContent.append(selectiveKeys.replace("<#>", generateSelectiveKeys(ModelDO.class)));
        xmlContent.append("\n");

        xmlContent.append(selectiveValues.replace("<#>", generateSelectiveValues(ModelDO.class)));
        xmlContent.append("\n");

        xmlContent.append(selectiveKeyValues.replace("<#>", generateSelectiveKeyValues(ModelDO.class)));
        xmlContent.append("\n");

        xmlContent.append(insertSelective);
        xmlContent.append("\n");

        xmlContent.append(updateSelectiveById);
        xmlContent.append("\n");

        xmlContent.append(tail);

        System.out.println(xmlContent.toString());
    }

    private static String generateHeadContent(Class<?> clazz) {
        return clazz.getName();
    }

    private static String generateTableNameContent(Class<?> clazz) {

        StringBuilder stringBuilder = new StringBuilder();
        String name = clazz.getSimpleName();
        name = name.replace("DO", "");
        char[] chars = name.toCharArray();
        List<Character> list = new ArrayList<>();

        // 驼峰转下划线

        list.add(String.valueOf(chars[0]).toLowerCase().toCharArray()[0]);
        for (int i = 1; i < chars.length; i++) {
            char aChar = chars[i];
            if (aChar >= 65 && aChar <= 90) {
                aChar = Character.toLowerCase(aChar);
                list.add('_');
            }
            list.add(aChar);
        }
        char[] cs = new char[list.size()];
        for (int i = 0; i < list.size(); i++) {
            cs[i] = list.get(i);
        }
        stringBuilder.append("\t\t").append(String.valueOf(cs));
        return stringBuilder.toString();
    }

    public static String generateAllColumnsName(Class<?> clazz) {
        List<String> columns = new ArrayList<>();
        List<String> columns2 = new ArrayList<>();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\t").append("\t");
        for (Field declaredField : clazz.getDeclaredFields()) {
            String name = declaredField.getName();
            columns2.add(name);
            char[] chars = name.toCharArray();
            List<Character> list = new ArrayList<>();

            // 驼峰转下划线
            for (int i = 0; i < chars.length; i++) {
                char aChar = chars[i];
                if (aChar >= 65 && aChar <= 90) {
                    aChar = Character.toLowerCase(aChar);
                    list.add('_');
                }
                list.add(aChar);
            }

            char[] cs = new char[list.size()];
            for (int i = 0; i < list.size(); i++) {
                cs[i] = list.get(i);
            }
            columns.add(String.valueOf(cs));
            stringBuilder.append(String.valueOf(cs) + ", ");
        }
        return stringBuilder.toString();
    }

    public static String generateEfficientColumnsName(Class<?> clazz) {
        List<String> columns = new ArrayList<>();
        List<String> columns2 = new ArrayList<>();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\t").append("\t");

        for (Field declaredField : clazz.getDeclaredFields()) {
            String name = declaredField.getName();
            if (name.equalsIgnoreCase("id") || name.equalsIgnoreCase("createdAt") || name.equalsIgnoreCase("updatedAt")) {
                continue;
            }
            columns2.add(name);
            char[] chars = name.toCharArray();
            List<Character> list = new ArrayList<>();

            // 驼峰转下划线
            for (int i = 0; i < chars.length; i++) {
                char aChar = chars[i];
                if (aChar >= 65 && aChar <= 90) {
                    aChar = Character.toLowerCase(aChar);
                    list.add('_');
                }
                list.add(aChar);
            }
            char[] cs = new char[list.size()];
            for (int i = 0; i < list.size(); i++) {
                cs[i] = list.get(i);
            }
            columns.add(String.valueOf(cs));
            stringBuilder.append(String.valueOf(cs)).append(", ");
        }
        return stringBuilder.toString();
    }

    public static String generateSelectiveKeys(Class<?> clazz) {
        List<String> columns = new ArrayList<>();
        List<String> columns2 = new ArrayList<>();

        for (Field declaredField : clazz.getDeclaredFields()) {
            String name = declaredField.getName();
            columns2.add(name);
            char[] chars = name.toCharArray();
            List<Character> list = new ArrayList<>();

            // 驼峰转下划线
            for (int i = 0; i < chars.length; i++) {
                char aChar = chars[i];
                if (aChar >= 65 && aChar <= 90) {
                    aChar = Character.toLowerCase(aChar);
                    list.add('_');
                }
                list.add(aChar);
            }
            char[] cs = new char[list.size()];
            for (int i = 0; i < list.size(); i++) {
                cs[i] = list.get(i);
            }
            columns.add(String.valueOf(cs));
        }

        StringBuilder stringBuilder = new StringBuilder();
        columns.remove("id");
        columns2.remove("id");
        for (int i = 0; i < columns.size(); i++) {
            String column = columns.get(i);
            String column2 = columns2.get(i);
            String value = "\t\t<if test=\"" + column2 + " != null\">" +  column + ", </if>";
            stringBuilder.append(value);
            if (i < columns.size() - 1) {
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.append("\t").toString();
    }

    public static String generateSelectiveValues(Class<?> clazz) {
        List<String> columns = new ArrayList<>();
        List<String> columns2 = new ArrayList<>();

        for (Field declaredField : clazz.getDeclaredFields()) {
            String name = declaredField.getName();
            columns2.add(name);
            char[] chars = name.toCharArray();
            List<Character> list = new ArrayList<>();

            // 驼峰转下划线
            for (int i = 0; i < chars.length; i++) {
                char aChar = chars[i];
                if (aChar >= 65 && aChar <= 90) {
                    aChar = Character.toLowerCase(aChar);
                    list.add('_');
                }
                list.add(aChar);
            }
            char[] cs = new char[list.size()];
            for (int i = 0; i < list.size(); i++) {
                cs[i] = list.get(i);
            }
            columns.add(String.valueOf(cs));
        }
        columns.remove("id");
        columns2.remove("id");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < columns.size(); i++) {
            String column = columns.get(i);
            String column2 = columns2.get(i);
            String value = "\t\t<if test=\"" + column2 + " != null\">#{" +  column2 + "}, </if>";
            stringBuilder.append(value);
            if (i < columns.size() - 1) {
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }

    public static String generateSelectiveKeyValues(Class<?> clazz) {
        List<String> columns = new ArrayList<>();
        List<String> columns2 = new ArrayList<>();

        for (Field declaredField : ModelDO.class.getDeclaredFields()) {
            String name = declaredField.getName();
            columns2.add(name);
            char[] chars = name.toCharArray();
            List<Character> list = new ArrayList<>();

            // 驼峰转下划线
            for (int i = 0; i < chars.length; i++) {
                char aChar = chars[i];
                if (aChar >= 65 && aChar <= 90) {
                    aChar = Character.toLowerCase(aChar);
                    list.add('_');
                }
                list.add(aChar);
            }
            char[] cs = new char[list.size()];
            for (int i = 0; i < list.size(); i++) {
                cs[i] = list.get(i);
            }
            columns.add(String.valueOf(cs));
        }
        columns.remove("id");
        columns2.remove("id");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < columns.size(); i++) {
            String column = columns.get(i);
            String column2 = columns2.get(i);
            String value = "\t\t<if test=\"" + column2 + " != null\">" + column + " = #{"  +  column2 + "}, </if>";
            stringBuilder.append(value);
            if (i < columns.size() - 1) {
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }


    public static void main1(String[] args) {

        List<String> columns = new ArrayList<>();
        List<String> columns2 = new ArrayList<>();

        System.out.println("---------------------属性---------------------");
        for (Field declaredField : ModelDO.class.getDeclaredFields()) {
            String name = declaredField.getName();
            columns2.add(name);
            char[] chars = name.toCharArray();
            List<Character> list = new ArrayList<>();

            // 驼峰转下划线
            for (int i = 0; i < chars.length; i++) {
                char aChar = chars[i];
                if (aChar >= 65 && aChar <= 90) {
                    aChar = Character.toLowerCase(aChar);
                    list.add('_');
                }
                list.add(aChar);
            }
            char[] cs = new char[list.size()];
            for (int i = 0; i < list.size(); i++) {
                cs[i] = list.get(i);
            }
            columns.add(String.valueOf(cs));
            System.out.print(String.valueOf(cs) + ", ");
        }

        System.out.println();

        System.out.println("---------------------selectiveKeys---------------------");
        for (int i = 0; i < columns.size(); i++) {
            String column = columns.get(i);
            String column2 = columns2.get(i);
            String value = "<if test=\"" + column2 + " != null\">" +  column + ", </if>";
            System.out.println(value);
        }

        System.out.println("---------------------selectiveValues---------------------");
        for (int i = 0; i < columns.size(); i++) {
            String column = columns.get(i);
            String column2 = columns2.get(i);
            String value = "<if test=\"" + column2 + " != null\">#{" +  column2 + "}, </if>";
            System.out.println(value);
        }

        System.out.println("---------------------selectiveKeyValues---------------------");
        for (int i = 0; i < columns.size(); i++) {
            String column = columns.get(i);
            String column2 = columns2.get(i);
            String value = "<if test=\"" + column2 + " != null\">" + column + " = #{"  +  column2 + "}, </if>";
            System.out.println(value);
        }
    }
}