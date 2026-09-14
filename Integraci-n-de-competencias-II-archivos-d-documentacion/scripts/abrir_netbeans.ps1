$ErrorActionPreference = "Stop"

$projectRoot = Split-Path -Parent (Split-Path -Parent $MyInvocation.MyCommand.Path)
$netbeansCandidates = @(
    "C:\Program Files\Apache NetBeans\bin\netbeans64.exe",
    "C:\Program Files\NetBeans-25\netbeans\bin\netbeans64.exe",
    "C:\Program Files\NetBeans-24\netbeans\bin\netbeans64.exe"
)

$netbeansExe = $netbeansCandidates | Where-Object { Test-Path -LiteralPath $_ } | Select-Object -First 1

if (-not $netbeansExe) {
    Write-Host "No encontre NetBeans. Abre Apache NetBeans manualmente y selecciona esta carpeta:" -ForegroundColor Yellow
    Write-Host $projectRoot
    Read-Host "Presiona Enter para cerrar"
    exit 1
}

Start-Process -FilePath $netbeansExe -ArgumentList "--open", "`"$projectRoot`""
