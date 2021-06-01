call copy keepaccounts.sql kadoc/
call apidoc -i ./ -o kadoc/
call scp -r kaidoc root@47.107.178.147:/usr/local/exam
