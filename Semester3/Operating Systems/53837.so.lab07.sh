#!/bin/bash
# SO IS1 222B LAB07
# Mateusz Tolpa
# tm53837@zut.edu.pl

if [ "$1" != "-l" ] && [ "$1" != "-b" ]
then
    echo "Zly argument"
    exit 1
fi

if [ ! -e "~/Desktop/bledy.txt" ]
then
        cd ~/Desktop
        touch bledy.txt
fi

cd /sys/module

case "$1" in
"-b")
echo "Moduly wbudowane: "

for folder in $(find . -maxdepth 1 -type d)
do
        modul=$(basename "$folder")

        if [ ! -e "$folder/refcnt" ]
        then
                parametry=$(ls -1 "$folder/parameters" 2>~/Desktop/bledy.txt)
                listaParam=$(echo "$parametry" | tr '\n' ', ' | sed 's/,$//')

                echo "$modul $listaParam"
        fi
        done | sort
;;

"-l")
echo "Moduly ladowalne:  "
for folder in $(find . -maxdepth 1 -type d)
do
        nazwa=$(basename "$folder")

        if [ -e "$folder/refcnt" ]
        then
                ilosc=$(cat "$folder/refcnt")
                holders=$(ls -1 "$folder/holders" 2>~/Desktop/bledy.txt)
                lista=$(echo "$holders" | tr '\n' ', ' | sed 's/,$//')
                echo "$nazwa $ilosc $lista"
        fi
        done | sort
;;
esac
