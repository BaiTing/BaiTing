@ECHO off
set libpath=lib\
setlocal EnableDelayedExpansion
for /f %%i in ('dir lib\*.jar /b') do (
    set liblist=!liblist!%libpath%%%i;
)
for /f %%i in ('dir *.jar /b') do (
    set appjar=!appjar!%%i;
)
set paths=!liblist!!appjar!
java -Xms128m -Xmx256m -Dfile.encoding=GB18030 -classpath %paths% com.baiting.main.BaiTingMain >> ./log/run.log
endlocal