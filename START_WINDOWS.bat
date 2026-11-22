@echo off
chcp 65001 >nul
cd /d "%~dp0"
echo ================================================
echo    Student Exam Results - Spring Boot
echo ================================================
echo.
echo Откройте в браузере: http://localhost:8080/students
echo Для остановки сервера нажмите Ctrl+C.
echo.
powershell.exe -NoLogo -NoProfile -ExecutionPolicy Bypass -File "%~dp0scripts\gradle-bootstrap.ps1" -Task bootRun
if errorlevel 1 (
  echo.
  echo Запуск завершился с ошибкой. Прочитайте сообщения выше.
  pause
)
