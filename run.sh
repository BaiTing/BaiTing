set paths
#loop lib directory jar package
for i in `ls lib | grep jar` 
do
paths=$paths'lib/'$libpath$i:
done
#loop current directory jar package
for i in `ls | grep jar`
do 
paths=$paths$i
done
#echo $paths
java -Xms128m -Xmx256m -Dfile.encoding=utf-8 -classpath $paths com.baiting.main.BaiTingMain
