@REM call mvnw -Djavacpp.platform.custom -Djavacpp.platform.host -Djavacpp.platform.linux-x86_64 kotlin:compile -Dmaven.test.skip=true package
call mvnw kotlin:compile -Dmaven.test.skip=true package
call scp target\keepAccounts-0.0.1-SNAPSHOT.jar root@1.14.65.118:~