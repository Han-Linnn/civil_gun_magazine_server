package generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import static com.jingde.equipment.constant.ProjectConstant.*;

/**
 * Created by oceanover on 2019-06-11.
 */
public class MabatisPlusGenerator {
  // 项目在硬盘上的基础路径
  private static final String baseProjectPath = System.getProperty("user.dir");
  private static final String outPutDir = baseProjectPath + "/src/main/java";
  //基本包名
  private static final String basePackage = BASE_PACKAGE;
  //作者
  private static final String authorName = "jingde";
  //数据库配置
  private static final String driverName = "com.mysql.cj.jdbc.Driver";
  private static final String url = "jdbc:mysql://47.107.180.247:3306/gz_civil_equipment_dev";
  private static final String username = "root";
  private static final String password = "Happy-2008-2009";

  // 表名前缀
  private static final String tablePrefix = "t_";
  // 要生成的表名(推荐填单个表名)
  private static final String[] table = {"t_clean_log","t_clean_log_info","t_firearms",
          "t_firearms_type","t_ammo_type","t_ammo_firearm","t_firearms_apply_log","t_firearms_apply_type_log",
          "t_firearms_apply_type_info","t_firearms_receive_batch","t_firearms_return_batch","t_department",
          "t_post","t_police","t_user","t_group","t_group_user","t_resources","t_group_menu","t_permission","t_group_permission"};
  // 包名（例 app、cabinet ）
  private static final String module = "temp";

  public static void main(String[] args) {

    AutoGenerator gen = new AutoGenerator();
    // 数据库配置
    gen.setDataSource(
      new DataSourceConfig()
        .setDbType(DbType.MYSQL)
        .setDriverName(driverName)
        .setUrl(url)
        .setUsername(username)
        .setPassword(password)
    );

    System.out.println(baseProjectPath);
    // 全局配置
    gen.setGlobalConfig(new GlobalConfig()
      //输出目录
      .setOutputDir(outPutDir)
      .setAuthor(authorName)
      .setOpen(false)
      // 是否覆盖文件
      .setFileOverride(true)
    );

    // 策略配置
    gen.setStrategy(
      new StrategyConfig()
        .setTablePrefix(tablePrefix)
        // 表名生成策略
        .setNaming(NamingStrategy.underline_to_camel)
        .setColumnNaming(NamingStrategy.underline_to_camel)
        // 需要生成的表
        .setInclude(table)
        .setEntityLombokModel(true)
        .setRestControllerStyle(true)
    );

    // 包配置
    gen.setPackageInfo(new PackageConfig()
      .setModuleName(module)
      // 自定义包路径
      .setParent(basePackage)
      .setEntity("model")
    );

    TemplateConfig templateConfig = new TemplateConfig();
    gen.setTemplate(templateConfig);
    gen.setTemplateEngine(new FreemarkerTemplateEngine());

    // 执行生成
    gen.execute();
  }
}
