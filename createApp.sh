#!/bin/bash    
app_name="$1"
app_url="$2"

echo $app_name
echo $app_url

sed -i -e 's#$website_name#'$app_name'#' ./res/values/strings.xml
sed -i -e 's#$website_url#'$app_url'#' ./res/values/strings.xml

rm ./res/values/strings.xml-e
rm -rf ./bin/*

ant release
/System/Library/Java/JavaVirtualMachines/1.6.0.jdk/Contents/Home/bin/jarsigner -verbose -keystore ~/AndKeyStore/AndKeyStore -storepass asdf1asdf ./bin/WebviewActivity-release-unsigned.apk brim4brim

cp ./bin/WebviewActivity-release-unsigned.apk ./bin/$1.apk

sed -i -e 's#'$app_name'#$website_name#' ./res/values/strings.xml
sed -i -e 's#'$app_url'#$website_url#' ./res/values/strings.xml

rm ./res/values/strings.xml-e
