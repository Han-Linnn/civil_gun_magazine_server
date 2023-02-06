# coding: utf-8
from fabric.api import env, local, cd, lcd, run, sudo, put, roles, prompt
import time

env.use_ssh_config = True
env.keepalive = True

env.roledefs = {
    'prod': ['root@192.168.1.100:22'],
    'dev': ['test@47.107.180.247:22']
}

env.passwords = {
    'root@192.168.1.100:22': '!@#123QWE',
    'test@47.107.180.247:22': '!@#123QWE'
}

# 日志文件夹
log_dir = '/home/test/equipment-logs'
# 容器日志
tomcat_log_file = '{}/tomcat_log.log'.format(log_dir)
# 应用日志
app_log_file = '{}/app_log.log'.format(log_dir)


# 测试环境 关闭应用
@roles('dev')
def stop():
    with cd('/home/test'):
        sudo('pkill -f spring-boot-api-civil-equipment')


# 查看进程
@roles('dev')
def process():
    with cd('/home/test'):
        run('ps -ef|grep spring-boot-api-civil-equipment')


# 已经上传了spring-boot-api-civil-equipment-1.0.jar
# 测试环境 启动应用
@roles('dev')
def start():
    with cd('/home/test'):
        # nohup confiadding a sleep after the command execution line
        # 记录标准输出、标准错误，由应用内日志记录
        run('nohup java -jar spring-boot-api-civil-equipment-1.0.jar > {} 2>&1 & sleep 2; exit 0'.format(tomcat_log_file))


# 测试环境 查看实时日志
@roles('dev')
def dev_log():
    with cd('/home/test'):
        run('tail -f -n 200 {}'.format(app_log_file))


@roles('dev')
def dev_tomcat_log():
    with cd('/home/test'):
        run('tail -f -n 200 {}'.format(tomcat_log_file))


# 测试环境 部署
@roles('dev')
def deploy():
    confirm_action = prompt('depoly on dev? [y/n]')
    if confirm_action == 'y':
        # 本地编译
        local('mvn clean package -P=test -Dmaven.test.skip=true')
        with cd('/home/test'):
            time.sleep(1)
            # 删除应用jar包
            sudo('rm -f spring-boot-api-civil-equipment-1.0.jar')
            with lcd('./target'):
                # 上传新的编译包
                put('./spring-boot-api-civil-equipment-1.0.jar', '~/')
                # 启动应用
                start()


# 正式环境 部署
@roles('prod')
def prod():
    pass


# 正式环境 查看实时日志
@roles('dev')
def prod_log():
    pass
