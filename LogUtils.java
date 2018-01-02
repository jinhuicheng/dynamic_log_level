/**
 *
 *  动态修改其实也需要有地方出发，比如可以用界面、zookeeper等形式，
 *  让系统接收到修改指令，然后再调用此方法，可达到动态修改日志的目的
 *
 */
public class LogUtils {
  private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(LogUtils.class);

  //静态方法，更新日志级别
  public static void updateLogLevel(String levelStr) {
    //根据传入的字符串，创建日志级别bean 
    Level level = Level.toLevel(levelStr, null);
    
    if (level == null) {
      LOGGER.error("updateLogLevel wrong level: {}", levelStr);
    }
    
    //获取系统日志上下文
    LoggerContext context = (LoggerContext) LogManager.getContext(false);
    
    //从上下文获取配置
    Configuration configuration = context.getConfiguration();
    
    //循环判断赋值
    for (Map.Entry<String, LoggerConfig> entry : configuration.getLoggers().entrySet()) {
      if (entry.getValue().getLevel().intLevel() != level.intLevel()) {
        entry.getValue().setLevel(level);
      }
    }
    //更新配置
    context.updateLoggers(configuration);
    
    LOGGER.info("updateLogLevel level: {}", levelStr);
  }
}
