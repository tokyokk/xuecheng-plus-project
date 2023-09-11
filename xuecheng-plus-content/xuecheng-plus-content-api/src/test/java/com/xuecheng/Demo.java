package com.xuecheng;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author Mr.Z
 * @version 1.0
 * @description
 * @create 2023-08-06 22:00
 * @github https://github.com/Ragnarokoo
 */
public class Demo {

    public static void main(String[] args) {
        Demo demo = new Demo();
        final String str1 = "12312";
        final String str2 = "adad3312132345";
//        demo.diffStr(str1, str2);
        List<Integer> nums = Arrays.asList(2, 11,7, 15);
        sumIndex(nums,26);
    }

    private void diffStr(final String str1, final String str2) {
        List<String> str1List = Lists.newArrayList(str1.split(""));
        str1List.add(0, null);
        Integer startIndex = str2.indexOf(str1List.get(1));
        if (startIndex < 0) {
            System.out.println("未找到开始元素.");
            return;
        }
        String startStr = null;
        if (startIndex > 0) {
            startStr = str2.substring(0, startIndex);
        }
        final String strRealDiff = str2.substring(startIndex);
        List<String> str2List = Lists.newArrayList(strRealDiff.split(""));
        str2List.add(0, startStr);
        for (int i = 1; i < str2List.size(); i++) {
            final String str2Diff = str2List.get(i);
            Integer index = str1List.indexOf(str2Diff);
            if (index < 0 || index == i) continue;
            if (index > i) {
                for (int j = i; j < index; j++) {
                    str2List.add(j, "");
                }
            }
        }
        for (int i = 0; i < str1List.size(); i++) {
            final String str1Diff = str1List.get(i);
            final String str2Diff = str2List.get(i);
            if (Objects.equals(str1Diff, str2Diff)) continue;
            String typeName = "错误";
            String printStr = str1Diff;
            if (str1Diff == null) {
                typeName = "多出";
                printStr = str2Diff;
            }
            if (str2Diff == "") {
                typeName = "缺少";
            }
            System.out.println("索引" + i + "" + typeName + "元素" + printStr);

        }
    }

    public static void sumIndex(List<Integer> source,Integer target) {

        for (int i = 0; i < source.size(); i++) {
            for (int j = i+1; j < source.size(); j++) {
                int sum = source.get(i) + source.get(j);
                if (sum==target) {
                    System.out.println(i  +","+ j + " ");
                }
            }
        }
    }

}
