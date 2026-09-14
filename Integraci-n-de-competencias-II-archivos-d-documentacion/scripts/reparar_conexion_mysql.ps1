$ErrorActionPreference = "Stop"

$projectRoot = Split-Path -Parent (Split-Path -Parent $MyInvocation.MyCommand.Path)
$repairPath = Join-Path $projectRoot "database\repair_user.sql"

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

if (-not (Test-Path -LiteralPath $repairPath)) {
    Write-Host "No encontre database\repair_user.sql." -ForegroundColor Red
    Read-Host "Presiona Enter para cerrar"
    exit 1
}

Write-Host "Reparando conexion de la aplicacion con MySQL Community..." -ForegroundColor Cyan
Write-Host "Esto NO borra datos existentes; solo repara usuario, permisos y tablas si faltan."
Write-Host ""

$securePassword = Read-Host "Ingresa la contrasena de root de MySQL" -AsSecureString
$bstr = [Runtime.InteropServices.Marshal]::SecureStringToBSTR($securePassword)
$plainPassword = [Runtime.InteropServices.Marshal]::PtrToStringBSTR($bstr)
[Runtime.InteropServices.Marshal]::ZeroFreeBSTR($bstr)

try {
    $env:MYSQL_PWD = $plainPassword
    Get-Content -LiteralPath $repairPath -Raw | & $mysqlExe -u root --default-character-set=utf8mb4
    if ($LASTEXITCODE -ne 0) {
        throw "MySQL devolvio codigo $LASTEXITCODE al reparar el usuario."
    }

    Remove-Item Env:\MYSQL_PWD -ErrorAction SilentlyContinue
    & $mysqlExe -u asistencia_app -pasistencia123 --default-character-set=utf8mb4 asistencia_mvp -e "SELECT CURRENT_USER(); SHOW TABLES; SELECT COUNT(*) AS usuarios FROM usuarios;"
    if ($LASTEXITCODE -ne 0) {
        throw "No se pudo verificar asistencia_app despues de la reparacion."
    }

    Write-Host ""
    Write-Host "Conexion reparada. Cierra y vuelve a ejecutar la app en NetBeans." -ForegroundColor Green
} catch {
    Write-Host ""
    Write-Host "No se pudo reparar la conexion:" -ForegroundColor Red
    Write-Host $_.Exception.Message -ForegroundColor Red
} finally {
    Remove-Item Env:\MYSQL_PWD -ErrorAction SilentlyContinue
}

Write-Host ""
Read-Host "Presiona Enter para cerrar"
