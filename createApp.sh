#!/bin/bash    
app_name="$1"
app_url="$2"
app_logo="$3"
app_package="$4"
build_path="/tmp/$app_name"
cur_path=$PWD

echo "App name: " $app_name
echo "\n App url: " $app_url
echo "\n App logo: " $app_logo
echo "\n App Package: " $app_package
echo "\n App Build Path: " $build_path
echo "\n Current/start Path: " $cur_path

mkdir $build_path
cp -r ./* $build_path

sed -i -e 's#$website_name#'$app_name'#' $build_path/res/values/strings.xml
sed -i -e 's#$website_url#'$app_url'#' $build_path/res/values/strings.xml

rm $build_path/res/values/strings.xml-e
rm -rf $build_path/bin/*

cp ~/Pictures/appLogos/hdpi/$app_logo ~/Pictures/appLogos/ldpi/$app_logo
cp ~/Pictures/appLogos/hdpi/$app_logo ~/Pictures/appLogos/mdpi/$app_logo

sips --resampleHeight 36 ~/Pictures/appLogos/ldpi/$app_logo
sips --resampleHeight 48 ~/Pictures/appLogos/mdpi/$app_logo

cp ~/Pictures/appLogos/hdpi/$app_logo $build_path/res/drawable-hdpi/ic_launcher.png
cp ~/Pictures/appLogos/ldpi/$app_logo $build_path/res/drawable-ldpi/ic_launcher.png
cp ~/Pictures/appLogos/mdpi/$app_logo $build_path/res/drawable-mdpi/ic_launcher.png

cd $build_path

ant clean

mv $build_path/src/com/brim4brim/webview/ $build_path/src/com/brim4brim/$app_package

#find $build_path -name “*” ‘ xargs file ‘ grep -v directory ‘ grep -v image ‘ cut -d’:’ -f1 ‘ xargs sed -i 's#com.brim4brim.webview#com.brim4brim.app_package#g'
find $build_path -type f -name '*.java' -exec sed -i '' s#com.brim4brim.webview#com.brim4brim.$app_package# {} +
find $build_path -type f -name '*.xml' -exec sed -i '' s#com.brim4brim.webview#com.brim4brim.$app_package# {} +


ant release

cd $cur_path

/System/Library/Java/JavaVirtualMachines/1.6.0.jdk/Contents/Home/bin/jarsigner -verbose -keystore ~/AndKeyStore/AndKeyStore -storepass asdf1asdf $build_path/bin/WebviewActivity-release-unsigned.apk brim4brim

cp $build_path/bin/WebviewActivity-release-unsigned.apk $cur_path/bin/$app_name.apk
#~/android-sdks/tools/zipalign -f -v $build_path/bin/WebviewActivity-release-unsigned.apk $cur_path/bin/Politics.ie.apk

sed -i -e 's#'$app_name'#$website_name#' $build_path/res/values/strings.xml
sed -i -e 's#'$app_url'#$website_url#' $build_path/res/values/strings.xml

rm $build_path/res/values/strings.xml-e
rm -rf /tmp/*
