@echo off
chcp 65001 >nul
cd /d "%~dp0"
echo 正在執行 CardArena Maven FinalStyle...
echo.
echo 若尚未打包，請先在 Eclipse 執行 Maven build: clean package
echo.
if exist "target\CardArena_Maven_FinalStyle-1.0.0.jar" (
  java -jar "target\CardArena_Maven_FinalStyle-1.0.0.jar"
) else (
  echo 找不到 target\CardArena_Maven_FinalStyle-1.0.0.jar
  echo 請先在 Eclipse: 專案右鍵 ^> Run As ^> Maven build... ^> clean package
  pause
)
