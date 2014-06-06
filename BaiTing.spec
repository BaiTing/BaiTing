Vendor:		LMQ
Distribution:	Any
Name:		BaiTing
Version:	1.0
Release:	1
Packager:	424501083@qq.com
License:	Copyright 2012 by LMQ
Group:		Application/Multimedia

Provides:	play song
Requires:	jre >= 1.6

BuildRoot:	%{_tmppath}/%{name}-%{version}-root
Source:		%{name}-%{version}.tar.gz

Summary:	Trivial application
%description
BaiTing Trivial Meida Application
A Music Play Application used to Play location music and net online music,It is synchronized lyrics and download the song.
This version pretends it requires JAVA at or abvoe 1.6.
Author: lmq

%prep

%setup

%build
#make

%install
mkdir -p $RPM_BUILD_ROOT/opt/BaiTing/bin
mkdir -p $RPM_BUILD_ROOT/usr/local/bin
cp -rvf %{_builddir}/%{name}-%{version}/* $RPM_BUILD_ROOT/opt/BaiTing/
rm -rvf $RPM_BUILD_ROOT/opt/BaiTing/baiting.sh
rm -rvf $RPM_BUILD_ROOT/opt/BaiTing/baiting.bat
rm -rvf $RPM_BUILD_ROOT/opt/BaiTing/runbat.vbs
rm -rvf $RPM_BUILD_ROOT/opt/BaiTing/Makefile
rm -rvf $RPM_BUILD_ROOT/opt/BaiTing/setting/config.xml
chmod -R 755 $RPM_BUILD_ROOT/opt/BaiTing/
chmod -R 7777 $RPM_BUILD_ROOT/opt/BaiTing/download
chmod -R 7777 $RPM_BUILD_ROOT/opt/BaiTing/lyrics
chmod -R 7777 $RPM_BUILD_ROOT/opt/BaiTing/list
chmod -R 7777 $RPM_BUILD_ROOT/opt/BaiTing/setting
install -m755 baiting.sh $RPM_BUILD_ROOT/opt/BaiTing/baiting
#install -m755 baiting.sh $RPM_BUILD_ROOT/usr/local/bin/baiting
#ln -s $RPM_BUILD_ROOT/opt/BaiTing/baiting $RPM_BUILD_ROOT/usr/local/bin/baiting

%clean
rm -rvf $RPM_BUILD_ROOT/opt/BaiTing

%post
ln -s $RPM_BUILD_ROOT/opt/BaiTing/baiting $RPM_BUILD_ROOT/usr/local/bin/baiting

%files
#/opt/BaiTing
/opt/BaiTing/baiting
#/usr/local/bin/baiting
/opt/BaiTing/baiting-1.0.jar
/opt/BaiTing/fonts/simsun.ttc
/opt/BaiTing/images/music_bg02.gif
/opt/BaiTing/list
/opt/BaiTing/download
/opt/BaiTing/lib/*.jar
/opt/BaiTing/lyrics
/opt/BaiTing/icon/*.gif
/opt/BaiTing/setting
#/opt/BaiTing
