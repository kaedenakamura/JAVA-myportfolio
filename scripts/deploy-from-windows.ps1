# ローカルから VPS へ再デプロイ
# 使い方:
#   .\scripts\deploy-from-windows.ps1 -SshKey "C:\path\to\devnakamura"
#
param(
    [Parameter(Mandatory = $true)]
    [string]$SshKey,
    [string]$Host = "160.251.205.21",
    [string]$User = "nakamura",
    [string]$ProjectDir = "/home/nakamura/project"
)

$ErrorActionPreference = "Stop"

if (-not (Test-Path $SshKey)) {
    Write-Error "SSH鍵が見つかりません: $SshKey"
}

$remote = "${User}@${Host}"
$cmd = "cd $ProjectDir && bash scripts/deploy-prod.sh"

Write-Host "==> SSH deploy to $remote"
ssh -i $SshKey -o StrictHostKeyChecking=accept-new $remote $cmd

Write-Host "==> Done: http://${Host}:9146/"
