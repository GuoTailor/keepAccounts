call copy keepaccounts.sql kpdoc/
call apidoc -i ./ -o kpdoc/
call scp -r kpidoc root@47.107.178.147:/usr/local/exam
