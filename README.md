# CivilGunMagazineServer

民用枪支管理系统服务端
Launch project:
First run: com.company.project.Application.java
or
mvn: spring-boot:run
Then request: http://localhost:8080

Upload deployment:
/home/test

Compile and deploy to server:
cd ~/project/equipment
git pull origin dev
mvn clean install

Then you can see the generated jar package:
cp target/spring-boot-api-civil-equipment-1.0.jar ~/

Kill the previous project first:
ps -ef|grep spring-boot-api-civil-equipment
test     17694 14464 99 17:57 pts/4    00:00:09 java -jar /home/test/spring-boot-api-civil-equipment-1.0.jar
test     17713 14464  0 17:58 pts/4    00:00:00 grep --color=auto spring-boot-api-civil-equipment
kill -9 17694
nohup java -jar ~/spring-boot-api-civil-equipment-1.0.jar > ~/spring-boot-api-civil-equipment.log 2>&1 &

View log:
tail -f -n 2000 ~/spring-boot-api-civil-equipment.log

Visit the following URL:
http://47.107.180.247:7878/springboot-equipment-api/
https://blog.csdn.net/yuhui123999/article/details/80593750

