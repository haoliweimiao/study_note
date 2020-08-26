# Base64加解密

``` shell
$ printf "abcdef" | base64
$ YWJjZGVm
 
$ printf "YWJjZGVm" | base64 -d
$ abcdef
```