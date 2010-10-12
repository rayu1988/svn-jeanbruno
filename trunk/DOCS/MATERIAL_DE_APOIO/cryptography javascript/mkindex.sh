#!/bin/bash

# Recursive index files generator for etherhack.co.uk dir structure
# author: Tim Stamp (timstamp.co.uk)
# version: v1.0
#
# this script TAKES NO ARGUMENTS!! if you specify arguments things might
# go a bit pear-shaped.
#
# don't forget to run the sumcheck.sh afterwards, then run them both again!
# - this is to ensure this file's checksums are included in the index listing,
# and that once they are, the checksum is updated to record this change.

if [ $# -eq 0 ]; then
   dir="."
else
   dir=$1
fi

## calculate the directory depth and add appropriate number of ../
## to link back to main page. We're not using a single / to make
## the system portable for use offline - the files are then unlikely
## to be mounted in the root of the filesystem.
depth=`echo "${dir}" | tr -dc "/" | wc -c`
dots=""
for((d=0;d<$depth;d++)); do
    dots="../${dots}"
done

echo "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"
   \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">
<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\">
<head>
<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\" />
<meta name=\"Author\" content=\"Tim Stamp\" />
<meta http-equiv=\"expires\" content=\"-1\" />
<meta http-equiv=\"pragma\" content=\"no-cache\" />
<meta http-equiv=\"cache-control\" content=\"no-cache\" />
<meta name=\"Keywords\" content=\"javascript, rsa, des, 3des, triple des, symmetric, asymmetric, cryptography, encryption, decryption, security, hashing, base64, blowfish, ascii conversion, base conversion, sha, whirlpool, ripemd\" />
<meta name=\"Description\" content=\"JavaScript based Cryptography tools for users and developers\" />
<link href=\"${dots}icon.png\" rev=\"contents\" rel=\"shortcut icon\" type=\"image/x-icon\" />
<title>JavaScript Crypto Library</title>
<style type=\"text/css\">
body {
  text-align: left;
  color: #333;
  font-family: Monospace;
  background-color: #fff;
  font-size: 9pt;
}
div.main { 
  background-image: url(${dots}img/bg_dir.png);
  background-repeat: no-repeat;
  background-position: top right;
  width: 600px;
  position:relative;
  margin:20px auto;
}
.extlink { color: #f00; }
p { margin: 1px; padding: 0px; }
p.devwarn { color: #f00; font-weight: bold;}
a, img.a, .w3 { border-width: 0px; color: #00f; text-decoration: none;}
a.dir {}
a.file { color: #080; }
table.linktbl { font-family: Monospace; font-size: 0.9em; min-width:300px; }
td { margin: 1px; padding: 0px; }
</style>
</head><body>
<div class=\"main\">
<div>
<a href=\"${dots}main.html\">[home]</a>
<a href=\"files.html.md5\">[md5]</a>
<a href=\"files.html.sha1\">[sha1]</a>
</div>
<br />" > "${dir}/files.html"

updir_a=""
updir_b=""
updir=""
if [ $# -eq 0 ]; then 
    echo "etherhack.co.uk" >> "${dir}/files.html"
else
    updir_a="<a class=\"dir\" type=\"text/html\" charset=\"utf-8\" href=\"../files.html\">"
    updir_b="</a>";
    echo "<a href=\"${dots}files.html\">etherhack.co.uk</a> ${updir_a}${dir}${updir_b} " >> "${dir}/files.html"
    updir="<tr><td>${updir_a}..${updir_b}</td><td></td></tr>"
fi

echo "<br /><br /><table class=\"linktbl\"><tr><td><b>filename</b></td><td><b>size</b></td></tr>
${updir}" >> "${dir}/files.html"

echo -n "["
for a in `ls -1 --group-directories-first $dir `; do
    if [ -d "${dir}/${a}" ]; then
        echo -n "o"
        $0 "${dir}/${a}"
        echo "<tr><td><a class=\"dir\" type=\"text/html\" charset=\"utf-8\" href=\"${a}/files.html\">${a}</a></td><td>[dir]</td></tr>" >> "${dir}/files.html"
    else
        echo -n "-"
        size=`stat -c%s "${dir}/${a}"`
        echo "<tr><td><a class=\"file\" href=\"${a}\">${a}</a></td><td>${size}</td></tr>" >> "${dir}/files.html"
    fi
done
echo -n "]"

echo "</table>
<br />
<p>
<a class=\"extlink\" type=\"text/html\" href=\"http://jigsaw.w3.org/css-validator/check/referer\">[W3C Valid CSS 2]</a><br />
<a class=\"extlink\" type=\"text/html\" href=\"http://validator.w3.org/check?uri=referer\">[W3C Valid XHTML 1.1]</a>
</p>
</div>
</body>
</html>" >> "${dir}/files.html"

if [ $# -eq 0 ]; then
    echo ""
fi
