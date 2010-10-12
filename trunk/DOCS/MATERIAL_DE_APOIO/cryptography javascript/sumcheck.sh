#!/bin/bash

# Recursive MD5 and SHA1 checksum tester and generator
# author: Tim Stamp (timstamp.co.uk)
# version: v1.0
#
# Warning! This script warns of mis-matching checksums and then over-writes
# the old ones with the new ones. If this is not the action you want, then
# you need to modify this script to your requirements.
#
# usage: prog.sh [extname]
# where extname is the extension of the files you wish to find checksums for
# default operation is to check .html .js .zip .gz .tar and .sh files

if [ $# -eq 0 ]; then
   ft="html"
   $0 "js"
   $0 "zip"
   $0 "gz"
   $0 "tar"
   $0 "bz2"
   $0 "sh"
else
   ft=$1
fi

echo "Checking .${ft} files :: (x is changed, + is added)"

chg="False"

for a in `find . -name \*.$ft`; do
      sha1=`sha1sum $a`
      md5=`md5sum $a`
      if [ -f "${a}.md5" ]; then
         osha1=`cat "${a}.sha1"`
         echo $sha1 > "${a}.sha1"
         echo $md5 > "${a}.md5"
         sha1=`cat "${a}.sha1"`
         if [ "$osha1" == "$sha1" ]; then
            echo "   ${a}"
         else
            echo " x ${a}"
            chg="True"
         fi
      else
         echo $sha1 > "${a}.sha1"
         echo $md5 > "${a}.md5"
         echo " + ${a}"
         chg="True"
         #echo -n "o"
      fi
done

if [ "$chg" ==  "True" ]; then
    echo "Checksums Updated"
else
    echo "File Structure Unchanged"
fi


