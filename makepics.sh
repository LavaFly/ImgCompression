#!/usr/bin/env bash
#konvertiert alle Bilder in dem selben Verzeichnis zum ppm Dateityp
#benötigt ImageMagick(nie auf Windows getestet)
Number=1
for bild in *.jpg
do
    convert "$bild" -resize "800x600!" -compress none  $Number.ppm
    let Number+=1;
    echo "$Number";
done
for bild in *.png
do
    convert "$bild" -resize "800x600!" -compress none  $Number.ppm
    let Number+=1;
    echo "$Number";
done
