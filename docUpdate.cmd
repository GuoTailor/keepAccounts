call copy ibms.sql doc/
call apidoc -i ./ -o apidoc/
call scp -r apidoc root@47.107.178.147:/usr/local/exam