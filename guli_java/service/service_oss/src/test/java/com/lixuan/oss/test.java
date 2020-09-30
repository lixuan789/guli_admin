package com.lixuan.oss;

public class test {
    public static void main(String[] args) {
        String filename="https://lixuan-file.oss-cn-beijing.aliyuncs.com/avatar/cover.jpg";
        String[] split = filename.split("/");
        String a="";
        for (int i=3;i<split.length;i++){
            if (i!=split.length-1){
                a=a+split[i]+"/";
            }else {
                a=a+split[i];
            }
        }
        System.out.println(a);
    }
}
