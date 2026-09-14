$ErrorActionPreference = "Stop"

$projectRoot = Split-Path -Parent (Split-Path -Parent $MyInvocation.MyCommand.Path)
$schemaPath = Join-Path $projectRoot "database\schema.sql"

$mysqlCandidates = @(
    "C:\Program Files\MySQL\MySQL Server 8.4\bin\mysql.exe",
    "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe"
)

$mysqlExe = $mysqlCandidates | Where-Object { Test-Path -LiteralPath $_ } | Select-Object -First 1
if (-not $mysqlExe) {
    $command = Get-Command mysql.exe -ErrorAction SilentlyContinue
    if ($command) {
        $mysqlExe = $command.Source
    }
}

if (-not $mysqlExe) {
    Write-Host "No encontre mysql.exe. Revisa la instalacion de MySQL Server Community." -ForegroundColor Red
    Read-Host "Presiona Enter para cerrar"
    exit 1
}

if (-not (Test-Path -LiteralPath $schemaPath)) {
    Write-Host "No encontre el archivo database\schema.sql." -ForegroundColor Red
    Read-Host "Presiona Enter para cerrar"
    exit 1
}

Write-Host "Configurando base de datos asistencia_mvp en MySQL Community..." -ForegroundColor Cyan
Write-Host "Se usara: $mysqlExe"
Write-Host ""

$securePassword = Read-Host "Ingresa la contrasena de root de MySQL" -AsSecureString
$bstr = [Runtime.InteropServices.Marshal]::SecureStringToBSTR($securePassword)
$plainPassword = [Runtime.InteropServices.Marshal]::PtrToStringBSTR($bstr)
[Runtime.InteropServices.Marshal]::ZeroFreeBSTR($bstr)

try {
    $env:MYSQL_PWD = $plainPassword
    Get-Content -LiteralPath $schemaPath -Raw | & $mysqlExe -u root --default-character-set=utf8mb4
    if ($LASTEXITCODE -ne 0) {
        throw "MySQL devolvio codigo $LASTEXITCODE al ejecutar el schema."
    }

    & $mysqlExe -u asistencia_app -pasistencia123 --default-character-set=utf8mb4 asistencia_mvp -e "SHOW TABLES; SELECT COUNT(*) AS usuarios FROM usuarios; SELECT COUNT(*) AS asistencias FROM asistencias;"
    if ($LASTEXITCODE -ne 0) {
        throw "No se pudo verificar la conexion con asistencia_app."
    }

    Write-Host ""
    Write-Host "Base de datos lista. Ya puedes abrir el proyecto en NetBeans." -ForegroundColor Green
} catch {
    Write-Host ""
    Write-Host "No se pudo configurar la base de datos:" -ForegroundColor Red
    Write-Host $_.Exception.Message -ForegroundColor Red
} finally {
    Remove-Item Env:\MYSQL_PWD -ErrorAction SilentlyContinue
}

Write-Host ""
Read-Host "Presiona Enter para cerrar"
