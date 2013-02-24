#!/bin/bash    
app_name="$1"
app_url="$2"
app_logo="$3"
app_package="$4"
build_path="/tmp/$app_name"
cur_path=$PWD

# output variable names for ease of display
echo "App name: " $app_name
echo "\n App url: " $app_url
echo "\n App logo: " $app_logo
echo "\n App Package: " $app_package
echo "\n App Build Path: " $build_path
echo "\n Current/start Path: " $cur_path

# make a copy of the code to make changes to
mkdir $build_path
cp -r ./* $build_path

# update the strings.xml file with app name and app url
sed -i -e 's#$website_name#'$app_name'#' $build_path/res/values/strings.xml
sed -i -e 's#$website_url#'$app_url'#' $build_path/res/values/strings.xml
rm $build_path/res/values/strings.xml-e
rm -rf $build_path/bin/*

# copy the app icons to the right locations
cp ~/Pictures/appLogos/hdpi/$app_logo ~/Pictures/appLogos/ldpi/$app_logo
cp ~/Pictures/appLogos/hdpi/$app_logo ~/Pictures/appLogos/mdpi/$app_logo
cp ~/Pictures/appLogos/hdpi/$app_logo ~/Pictures/appLogos/store/hires/$app_logo

# resize the app icons
sips --resampleHeight 36 ~/Pictures/appLogos/ldpi/$app_logo
sips --resampleHeight 48 ~/Pictures/appLogos/mdpi/$app_logo
sips --resampleHeight 512 ~/Pictures/appLogos/store/hires/$app_logo

# copy the app icons to path in build
cp ~/Pictures/appLogos/hdpi/$app_logo $build_path/res/drawable-hdpi/ic_launcher.png
cp ~/Pictures/appLogos/ldpi/$app_logo $build_path/res/drawable-ldpi/ic_launcher.png
cp ~/Pictures/appLogos/mdpi/$app_logo $build_path/res/drawable-mdpi/ic_launcher.png

# go to path to run ant scripts
cd $build_path
ant clean

# rename the packages to match the app as this is what google store uses to identify app
mv $build_path/src/com/brim4brim/webview/ $build_path/src/com/brim4brim/$app_package

# rename the package string in every file in the project that requires it for app to compile and run
find $build_path -type f -name '*.java' -exec sed -i '' s#com.brim4brim.webview#com.brim4brim.$app_package# {} +
find $build_path -type f -name '*.xml' -exec sed -i '' s#com.brim4brim.webview#com.brim4brim.$app_package# {} +

# build the app in release mode and go back to original path as ant scripts are finished
ant release
cd $cur_path

# sign the app and zipalign it, copy it to the output directory and rename it here too
/System/Library/Java/JavaVirtualMachines/1.6.0.jdk/Contents/Home/bin/jarsigner -verbose -keystore ~/AndKeyStore/AndKeyStore -storepass asdf1asdf $build_path/bin/WebviewActivity-release-unsigned.apk brim4brim
~/android-sdks/tools/zipalign -f -v 4 $build_path/bin/WebviewActivity-release-unsigned.apk $cur_path/bin/$app_name.apk

# reset the strings in the xml, not required anymore as working on copy of the code but doesn't hurt
sed -i -e 's#'$app_name'#$website_name#' $build_path/res/values/strings.xml
sed -i -e 's#'$app_url'#$website_url#' $build_path/res/values/strings.xml
rm $build_path/res/values/strings.xml-e
rm -rf /tmp/*
