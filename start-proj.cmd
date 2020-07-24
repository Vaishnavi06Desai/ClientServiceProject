@echo off
if %1%==start goto start
if %1%==stop goto stop
if %1%==help goto help
goto help
:start
pushd .
C:
cd C:\Users\Vanilla\Angular Projects\ClientWebPage
start http-server -p 8040 ./dist/ClientWebPage -o --allow-insecure-localhost
cd C:\Users\Vanilla\EclipseProjects\LoggerService
start mvnw spring-boot:run
timeout 4
cd C:\Users\Vanilla\EclipseProjects\AuthServer
start mvnw spring-boot:run
cd C:\Users\Vanilla\EclipseProjects\ClientService
start mvnw spring-boot:run
cd C:\Users\Vanilla\EclipseProjects\OcrService
start mvnw spring-boot:run
popd
goto end
:stop
pushd .
C:
cd C:\Users\Vanilla\EclipseProjects\LoggerService
curl POST localhost:8084/actuator/shutdown
cd C:\Users\Vanilla\EclipseProjects\AuthServer
curl POST localhost:8082/actuator/shutdown
cd C:\Users\Vanilla\EclipseProjects\ClientService
curl POST localhost:8080/actuator/shutdown
cd C:\Users\Vanilla\EclipseProjects\OcrService
curl POST localhost:8443/actuator/shutdown
popd
goto end
:help
echo Usage
echo "doc-cat start" to start
echo "doc-cat stop" to stop
:end
