# Civil Gun Management System Server

民用枪支管理系统服务端
###
### Launch project:
First run: 
```bash
com.company.project.Application.java
```
or
```bash
mvn: spring-boot:run
```
Then request: http://localhost:8080

Upload deployment:
```bash
/home/test
```

###
### Compile and deploy to server:
```bash
cd ~/project/equipment
git pull origin dev
mvn clean install
```bash

Then you can see the generated jar package:
```bash
cp target/spring-boot-api-civil-equipment-1.0.jar ~/
```

Kill the previous project first:
```bash
ps -ef|grep spring-boot-api-civil-equipment
test     17694 14464 99 17:57 pts/4    00:00:09 java -jar /home/test/spring-boot-api-civil-equipment-1.0.jar
test     17713 14464  0 17:58 pts/4    00:00:00 grep --color=auto spring-boot-api-civil-equipment
kill -9 17694
nohup java -jar ~/spring-boot-api-civil-equipment-1.0.jar > ~/spring-boot-api-civil-equipment.log 2>&1 &
```

View log:
```bash
tail -f -n 2000 ~/spring-boot-api-civil-equipment.log
```

Visit the following URL:
```bash
http://47.107.180.247:7878/springboot-equipment-api/
https://blog.csdn.net/yuhui123999/article/details/80593750
```

