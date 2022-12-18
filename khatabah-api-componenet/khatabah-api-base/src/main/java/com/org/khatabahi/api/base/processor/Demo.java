package com.org.khatabahi.api.base.processor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Demo {

    public static void main(String[] a){

        List<String> testList  = Arrays.asList("sdsdf", "abcds", "addsed", "azsxd"
        );

        List<String>  testListRes = testList.stream().filter(test -> test.startsWith("a")).collect(Collectors.toList());
        System.out.println(testListRes);

    }
}
