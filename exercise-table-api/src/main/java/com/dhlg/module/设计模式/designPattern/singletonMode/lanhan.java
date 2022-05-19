package com.dhlg.module.设计模式.designPattern.singletonMode;

public class lanhan {
    // 懒汉
    private  lanhan(){
    }
    private static volatile lanhan lanhan = null;

    public static synchronized lanhan getLanhan(){
        if (lanhan == null){
            lanhan = new lanhan();
        }
        return lanhan;
    }
}
