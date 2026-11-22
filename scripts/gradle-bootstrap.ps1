param(
    [ValidateSet('bootRun', 'test', 'bootJar', 'clean')]
    [string]$Task = 'bootRun'
)

$ErrorActionPreference = 'Stop'
$projectRoot = (Resolve-Path (Join-Path $PSScriptRoot '..')).Path
$gradleVersion = '8.14.3'
$gradleHome = Join-Path $projectRoot ".tools\gradle-$gradleVersion"
$gradleExe = Join-Path $gradleHome 'bin\gradle.bat'

try {
    Push-Location $projectRoot

    if (-not (Get-Command java -ErrorAction SilentlyContinue)) {
        throw 'Java не найдена. Установите JDK 21 и перезапустите терминал: https://adoptium.net/temurin/releases/?version=21'
    }

    $javaVersionOutput = (& cmd.exe /d /c "java -version 2>&1" | Out-String)
    $javaMatch = [regex]::Match($javaVersionOutput, 'version "(\d+)')
    if (-not $javaMatch.Success) {
        throw 'Не удалось определить версию Java. Установите JDK 21.'
    }
    $javaMajor = [int]$javaMatch.Groups[1].Value
    if ($javaMajor -ne 21) {
        throw "Нужна Java 21, найдена Java $javaMajor. Ссылка: https://adoptium.net/temurin/releases/?version=21"
    }

    if (-not (Test-Path $gradleExe)) {
        Write-Host 'Первый запуск: загружаю Gradle 8.14.3 (потребуется интернет)...' -ForegroundColor Cyan
        $toolDir = Join-Path $projectRoot '.tools'
        New-Item -ItemType Directory -Path $toolDir -Force | Out-Null
        $archive = Join-Path $toolDir 'gradle-8.14.3-bin.zip'
        [Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12
        Invoke-WebRequest -Uri "https://services.gradle.org/distributions/gradle-$gradleVersion-bin.zip" -OutFile $archive -UseBasicParsing
        Expand-Archive -Path $archive -DestinationPath $toolDir -Force
        Remove-Item $archive -Force
    }

    Write-Host "Выполняется Gradle: $Task" -ForegroundColor Cyan
    & $gradleExe --no-daemon $Task
    $result = $LASTEXITCODE
    if ($result -ne 0) {
        throw "Gradle завершился с кодом $result. Прочитайте сообщение об ошибке выше."
    }
} catch {
    Write-Host "ОШИБКА: $($_.Exception.Message)" -ForegroundColor Red
    exit 1
} finally {
    Pop-Location
}
