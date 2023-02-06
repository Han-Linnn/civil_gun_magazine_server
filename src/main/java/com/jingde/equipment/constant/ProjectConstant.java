package com.jingde.equipment.constant;

/**
 * 项目客观常量（包名）
 *
 * @author
 */
public final class ProjectConstant {
    // 生成代码所在的基础包名称，与src目录项目默认的包路径保持一致
    public static final String BASE_PACKAGE = "com.jingde.equipment";
    private static final String APP_PACKAGE = BASE_PACKAGE + ".app.*";
    // 生成的Model所在包
    public static final String MODEL_PACKAGE = APP_PACKAGE + ".model";
    // 生成的Mapper所在包
    public static final String MAPPER_PACKAGE = APP_PACKAGE + ".dao";
    // 生成的Service所在包
    public static final String SERVICE_PACKAGE = APP_PACKAGE + ".service";
    // 生成的ServiceImpl所在包
    public static final String SERVICE_IMPL_PACKAGE = APP_PACKAGE + ".impl";
    // 生成的Controller所在包
    public static final String CONTROLLER_PACKAGE = APP_PACKAGE + ".controller";
}
