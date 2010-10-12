#!/bin/bash

# Compress standard web files to download directories
# v1.0
# author Tim Stamp (timstamp.co.uk)

date=`date +%Y%m%d%H%M`

rm download/*.tar.*
rm download/*.zip.*
rm download/*.zip

zip -r -q "download/${date}.zip" *.* ascii asymmetric hashing img symmetric
zip -r -q "download/no_imgs_${date}.zip" *.* ascii asymmetric hashing symmetric

tar -c *.* ascii asymmetric hashing img symmetric > "download/${date}.tar"
tar -c *.* ascii asymmetric hashing symmetric > "download/no_imgs_${date}.tar"

bzip2 -qfc "download/${date}.tar" > "download/${date}.tar.bz2"
bzip2 -qfc "download/no_imgs_${date}.tar" > "download/no_imgs_${date}.tar.bz2"
gzip -qf "download/${date}.tar"
gzip -qf "download/no_imgs_${date}.tar"

